package com.stelo.simpleops.common.security.service;


public interface AuthorizationManager {

    void login(long userId, String secret);

    void logout(long userId, String secret);

    boolean validate(long userId, String secret);
}
