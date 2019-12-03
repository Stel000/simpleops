package com.stelo.simpleops.common.controller.login;


import com.stelo.simpleops.common.dto.UserLoginDto;
import com.stelo.simpleops.common.security.entity.LoginAuthenticationToken;
import com.stelo.simpleops.constants.ApiPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = ApiPrefix.APIV1)
@Slf4j
public class UserLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @PostMapping("/public/login")
    public void auth(@RequestBody @Valid UserLoginDto command) throws IOException, ServletException {
        String username = command.getUsername();
        String password = command.getPassword();
        auth(username, password);
    }


    private void auth(String username, String password) throws IOException, ServletException {
        LoginAuthenticationToken token = new LoginAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            successHandler.onAuthenticationSuccess(request, response, authentication);
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        } catch (Exception e) {
            log.error("username[{}] login fail, reason: {}", username, e);
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
