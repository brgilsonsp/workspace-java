package br.com.gilson.saveserver.api.controller;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.gilson.saveserver.api.model.Response;
import br.com.gilson.saveserver.api.service.MyFileService;

@RestController
@RequestMapping("/api/myfile/")
@CrossOrigin(origins ="*")
public class MyFileController {

	private MyFileService myFileService;
	
	@Value("${file.address.root}")
	private String ROOT;
	
	@Value("${file.address.resource}")
	private String RESOURCE;
	
	@Autowired
	public MyFileController(MyFileService myFileService) {
		this.myFileService = myFileService;
	}
	
	@PostMapping
	public ResponseEntity<Response<Map<String, String>>> upload(MultipartFile fileUpload, HttpServletRequest request){
		Response<Map<String, String>> response = new Response<>();
		
		if(fileUpload == null) {
			response.addError("File don't save", "File don't send");
		}
		if(!response.hashError()) {
			try {
				String fileName = fileUpload.getOriginalFilename();
				
				File directory = this.myFileService.getDirectory(ROOT, RESOURCE);
				File file = this.myFileService.buildFile(directory, fileName);
				this.myFileService.writeFile(file, fileUpload);
				String url = this.myFileService.buildUrlFileSaved(request, RESOURCE, fileName);
				
				Map<String, String> message = new LinkedHashMap<>();
				message.put("File saved", fileName);
				message.put("URL", url);
				response.setData(message);
				
			}catch(Exception e) {
				response.addError("Error while saving file", fileUpload.getOriginalFilename());
				response.addError("Exception", e.getMessage());
			}
		}
		
		if(response.hashError()) {
			return ResponseEntity.badRequest().body(response);
		}else {
			return ResponseEntity.ok(response);
		}
	}
}
