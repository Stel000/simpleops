package com.stelo.simpleops.common.security.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
class Secret {
    private String secret;
    private long expiredTime;
}

public class SimpleAuthorizationManager extends AbstractAuthorizationManager {
    private Map<String, Secret> map = new ConcurrentHashMap<>();

    @Override
    public void loginInner(long userId, String secret) {
        Secret secretItem = new Secret(secret, System.currentTimeMillis() + properties.getDefaultTimeout());
        map.put(generateKey(userId), secretItem);
    }

    @Override
    public void logout(long userId, String secret) {
        map.remove(generateKey(userId));
    }

    private String getSecret(long userId) {
        String key = generateKey(userId);
        if (!map.containsKey(key)) {
            return null;
        }
        Secret secret = map.get(key);
        if (System.currentTimeMillis() > secret.getExpiredTime()) {
            map.remove(key);
            return null;
        }
        secret.setExpiredTime(System.currentTimeMillis() + properties.getDefaultTimeout());
        map.put(key, secret);
        return secret.getSecret();
    }

    @Override
    public boolean validateInner(long userId, String secret) {
        return secret != null && secret.equals(getSecret(userId));
    }
}
