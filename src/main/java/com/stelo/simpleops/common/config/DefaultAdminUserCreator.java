package com.stelo.simpleops.common.config;

import com.stelo.simpleops.common.domain.User;
import com.stelo.simpleops.common.properties.SimpleOpsProperties;
import com.stelo.simpleops.common.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableConfigurationProperties(SimpleOpsProperties.class)
public class DefaultAdminUserCreator implements ApplicationRunner {

    @Resource
    private UserService userService;

    @Resource
    private SimpleOpsProperties simpleOpsProperties;

    private static final String DEFAULT_ADMIN_USER_NAME = "admin";

    private static final String DEFAULT_ADMIN_USER_NICK = "admin";

    @Override
    public void run(ApplicationArguments args) {
        if (!isDefaultAdminUserExists()) {
            User user = new User();
            user.setUsername(DEFAULT_ADMIN_USER_NAME);
            user.setNickname(DEFAULT_ADMIN_USER_NICK);
            user.setPassword(simpleOpsProperties.getAdminPassword());
            userService.registerUser(user);
        }
    }

    public boolean isDefaultAdminUserExists() {
        return userService.queryUserByUserName("admin") != null;
    }
}
