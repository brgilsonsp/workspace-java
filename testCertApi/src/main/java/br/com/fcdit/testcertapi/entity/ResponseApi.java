package br.com.fcdit.testcertapi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseApi<T> implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = -2330670524634674449L;
	
	private String message;
	
	@JsonProperty("data_return")
	private T dataReturn;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getDataReturn() {
		return dataReturn;
	}

	public void setDataReturn(T dataReturn) {
		this.dataReturn = dataReturn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
