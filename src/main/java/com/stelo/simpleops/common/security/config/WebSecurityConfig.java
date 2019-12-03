package com.stelo.simpleops.common.security.config;

import com.stelo.simpleops.common.security.filter.AuthenticationFailureHandlerImpl;
import com.stelo.simpleops.common.security.filter.AuthenticationSuccessHandlerImpl;
import com.stelo.simpleops.common.security.filter.JwtAuthenticationFilter;
import com.stelo.simpleops.common.security.properties.SecurityProperties;
import com.stelo.simpleops.common.security.service.AuthorizationManager;
import com.stelo.simpleops.common.security.utils.JwtUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.SessionManagementFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout().disable()
                .addFilterBefore(jwtAuthenticationFilter(), SessionManagementFilter.class).anonymous().disable()
                .csrf().disable();
    }

    @Bean
    public JwtUtils jwtUtils(SecurityProperties properties) {
        JwtUtils utils = new JwtUtils();
        utils.setProperties(properties);
        return utils;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager providerManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(SecurityProperties properties, AuthorizationManager authorizationManager) {
        return new AuthenticationSuccessHandlerImpl(jwtUtils(properties), authorizationManager);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }
}
