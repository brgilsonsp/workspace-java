package com.gilson.helpdesk.api.security.jwt;

import java.io.Serializable;

//Classe utilizada para autenticação, dados do usuário
public class JwtAuthenticationRequest implements Serializable{

	private static final long serialVersionUID = -2773578108046076244L;
	private String email;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}