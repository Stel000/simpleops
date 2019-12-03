package com.stelo.simpleops.common.security.config;

import com.stelo.simpleops.common.security.enums.Encoder;
import com.stelo.simpleops.common.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PasswordEncoderConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(Encoder.BCRYPT.getCode(), new BCryptPasswordEncoder());
        encoders.put(Encoder.PBKDF2.getCode(), new Pbkdf2PasswordEncoder());
        encoders.put(Encoder.SCRYPT.getCode(), new SCryptPasswordEncoder());
        encoders.put(Encoder.SHA256.getCode(), new StandardPasswordEncoder());
        encoders.put(Encoder.MD5.getCode(), new MessageDigestPasswordEncoder(Encoder.MD5.getCode()));
        return new DelegatingPasswordEncoder(securityProperties.getPasswordEncoder().getCode(), encoders);
    }
}
