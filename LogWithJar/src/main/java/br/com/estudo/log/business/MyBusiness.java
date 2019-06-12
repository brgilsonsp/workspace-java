package br.com.estudo.log.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyBusiness {
	
	private final Logger log = LogManager.getLogger(MyBusiness.class);
	
	public MyBusiness() {
		log.info("Made MyBusiness");
	}

}
