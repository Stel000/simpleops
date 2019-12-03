package com.stelo.simpleops.common.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisAuthorizationManager extends AbstractAuthorizationManager {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void loginInner(long userId, String secret) {
        String key = generateKey(userId);
        redisTemplate.opsForValue().set(key, secret, properties.getDefaultTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void logout(long userId, String secret) {
        redisTemplate.delete(generateKey(userId));
    }

    private String getSecret(long userId) {
        String key = generateKey(userId);
        String secret = redisTemplate.opsForValue().get(key);
        redisTemplate.expire(key, properties.getDefaultTimeout(), TimeUnit.MILLISECONDS);
        return secret;
    }

    @Override
    public boolean validateInner(long userId, String secret) {
        return secret != null && secret.equals(getSecret(userId));
    }
}
