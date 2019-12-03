package com.stelo.simpleops.common.security.filter;

import com.stelo.simpleops.utils.LanguageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Resource
    private LanguageHelper languageHelper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(),languageHelper.getMessage(exception.getMessage()));
    }
}
