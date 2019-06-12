package br.com.estudo.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.estudo.log.business.MyBusiness;

public class ClassMain {

	protected final static Logger log = LogManager.getLogger(ClassMain.class);
	
	public static void main(String[] args) {
		log.info("Access to main");
		MyBusiness business = new MyBusiness();
	}

}
