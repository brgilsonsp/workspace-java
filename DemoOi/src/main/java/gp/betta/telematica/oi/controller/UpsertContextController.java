package gp.betta.telematica.oi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gp.betta.telematica.oi.entity.Pesquisa;
import gp.betta.telematica.oi.entity.Response;

@RestController
@RequestMapping("/upsert/")
@CrossOrigin(origins = "*")
public class UpsertContextController {
	
	private Logger logger = LogManager.getLogger(UpsertContextController.class);
	
	@GetMapping()
	public ResponseEntity<Response> getTest(){
		this.logger.info("======================Starter======================================");
		Response retorno = new Response();
		retorno.setMessage("Acessou o get");
		this.logger.info(retorno.getMessage());
		this.logger.info("======================End======================================");
		return ResponseEntity.ok(retorno);
	}

	@PutMapping("{contextId}")
	public ResponseEntity<Response> upsert(@PathVariable String contextId, @RequestBody Pesquisa pesquisa){
		this.logger.info("======================Starter======================================");
		
		Response retorno = new Response();
		
		try {
			if(pesquisa != null && pesquisa.getEmpresa() != null) {
				String.format("ID: %s /n%s", contextId, pesquisa);
				retorno.setMessage(pesquisa + " - ContextID: " + contextId);				
			}else {
				retorno.addError("Dados em branco");
			}
		}catch(Exception e) {
			retorno.addError("Exception: " + e.getMessage());			
		}
		
		if(retorno.hasError()) {
			retorno.getErrors().forEach(item -> {
				this.logger.error(item);
			});
			this.logger.info("======================End======================================");
			return ResponseEntity.badRequest().body(retorno);
		}else {
			this.logger.info(retorno.getMessage());
			this.logger.info("======================End======================================");
			return ResponseEntity.ok(retorno);
		}
	}
}
