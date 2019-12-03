package com.stelo.simpleops.common.security.service.impl;

import com.stelo.simpleops.common.domain.User;
import com.stelo.simpleops.common.security.entity.RequestAuthenticationToken;
import com.stelo.simpleops.common.security.entity.UserDetailsImpl;
import com.stelo.simpleops.common.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsAuthenticationProiderImpl extends AbstractUserDetailsAuthenticationProvider {

    @Resource
    private UserService userService;

    @Override
    protected UserDetailsImpl retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
        RequestAuthenticationToken token = (RequestAuthenticationToken) authentication;
                User user = userService.queryUserByIdNotNull(token.getUserId());
                return UserDetailsImpl.builder()
                        .id(user.getId())
                        .username(username)
                        .password(user.getPassword())
                        .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RequestAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {

    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        RequestAuthenticationToken result = (RequestAuthenticationToken) authentication;
        result.setDetails(user);
        return result;
    }
}
