package com.stelo.simpleops.common.security.entity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class RequestAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private long userId;

    public RequestAuthenticationToken(Object principal, Object credentials, final long userId) {
        super(principal, credentials);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
