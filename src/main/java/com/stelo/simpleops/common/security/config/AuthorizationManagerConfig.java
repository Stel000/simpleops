package com.stelo.simpleops.common.security.config;

import com.stelo.simpleops.common.security.service.AuthorizationManager;
import com.stelo.simpleops.common.security.service.impl.RedisAuthorizationManager;
import com.stelo.simpleops.common.security.service.impl.SimpleAuthorizationManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AuthorizationManagerConfig {
    @Bean
    @ConditionalOnMissingBean(AuthorizationManager.class)
    public AuthorizationManager simpleAuthorizationManager() {
        return new SimpleAuthorizationManager();
    }

    @ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis", matchIfMissing = true)
    @ConditionalOnClass(StringRedisTemplate.class)
    public static class RedisAuthorizationManagerConfig {
        @Bean
        public AuthorizationManager redisAuthorizationManager() {
            return new RedisAuthorizationManager();
        }
    }

}
