package br.com.fcdit.consumerCertApi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntryData implements Serializable {


	@JsonIgnore
	private static final long serialVersionUID = -4474282104963532456L;

	@JsonProperty("value_api")
	private String valueApi;
	
	@JsonProperty("data_one")
	private String dataOne;
	
	@JsonProperty("data_two")
	private String dataTwo;

	public String getValueApi() {
		return valueApi;
	}

	public void setValueApi(String valueApi) {
		this.valueApi = valueApi;
	}

	public String getDataOne() {
		return dataOne;
	}

	public void setDataOne(String dataOne) {
		this.dataOne = dataOne;
	}

	public String getDataTwo() {
		return dataTwo;
	}

	public void setDataTwo(String dataTwo) {
		this.dataTwo = dataTwo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntryData [valueApi= ");
		builder.append(valueApi);
		builder.append(", dataOne= ");
		builder.append(dataOne);
		builder.append(", dataTwo= ");
		builder.append(dataTwo);
		builder.append("]");
		
		return builder.toString();
	}
}
