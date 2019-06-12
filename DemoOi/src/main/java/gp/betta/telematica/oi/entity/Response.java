package gp.betta.telematica.oi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {

	private static final long serialVersionUID = 6462200630341218629L;

	
	private String message;
	
	private List<String> errors = new ArrayList<>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public boolean hasError() {
		return errors.size() > 0;
	}
	
}
