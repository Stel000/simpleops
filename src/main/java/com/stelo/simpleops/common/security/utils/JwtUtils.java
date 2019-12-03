package com.stelo.simpleops.common.security.utils;

import com.stelo.simpleops.common.security.properties.SecurityProperties;
import com.stelo.simpleops.common.security.entity.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils implements InitializingBean {

    private SecurityProperties properties;

    private Key key;

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_NAME = "realm";
    public static final String KEY_RAND = "randNum";

    @Resource
    public void setProperties(SecurityProperties securityProperties) {
        this.properties = securityProperties;
    }


    public String createUserToken(UserDetailsImpl userDetails, String secret) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(KEY_USER_ID, userDetails.getId());
        dataMap.put(KEY_USER_NAME, userDetails.getUsername());
        dataMap.put(KEY_RAND, secret);
        return createToken(dataMap);
    }

    public UserDetailsImpl parseUser(String token) {
        try {
            Map<String, Object> map = parseToken(token);
            return UserDetailsImpl.builder()
                    .id(Long.valueOf(map.get(KEY_USER_ID).toString()))
                    .username(map.get(KEY_USER_NAME).toString())
                    .password("")
                    .build();
        } catch (NullPointerException e) {
            if (log.isDebugEnabled()) {
                log.error("token validation failed with missing required fields");
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("parse token fail, {}", e);
            }
        }
        return null;
    }

    public UserDetailsImpl parseUser(Map<String, Object> map) {
        try {
            return UserDetailsImpl.builder()
                    .id(Long.valueOf(map.get(KEY_USER_ID).toString()))
                    .username(map.get(KEY_USER_NAME).toString())
                    .password("")
                    .build();
        } catch (NullPointerException e) {
            if (log.isDebugEnabled()) {
                log.error("token validation failed with missing required fields");
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("parse token fail, {}", e);
            }
        }
        return null;
    }

    String createToken(Map<String, Object> dataMap) {
        return Jwts.builder().setClaims(dataMap).signWith(key).compact();
    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    @Override
    public void afterPropertiesSet() {
        byte[] bytes = Base64.getDecoder().decode(properties.getJwtSecretKey());
        this.key = Keys.hmacShaKeyFor(bytes);
    }
}
