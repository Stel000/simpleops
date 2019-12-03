package com.stelo.simpleops.project.service;

import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.project.domain.Project;
import com.stelo.simpleops.project.enums.ProjectStatus;

public interface ProjectService {

    Project createProject(Project project);

    PageInfo<Project> pageQueryProject(int page, int rows);

    PageInfo<Project> pageQueryProjectByUserId(long userId, int page, int rows);

    boolean secretProjectExists(String secret);

    Project queryProjectBySecret(String secret);

    ProjectStatus queryProjectStatusByProjectId(Long projectId);

    Project completeProject(Project project);

    Project updateProject(long projectId, String projectName);

    void deleteProject(long projectId);
}
