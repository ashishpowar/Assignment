package com.amdocs.media.assignment.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final String username;
    

    public String getUsername() {
		return username;
	}

	public AuthenticationResponse(String jwt,String username) {
        this.jwt = jwt;
        this.username=username;
    }

    public String getJwt() {
        return jwt;
    }
}
