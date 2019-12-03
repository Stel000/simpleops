package com.stelo.simpleops.project.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.stelo.simpleops.build.enums.BuildStatus;
import com.stelo.simpleops.git.enums.EventStatus;
import com.stelo.simpleops.project.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(name = "`name`")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "secret")
    private String secret;

    @Column(name = "eid")
    private String eid;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_avatar")
    private String ownerAvatar;

    @Column(name = "clone_url")
    private String cloneUrl;

    @Column(name = "git_url")
    private String gitUrl;

    @NotBlank
    @Column(name = "repo_access_token")
    private String repoAccessToken;

    @Column(name = "current_event_status")
    private EventStatus currentEventStatus;

    @Column(name = "current_build_status")
    private BuildStatus currentBuildStatus;

    @Column(name = "`status`")
    private ProjectStatus status;
}