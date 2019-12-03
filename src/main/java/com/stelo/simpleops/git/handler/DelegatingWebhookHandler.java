package com.stelo.simpleops.git.handler;

import com.stelo.simpleops.git.enums.EventsType;
import com.stelo.simpleops.git.enums.RepoProviderEnum;
import com.stelo.simpleops.project.domain.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public class DelegatingWebhookHandler implements WebhookHandler {

    private final EnumMap<RepoProviderEnum, WebhookHandler> webhookHandlerMap;

    public DelegatingWebhookHandler(Map<RepoProviderEnum, WebhookHandler> webhookHandlerMap) {
        this.webhookHandlerMap = (EnumMap<RepoProviderEnum, WebhookHandler>) webhookHandlerMap;
    }

    @Override
    public void processWebhookPayload(Project project, RepoProviderEnum providerEnum, EventsType eventsType, String payload) {
        if (providerEnum == null) {
            throw new IllegalArgumentException("Must specific repo provider");
        } else if (!webhookHandlerMap.containsKey(providerEnum)) {
            throw new IllegalArgumentException(providerEnum.getValue() + "is not found in webhook handler");
        }
        WebhookHandler webhookHandler = webhookHandlerMap.get(providerEnum);
        Class<? extends WebhookHandler> webhookHandlerClass = webhookHandler.getClass();
        try {
            // Invocation webhook process method with EventsType value
            Method eventProcessMethod = webhookHandlerClass.getMethod(eventsType.getValue(), Project.class, String.class);
            eventProcessMethod.invoke(webhookHandler, project, payload);
        } catch (NoSuchMethodException e) {
            log.warn("The handler {} dose not have method to process event {}", webhookHandlerClass.getSimpleName(), eventsType.getValue());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Webhook handler {} process event {} invocation error", webhookHandlerClass.getSimpleName(), eventsType.getValue(), e);
        }
    }
}
