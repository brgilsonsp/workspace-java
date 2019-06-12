package br.com.dds.demobotscope.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/botscope")
@CrossOrigin(origins = "*")
public class ReceiveMessage {

	@PostMapping
	public ResponseEntity<String> postTalk(@RequestBody String talk){
		if(talk != null && talk.length() > 0) {
			System.out.println("Sucesso: " + talk);
			return ResponseEntity.ok("Sucesso");
			//return ResponseEntity.badRequest().body("Error");
		}
		System.out.println("Não enviado");
		return ResponseEntity.badRequest().body("Error");
	}
}
