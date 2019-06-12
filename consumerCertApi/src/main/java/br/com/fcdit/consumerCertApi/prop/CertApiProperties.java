package br.com.fcdit.consumerCertApi.prop;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.fcdit.consumerCertApi.entity.EntryData;

@Configuration
@ConfigurationProperties(value="config.certapi")
public class CertApiProperties {
	
	private String address;
	
	private String resourceWhitParameter;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResourceWhitParameter() {
		return resourceWhitParameter;
	}

	public void setResourceWhitParameter(String resourceWhitParameter) {
		this.resourceWhitParameter = resourceWhitParameter;
	}
	
	public URI getUriStart() {
		return URI.create(this.address);
	}
	
	public URI getUriParam() {
		return URI.create(this.address + this.resourceWhitParameter);
	}
	
	public HttpHeaders getHttpHeader() {
		MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
		values.add("Content-Type", "application/json");
		values.add("Accept", "application/json");
		
		HttpHeaders headers = new HttpHeaders();
		headers.addAll(values);
		
		return headers;
	}
	
	public HttpEntity<String> getHttpEntity() {
		HttpHeaders headers = this.getHttpHeader();
		return new HttpEntity<String>(headers);
	}
	
	public HttpEntity<EntryData> getHttpEntity(EntryData entyData) {
		HttpHeaders headers = this.getHttpHeader();
		return new HttpEntity<EntryData>(entyData, headers);
	}

}
