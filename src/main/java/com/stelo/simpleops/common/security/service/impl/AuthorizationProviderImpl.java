package com.stelo.simpleops.common.security.service.impl;

import com.stelo.simpleops.common.domain.User;
import com.stelo.simpleops.common.security.entity.LoginAuthenticationToken;
import com.stelo.simpleops.common.security.entity.UserDetailsImpl;
import com.stelo.simpleops.common.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthorizationProviderImpl extends AbstractUserDetailsAuthenticationProvider {
    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    protected UserDetailsImpl retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String credentials = authentication.getCredentials().toString();
        User user = userService.queryUserByUserNameNotNull(username);
        if (user == null) {
            throw new UsernameNotFoundException("user.error.no_exist");
        }
        if (!passwordEncoder.matches(credentials, user.getPassword())) {
            throw new BadCredentialsException("error.user.login.password");
        }
        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(username)
                .password("")
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        // Do nothing
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        LoginAuthenticationToken result = (LoginAuthenticationToken) authentication;
        result.setDetails(user);
        return result;
    }
}
