package com.stelo.simpleops.common.security.service.impl;

import com.stelo.simpleops.common.security.properties.SecurityProperties;
import com.stelo.simpleops.common.security.service.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAuthorizationManager implements AuthorizationManager {
    @Autowired
    protected SecurityProperties properties;

    protected static final String STORE_NAME = "login";

    @Override
    public void login(long userId, String secret) {
        loginInner(userId, secret);
    }

    protected abstract void loginInner(long userId, String secret);

    @Override
    public boolean validate(long userId, String secret) {
        return validateInner(userId, secret);
    }

    protected abstract boolean validateInner(long userId, String secret);

    final String generateKey(final long userId) {
        return String.format("%s:%s", STORE_NAME, userId);
    }
}
