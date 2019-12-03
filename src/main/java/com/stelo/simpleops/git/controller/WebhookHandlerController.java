package com.stelo.simpleops.git.controller;

import com.stelo.simpleops.git.enums.EventsType;
import com.stelo.simpleops.git.enums.RepoProviderEnum;
import com.stelo.simpleops.git.handler.WebhookHandler;
import com.stelo.simpleops.project.domain.Project;
import com.stelo.simpleops.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookHandlerController {

    @Resource
    private ProjectService projectService;

    @Resource
    private WebhookHandler webhookHandler;

    private final Map<String, EventsType> githubEventsParseMap;

    public WebhookHandlerController() {
        this.githubEventsParseMap = new HashMap<>();
        githubEventsParseMap.put("push", EventsType.PUSH_EVENT);
        githubEventsParseMap.put("pull_request", EventsType.PULL_REQUEST_EVENT);
        githubEventsParseMap.put("issues", EventsType.ISSUES_EVENT);
    }

    @PostMapping("/github/{secret}")
    public ResponseEntity<String> githubWebhookHandler(@RequestHeader("X-GitHub-Event") String eventType, @PathVariable("secret") String secret, @RequestBody String payload) {
        Project project = projectService.queryProjectBySecret(secret);
        if (project == null) {
            return ResponseEntity.badRequest().body("Project dose not exists");
        }
        EventsType eventsType = githubEventsParseMap.get(eventType);
        if (eventsType==null) {
            return ResponseEntity.ok("Success");
        }
        webhookHandler.processWebhookPayload(project, RepoProviderEnum.GITHUB, eventsType, payload);
        return ResponseEntity.ok("Success");
    }
}
