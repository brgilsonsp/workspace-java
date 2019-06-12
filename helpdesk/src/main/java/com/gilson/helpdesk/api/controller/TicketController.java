package com.gilson.helpdesk.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gilson.helpdesk.api.dto.Summary;
import com.gilson.helpdesk.api.entity.ChangeStatus;
import com.gilson.helpdesk.api.entity.Ticket;
import com.gilson.helpdesk.api.entity.User;
import com.gilson.helpdesk.api.enums.ProfileEnum;
import com.gilson.helpdesk.api.enums.StatusEnum;
import com.gilson.helpdesk.api.response.Response;
import com.gilson.helpdesk.api.security.jwt.JwtTokenUtil;
import com.gilson.helpdesk.api.service.TicketService;
import com.gilson.helpdesk.api.service.UserService;

@RestController
@RequestMapping("/api/ticket/")
@CrossOrigin(origins = "*")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<?> create(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		Response<Ticket> response = new Response<>();
		validateCreateTicket(ticket, result);
		try {
			if(result.hasErrors()) {
				result.getAllErrors().forEach(item -> 
				response.addError(String.format("%s: %s", item.getObjectName(),item.getDefaultMessage())));
			}
			if(!response.hasErrors()) {
				ticket.setStatus(StatusEnum.New);
				ticket.setUser(this.userFromRequest(request));
				ticket.setNumber(this.generateNumber());
				ticket.setDate(new Date());
				ticket.getUser().setPassword(null);
				response.setData(this.ticketService.createOrUpdate(ticket));
			}
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Ticket>> update(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		Response<Ticket> response = new Response<>();
		
		try {
			this.validateUpdateTicket(ticket, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(item -> 
				response.addError(String.format("%s: %s", item.getObjectName(), item.getDefaultMessage())));
			}
			if(!response.hasErrors()) {
				Ticket ticketFounded = this.ticketService.findById(ticket.getId());
				ticket.setStatus(ticketFounded.getStatus());
				ticket.setUser(ticketFounded.getUser());
				ticket.setDate(ticketFounded.getDate());
				ticket.setNumber(ticketFounded.getNumber());
				if(ticketFounded.getAssignedUser() != null) {
					ticket.setAssignedUser(ticketFounded.getAssignedUser());
				}
				ticket.getUser().setPassword(null);
				response.setData(this.ticketService.createOrUpdate(ticket));
			}
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
	public ResponseEntity<Response<Ticket>> findById(@PathVariable("id") String id){
		Response<Ticket> response = new Response<>();
		
		try {
			Ticket ticketFounded = this.ticketService.findById(id);
			if(ticketFounded == null || ticketFounded.getId() == null) {
				response.addError("Ticket not found with ID: " + id);
			}
			if(!response.hasErrors()) {
				List<ChangeStatus> changes = new ArrayList<>();
				Iterable<ChangeStatus> listChangeStatus = this.ticketService.listChangeStatus(id);
				listChangeStatus.forEach(item -> {
					item.setTicket(null);
					changes.add(item);
				});
				ticketFounded.setChanges(changes);
				ticketFounded.getUser().setPassword(null);
				response.setData(ticketFounded);
			}
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	@GetMapping("{page}/{count}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
	public ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request, @PathVariable("page") Integer page, 
					@PathVariable("count") Integer count){
		Response<Page<Ticket>> response = new Response<>();
		
		try {
			User user = this.userFromRequest(request);
			if(user.getProfile().equals(ProfileEnum.ROLE_TECHNICIAN)) {
				Page<Ticket> pageTicket = this.ticketService.listTicket(page, count);
				pageTicket.forEach(item -> item.getUser().setPassword(null));
				response.setData(pageTicket);
			}else if(user.getProfile().equals(ProfileEnum.ROLE_CUSTOMER)) {
				Page<Ticket> pageTicket = this.ticketService.findByCurrentUser(page, count, user.getId());
				pageTicket.forEach(item -> item.getUser().setPassword(null));
				response.setData(pageTicket);
			}
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	@GetMapping("{page}/{count}/{number}/{title}/{status}/{priority}/{assigned}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
	public ResponseEntity<Response<Page<Ticket>>> findByParams(HttpServletRequest request, 
														@PathVariable("page") Integer page, 
														@PathVariable("count") Integer count, 
														@PathVariable("number") Integer number, 
														@PathVariable("title") String title, 
														@PathVariable("status") String status, 
														@PathVariable("priority") String priority,
														@PathVariable("assigned") Boolean assigned){
		
		title = title.equals("uninformed") ? "" : title;
		status = status.equals("uninformed") ? "" : status;
		priority = priority.equals("uninformed") ? "" : priority;
		//Boolean assigned = false;
		
		Response<Page<Ticket>> response = new Response<>();
		
		try {
			if(number > 0) {
				System.out.println("FindByNumber");
				Page<Ticket> pagesTicket = this.ticketService.findByNumber(page, count, number);
				pagesTicket.forEach(item -> item.getUser().setPassword(null));
				response.setData(pagesTicket);
			}else {
				User user = this.userFromRequest(request);
				if(user.getProfile().equals(ProfileEnum.ROLE_TECHNICIAN)) {
					if(assigned) {
						System.out.println("FindByParamaters TECHNICIAN with assigned");
						Page<Ticket> pagesTicketAssigned = this.ticketService
										.findByParametersAndAssignedUser(page, count, title, status, priority, user.getId());
						pagesTicketAssigned.forEach(item -> item.getUser().setPassword(null));
						response.setData(pagesTicketAssigned);
					}else {
						System.out.println("FindByParamaters TECHNICIAN without assigned");
						Page<Ticket> pagesTicket = this.ticketService.findByParameters(page, count, title, status, priority);
						pagesTicket.forEach(item -> item.getUser().setPassword(null));
						response.setData(pagesTicket);
					}
				} else if (user.getProfile().equals(ProfileEnum.ROLE_CUSTOMER)) {
					System.out.println("FindByParamaters CUSTOMER");
					Page<Ticket> pagesTicketCustomer = this.ticketService
												.findByParametersAndCurrentUser(page, count, title, status, priority, user.getId());
					pagesTicketCustomer.forEach(item -> item.getUser().setPassword(null));
					response.setData(pagesTicketCustomer);
				}
			}
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "{id}/{status}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
	public ResponseEntity<Response<Ticket>> changeStatus(@PathVariable("id") String id, 
														@PathVariable("status") String status, 
														HttpServletRequest request, 
														@RequestBody Ticket ticket, 
														BindingResult result){
		Response<Ticket> response = new Response<>();
		System.out.println("Starter PUT");
		
		try {
			this.validateChangeStatus(id, status, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.addError(error.getDefaultMessage()));
			}
			if(!response.hasErrors()) {
				Ticket ticketCurrent = this.ticketService.findById(id);
				ticketCurrent.setStatus(StatusEnum.getStatusEnum(status));
				if(status.equalsIgnoreCase("Assigned")) {
					ticketCurrent.setAssignedUser(this.userFromRequest(request));
				}
				Ticket tickerPersisted = this.ticketService.createOrUpdate(ticketCurrent);
				ChangeStatus changeStatus = new ChangeStatus();
				changeStatus.setUser(userFromRequest(request));
				changeStatus.setDateChangeStatus(new Date());
				changeStatus.setTicket(tickerPersisted);
				this.ticketService.createChangeStatus(changeStatus);
				response.setData(tickerPersisted);
			}			
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<Response<Summary>> findSummary(){
		Response<Summary> response = new Response<>();
		Summary summary = new Summary();
		int amountNew = 0;
		int amountResolved = 0;
		int amountApproved = 0;
		int amountDisapproved = 0;
		int amountAssigned = 0;
		int amountClosed = 0;
		
		try {
			Iterable<Ticket> tickets = this.ticketService.findAll();
			if(tickets != null) {
				for(Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
					Ticket ticket = iterator.next();
					if(ticket.getStatus().equals(StatusEnum.New)) {
						amountNew++;
					}
					if(ticket.getStatus().equals(StatusEnum.Resolved)) {
						amountApproved++;
					}
					if(ticket.getStatus().equals(StatusEnum.Approved)) {
						amountResolved++;
					}
					if(ticket.getStatus().equals(StatusEnum.Disapproved)) {
						amountDisapproved++;
					}
					if(ticket.getStatus().equals(StatusEnum.Assigned)) {
						amountAssigned++;
					}
					if(ticket.getStatus().equals(StatusEnum.Closed)) {
						amountClosed++;
					}
				}
			}
			summary.setAmountNew(amountNew);
			summary.setAmountResolved(amountResolved);
			summary.setAmountApproved(amountApproved);
			summary.setAmountDisapproved(amountDisapproved);
			summary.setAmountAssigned(amountAssigned);
			summary.setAmountClosed(amountClosed);
			response.setData(summary);
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id){
		Response<String> response = new Response<>();
		
		try {
			Ticket ticketFounded = this.ticketService.findById(id);
			if(ticketFounded == null || ticketFounded.getId() == null) {
				response.addError("Ticket not found with ID: " + id);
			}
			if(!response.hasErrors()) {
				this.ticketService.delete(id);
				response.setData(String.format("Ticket com ID %s excluido com sucesso.", id));
			}
		}catch(Exception e) {
			response.getErrors().add(e.getMessage());
		}
		
		if(response.hasErrors())
			return ResponseEntity.badRequest().body(response);
		else
			return ResponseEntity.ok(response);
	}
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}

	private void validateCreateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getTitle() == null || ticket.getTitle().trim() == "") {
			result.addError(new ObjectError("Title", "Title is required"));
		}
	}
	
	private void validateUpdateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getId() == null || ticket.getId().trim() == "") {
			result.addError(new ObjectError("Id", "Id is required"));
		}
		if(ticket.getTitle() == null || ticket.getTitle().trim() == "") {
			result.addError(new ObjectError("Title", "Title is required"));
		}
	}
	
	private void validateChangeStatus(String id, String satus, BindingResult result) {
		if(id == null || id.trim().isEmpty()) {
			result.addError(new ObjectError("Ticket", "Id no information"));
		}
		if(satus == null || satus.trim().isEmpty()) {
			result.addError(new ObjectError("Ticket", "Status no information"));
		}
	}
	
	private User userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String email = this.jwtTokenUtil.getUsernameFromToken(token);
		return this.userService.findByEmail(email);
	}
	
}
