package com.stelo.simpleops.common.security.entity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public LoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
