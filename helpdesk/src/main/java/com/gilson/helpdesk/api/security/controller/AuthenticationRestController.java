package com.gilson.helpdesk.api.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gilson.helpdesk.api.entity.User;
import com.gilson.helpdesk.api.security.jwt.JwtAuthenticationRequest;
import com.gilson.helpdesk.api.security.jwt.JwtTokenUtil;
import com.gilson.helpdesk.api.security.jwt.model.CurrentUser;
import com.gilson.helpdesk.api.service.UserService;

//Classe utilizada para autenticação, controller
@RestController
@CrossOrigin(origins = "*") //permite o acesso de qualquer porta/IP que fizer a requisição
public class AuthenticationRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	//Solicita o token
	@PostMapping(value="/api/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final User user = userService.findByEmail(authenticationRequest.getEmail());
        user.setPassword(null);
        
        return ResponseEntity.ok(new CurrentUser(token, user));
    }

	//atualiza o token
	@PostMapping(value = "/api/refresh")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws Exception{
		String token = request.getHeader("Authorization");
		String username = this.jwtTokenUtil.getUsernameFromToken(token);
		final User user = this.userService.findByEmail(username);
		if(this.jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = this.jwtTokenUtil.refreshToken(token);
			user.setPassword(null);
			return ResponseEntity.ok(new CurrentUser(refreshedToken, user));
		}else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
}
