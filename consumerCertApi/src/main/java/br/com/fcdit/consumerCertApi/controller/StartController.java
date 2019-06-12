package br.com.fcdit.consumerCertApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.fcdit.consumerCertApi.prop.CertApiProperties;

@RestController
@RequestMapping("/api/start")
@CrossOrigin(origins="*")
public class StartController {
	
	@Autowired
	private RestTemplate restTempate;
	
	@Autowired
	private CertApiProperties prop;

	@GetMapping
	public ResponseEntity<String> getStart(){
		ResponseEntity<String> responseEntity = this.restTempate.getForEntity(prop.getUriStart(), String.class);
		String body = responseEntity.getBody();
		
		return ResponseEntity.ok(body);
	}
}
