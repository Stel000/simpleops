package com.stelo.simpleops.common.security.properties;

import com.stelo.simpleops.common.security.enums.Encoder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> allowedOrigins;
    private long defaultTimeout = 1000 * 60 * 60 * 2;
    private String jwtSecretKey;
    private Encoder passwordEncoder;
    private String authUrl = "/public/auth";

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public long getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public Encoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(Encoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }
}
