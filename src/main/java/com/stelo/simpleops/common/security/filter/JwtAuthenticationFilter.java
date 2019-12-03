package com.stelo.simpleops.common.security.filter;

import com.stelo.simpleops.common.security.constants.SecurityContants;
import com.stelo.simpleops.common.security.entity.RequestAuthenticationToken;
import com.stelo.simpleops.common.security.entity.UserDetailsImpl;
import com.stelo.simpleops.common.security.service.AuthorizationManager;
import com.stelo.simpleops.common.security.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthorizationManager authorizationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authToken = getAuthToken(request);
        if (StringUtils.isEmpty(authToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Map<String, Object> map = jwtUtils.parseToken(authToken);
            String secret = map.getOrDefault(JwtUtils.KEY_RAND, null).toString();
            UserDetailsImpl userDetails = jwtUtils.parseUser(map);
            final long userId = userDetails.getId();
            if (!authorizationManager.validate(userId, secret)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "the user is not logged in");
                log.trace("the user userId={} is not logged in", userId);
                return;
            }
            RequestAuthenticationToken token = new RequestAuthenticationToken(
                    "",
                    "",
                    userId
            );
            token.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(request, response);
        } catch (Throwable e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            log.trace("The token is not valid, reason: {}", e);
        }
    }

    private String getAuthToken(HttpServletRequest request) {
        String authToken = null;
        String authorization = request.getHeader(SecurityContants.HEADER_STRING);
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith(SecurityContants.TOKEN_PREFIX)) {
            authToken = authorization.substring(7);
        }
        return authToken;
    }

}
