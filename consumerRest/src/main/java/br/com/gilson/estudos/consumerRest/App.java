package br.com.gilson.estudos.consumerRest;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import br.com.gilson.estudos.consumerRest.api.entity.Quote;
import br.com.gilson.estudos.consumerRest.api.entity.ResponseFace;
import br.com.gilson.estudos.consumerRest.api.entity.UserFace;
import br.com.gilson.estudos.consumerRest.api.entity.VersionFace;

@SpringBootApplication
public class App 
{
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    	SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
    	        .loadTrustMaterial(null, acceptingTrustStrategy)
    	        .build();

    	SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    	CloseableHttpClient httpClient = HttpClients.custom()
    	        .setSSLSocketFactory(csf)
    	        .build();

    	HttpComponentsClientHttpRequestFactory requestFactory =
    	        new HttpComponentsClientHttpRequestFactory(httpClient);

    	RestTemplate restTemplate = builder.build();
    	restTemplate.setRequestFactory(requestFactory);
    	return restTemplate;
    }
    
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception{
    	return args -> {
    		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
    		log.info(quote.toString());
    		
    		HttpHeaders header = new HttpHeaders();
    		header.add("Accept", "application/json");
    		HttpEntity<ResponseFace<VersionFace>> entity = new HttpEntity<>(header);
			ResponseEntity<ResponseFace<VersionFace>> version = restTemplate.exchange("https://192.168.34.23:8443/Face/version", HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseFace<VersionFace>>() {});
			ResponseFace<VersionFace> resultVersion = version.getBody();
    		log.info("Version: " + resultVersion.getResult().getVersion());
    		
    		header.add("Content-Type", "application/x-www-form-urlencoded");
    		HttpEntity<String> entityPost = new HttpEntity<>("userEmail=integracao@bgpeps.local&password=Oracle@300", header);
    		ResponseEntity<ResponseFace<UserFace>> postToken = restTemplate.exchange("https://192.168.34.23:8443/Face/login", HttpMethod.POST, entityPost, new ParameterizedTypeReference<ResponseFace<UserFace>>() {});
    		ResponseFace<UserFace> userFace = postToken.getBody();
    		log.info("Token: " + userFace.getResult().toString());
    	};
    }
}
