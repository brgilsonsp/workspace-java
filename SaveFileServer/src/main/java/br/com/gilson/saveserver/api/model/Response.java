package br.com.gilson.saveserver.api.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Response<T> {

	private T data;
	
	private List<Map<String, String>> errors = new ArrayList<>();
	
	public void addError(String key, String value) {
		Map<String, String> error = new LinkedHashMap<>();
		error.put(key, value);
		errors.add(error);
	}
	
	public Boolean hashError() {
		return this.errors.size() > 0;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Map<String, String>> getErrors() {
		return Collections.unmodifiableList(errors);
	}

}
