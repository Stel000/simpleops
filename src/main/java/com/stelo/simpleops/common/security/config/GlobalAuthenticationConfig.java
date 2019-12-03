package com.stelo.simpleops.common.security.config;

import com.stelo.simpleops.common.security.service.impl.AuthorizationProviderImpl;
import com.stelo.simpleops.common.security.service.impl.UserDetailsAuthenticationProiderImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {
    @Resource
    private AuthorizationProviderImpl authorizationProvider;
    @Resource
    private UserDetailsAuthenticationProiderImpl userDetailsAuthenticationProider;


    @Override
    public void init(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(authorizationProvider)
                .authenticationProvider(userDetailsAuthenticationProider);
    }
}

