package com.stelo.simpleops.git.handler;

import com.stelo.simpleops.project.domain.Project;

/**
 * Method name must match with the value property of EventsType and with parameter (Project project, String payload)
 */
public interface WebhookProcessor {
    void push(Project project, String payload);

    void pullRequest(Project project, String payload);

    void issues(Project project, String payload);
}
