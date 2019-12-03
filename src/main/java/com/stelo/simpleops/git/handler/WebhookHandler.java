package com.stelo.simpleops.git.handler;

import com.stelo.simpleops.git.enums.EventsType;
import com.stelo.simpleops.git.enums.RepoProviderEnum;
import com.stelo.simpleops.project.domain.Project;

public interface WebhookHandler {
    void processWebhookPayload(Project project, RepoProviderEnum providerEnum, EventsType eventsType, String payload);
}
