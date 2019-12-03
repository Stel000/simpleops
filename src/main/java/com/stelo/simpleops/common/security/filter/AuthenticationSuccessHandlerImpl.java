package com.stelo.simpleops.common.security.filter;

import com.stelo.simpleops.common.security.constants.SecurityContants;
import com.stelo.simpleops.common.security.entity.UserDetailsImpl;
import com.stelo.simpleops.common.security.service.AuthorizationManager;
import com.stelo.simpleops.common.security.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final AuthorizationManager authorizationManager;

    public AuthenticationSuccessHandlerImpl(JwtUtils jwtUtils, AuthorizationManager authorizationManager) {
        this.jwtUtils = jwtUtils;
        this.authorizationManager = authorizationManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getDetails();
        String secret = UUID.randomUUID().toString();
        String jwtToken = jwtUtils.createUserToken(user, secret);
        authorizationManager.login(user.getId(), secret);
        response.addHeader(SecurityContants.HEADER_STRING, SecurityContants.TOKEN_PREFIX + jwtToken);
    }
}
