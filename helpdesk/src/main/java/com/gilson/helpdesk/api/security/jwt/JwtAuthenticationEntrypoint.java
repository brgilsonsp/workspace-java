package com.gilson.helpdesk.api.security.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//Trata erros de autenticação
@Component
public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint, Serializable{

	private static final long serialVersionUID = -2540215220562918225L;

	@Override
	public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}
