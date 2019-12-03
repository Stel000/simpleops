CREATE TABLE `project`
(
    `id`                   bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_id`              bigint(20)   NOT NULL,
    `name`                 varchar(64)  NOT NULL DEFAULT '',
    `url`                  varchar(512) NOT NULL DEFAULT '',
    `secret`               varchar(64)  NOT NULL,
    `eid`                  varchar(16)  NOT NULL DEFAULT '',
    `owner_name`           varchar(256) NOT NULL DEFAULT '',
    `owner_avatar`         varchar(512) NOT NULL DEFAULT '',
    `clone_url`            varchar(256) NOT NULL DEFAULT '',
    `git_url`              varchar(256) NOT NULL DEFAULT '',
    `repo_access_token`    varchar(256) NOT NULL DEFAULT '',
    `current_event_status` tinyint(4)   NOT NULL DEFAULT '0',
    `current_build_status` tinyint(4)   NOT NULL DEFAULT '0',
    `status`               tinyint(4)   NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user`
(
    `id`       bigint(20)   NOT NULL AUTO_INCREMENT,
    `username` varchar(40)  NOT NULL,
    `nickname` varchar(40)  NOT NULL DEFAULT '',
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `push_event`
(
    `id`                  bigint(20)   NOT NULL AUTO_INCREMENT,
    `project_id`          bigint(20)   NOT NULL,
    `event_source`        tinyint(4)   NOT NULL DEFAULT '0',
    `head_commit_hash`    varchar(40)  NOT NULL,
    `head_commit_message` varchar(512) NOT NULL,
    `head_commit_url`     varchar(512) NOT NULL,
    `head_committer`      varchar(128) NOT NULL,
    `ref`                 varchar(512) NOT NULL,
    `last_commit_hash`    varchar(40)  NOT NULL,
    `payload`             blob,
    `status`              tinyint(4)   NOT NULL DEFAULT '0',
    `created_date`        timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `build_info`
(
    `id`             bigint(20)   NOT NULL,
    `project_id`     bigint(20)   NOT NULL,
    `registry_id`    varchar(128)   NOT NULL,
    `build_nodes`    varchar(128) NOT NULL,
    `dockerfile`     varchar(512) NOT NULL DEFAULT  '',
    `tag`            varchar(128) NOT NULL,
    `use_cache`      tinyint(4)   NOT NULL DEFAULT '1',
    `always_pull`    tinyint(4)   NOT NULL DEFAULT '0',
    `build_args`     blob,
    `extra_registry` varchar(128) NOT NULL DEFAULT '',
    `auto_build`     tinyint(4)   NOT NULL DEFAULT '0',
    `create_date`    timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    UNIQUE KEY `tag_UNIQUE` (`tag`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `build_node` (
  `id` bigint(20) NOT NULL,
  `nick` varchar(64) NOT NULL,
  `host` varchar(512) NOT NULL,
  `tls` tinyint(4) NOT NULL DEFAULT '0',
  `tls_cert_path` varchar(512) NOT NULL,
  `max_build_task_count` int(8) NOT NULL,
  `current_build_task_count` int(8) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `host_UNIQUE` (`host`),
  UNIQUE KEY `tls_cert_path_UNIQUE` (`tls_cert_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `registry` (
  `id` bigint(20) NOT NULL,
  `nick` varchar(64) NOT NULL,
  `url` varchar(512) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL DEFAULT '',
  `create_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `simops`.`user`
    ADD UNIQUE INDEX `username_UNIQUE` (`username` ASC),
    ADD UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC);

ALTER TABLE `simops`.`project`
    ADD UNIQUE INDEX `secret_UNIQUE` (`secret` ASC);
