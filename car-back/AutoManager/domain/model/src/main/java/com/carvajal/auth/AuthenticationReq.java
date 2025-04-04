package com.carvajal.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

    public AuthenticationReq(String email, String password) {
        this.email = email;
        this.password = password;
    }
}