package com.gilson.helpdesk.api.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T data;
	
	private List<String> errors = new ArrayList<>();

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public Boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public void addError(String error) {
		this.errors.add(error);
	}
}