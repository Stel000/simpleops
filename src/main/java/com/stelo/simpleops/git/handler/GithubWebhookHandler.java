package com.stelo.simpleops.git.handler;

import com.stelo.simpleops.git.domain.PushEvent;
import com.stelo.simpleops.git.enums.EventsType;
import com.stelo.simpleops.git.enums.RepoProviderEnum;
import com.stelo.simpleops.git.service.PushEventService;
import com.stelo.simpleops.project.domain.Project;
import com.stelo.simpleops.project.enums.ProjectStatus;
import com.stelo.simpleops.project.service.ProjectService;
import com.stelo.simpleops.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class GithubWebhookHandler implements WebhookHandler, WebhookProcessor {

    @Resource
    private ProjectService projectService;

    @Resource
    private PushEventService pushEventService;


    @Override
    public void processWebhookPayload(Project project, RepoProviderEnum providerEnum, EventsType eventsType, String payload) {
        // Do nothing
    }

    @Override
    public void push(Project project, String payload) {
        JSONObject webhook = JsonUtils.getJsonObject(payload);
        if (projectService.queryProjectStatusByProjectId(project.getId()).equals(ProjectStatus.WAITING)) {
            completeProject(project, webhook);
        }
        PushEvent pushEvent = createPushEvent(project, webhook);
        log.debug("Process webhook:\n{}",webhook.toString());

    }

    private void completeProject(Project project, JSONObject webhook) {
        try {
            String eid = webhook.getJSONObject("repository").getString("id");
            String url = webhook.getJSONObject("repository").getString("html_url");
            String gitUrl = webhook.getJSONObject("repository").getString("git_url");
            String cloneUrl = webhook.getJSONObject("repository").getString("clone_url");
            String ownerName = webhook.getJSONObject("repository").getJSONObject("owner").getString("name");
            String ownerAvatar = webhook.getJSONObject("repository").getJSONObject("owner").getString("avatar_url");
            project.setEid(eid);
            project.setUrl(url);
            project.setGitUrl(gitUrl);
            project.setCloneUrl(cloneUrl);
            project.setOwnerName(ownerName);
            project.setOwnerAvatar(ownerAvatar);
            project.setStatus(ProjectStatus.NORMAL);
            projectService.completeProject(project);
        } catch (JSONException e) {
            log.error("Complete project [{}] error because webhook parse failed", project.getId());
            throw new IllegalArgumentException("Complete project information error");
        }
    }

    private PushEvent createPushEvent(Project project, JSONObject webhook) {
        try {
            PushEvent pushEvent = PushEvent.builder()
                    .projectId(project.getId()).eventSource(RepoProviderEnum.GITHUB)
                    .headCommitHash(webhook.getJSONObject("head_commit").getString("id"))
                    .headCommitMessage(webhook.getJSONObject("head_commit").getString("message"))
                    .headCommitUrl(webhook.getJSONObject("head_commit").getString("url"))
                    .headCommitter(webhook.getJSONObject("head_commit").getJSONObject("committer").getString("username"))
                    .ref(webhook.getString("ref"))
                    .lastCommitHash(webhook.getString("before"))
                    .createdDate(new Date())
                    .payload(webhook.toString()).build();
            return pushEventService.createPushEvent(pushEvent);
        } catch (JSONException e) {
            log.error("Parse webhook [{}] error because", webhook.toString(),e);
            throw new IllegalArgumentException("Complete project information error");
        }
    }

    @Override
    public void pullRequest(Project project, String payload) {
        JSONObject webhook = JsonUtils.getJsonObject(payload);
        if (projectService.queryProjectStatusByProjectId(project.getId()).equals(ProjectStatus.WAITING)) {
            completeProject(project, webhook);
        }
        log.warn("Github pull request webhook process was WIP");
    }

    @Override
    public void issues(Project project, String payload) {
        JSONObject webhook = JsonUtils.getJsonObject(payload);
        if (projectService.queryProjectStatusByProjectId(project.getId()).equals(ProjectStatus.WAITING)) {
            completeProject(project, webhook);
        }
        log.warn("Github issues webhook process was WIP");
    }
}
