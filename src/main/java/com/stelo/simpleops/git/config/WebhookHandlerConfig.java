package com.stelo.simpleops.git.config;

import com.stelo.simpleops.git.enums.RepoProviderEnum;
import com.stelo.simpleops.git.handler.DelegatingWebhookHandler;
import com.stelo.simpleops.git.handler.GithubWebhookHandler;
import com.stelo.simpleops.git.handler.WebhookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class WebhookHandlerConfig {

    @Resource
    private GithubWebhookHandler githubWebhookHandler;

    @Bean
    public WebhookHandler webhookHandler() {
        Map<RepoProviderEnum, WebhookHandler> handlerMap = new EnumMap<>(RepoProviderEnum.class);
        handlerMap.put(RepoProviderEnum.GITHUB, githubWebhookHandler);
        return new DelegatingWebhookHandler(handlerMap);
    }
}
