package br.com.fcdit.testcertapi.controller;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fcdit.testcertapi.entity.EntryData;
import br.com.fcdit.testcertapi.entity.ResponseApi;

@RestController
@RequestMapping("/api/start")
@CrossOrigin(origins="*")
public class StartApiController {
	
	private final Logger logger = LogManager.getLogger(StartApiController.class);

	@GetMapping
	public ResponseEntity<ResponseApi<String>> getStartWhitoutParameter(){
		
		ResponseApi<String> response = new ResponseApi<>();
		response.setMessage("Success GET whitout parameter");
		response.setDataReturn("No data to return");
				
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path="/param", params= {"first"})
	public ResponseEntity<ResponseApi<String>> getStartWhitParameter(String first, @RequestParam("second") String second){
		
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("getStartWhitParameter: {");
		logBuilder.append("first: ");
		logBuilder.append(first);
		logBuilder.append(", second: ");
		logBuilder.append(second);
		logBuilder.append("}");
		this.logger.info(logBuilder.toString());
		
		ResponseApi<String> response = new ResponseApi<>();
		response.setMessage("Success GET whit parameter");
		StringBuilder builder = new StringBuilder();
		builder.append("Received parameter first with value: '");
		builder.append(first);
		builder.append(", ");
		builder.append(second);
		builder.append("'");
		
		response.setDataReturn(builder.toString());
				
		return ResponseEntity.ok(response);
	}
	
	@PostMapping()
	public ResponseEntity<ResponseApi<EntryData>> postMapping(@RequestBody EntryData entry){
		
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("postMapping: ");
		logBuilder.append(entry.toString());
		this.logger.info(logBuilder.toString());
		
		entry.setValueApi("New Value API " + new Random().nextInt());
		
		ResponseApi<EntryData> response = new ResponseApi<>();
		response.setMessage("Success POST");
		response.setDataReturn(entry);
				
		return ResponseEntity.ok(response);
	}
}
