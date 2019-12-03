package com.stelo.simpleops.git.domain;

import java.util.Date;
import javax.persistence.*;

import com.stelo.simpleops.build.enums.BuildStatus;
import com.stelo.simpleops.git.enums.RepoProviderEnum;
import com.stelo.simpleops.utils.BlobStringTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.ColumnType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "push_event")
public class PushEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "event_source")
    private RepoProviderEnum eventSource;

    @Column(name = "head_commit_hash")
    private String headCommitHash;

    @Column(name = "head_commit_message")
    private String headCommitMessage;

    @Column(name = "head_commit_url")
    private String headCommitUrl;

    @Column(name = "head_committer")
    private String headCommitter;

    @Column(name = "`ref`")
    private String ref;

    @Column(name = "last_commit_hash")
    private String lastCommitHash;

    @Column(name = "`status`")
    private BuildStatus status;

    @Column(name = "created_date")
    private Date createdDate;

    @ColumnType(column = "payload", typeHandler = BlobStringTypeHandler.class)
    private String payload;

    public static PushEventBuilder builder() {
        return new PushEventBuilder();
    }
}