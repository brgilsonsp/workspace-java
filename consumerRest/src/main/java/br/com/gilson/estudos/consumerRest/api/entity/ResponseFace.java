package br.com.gilson.estudos.consumerRest.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseFace <T> {

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	/*private UserFace result;

	public UserFace getResult() {
		return result;
	}

	public void setResult(UserFace result) {
		this.result = result;
	}*/
	
	@Override
	public String toString() {
		return "result: " + result.toString();
	}
}
