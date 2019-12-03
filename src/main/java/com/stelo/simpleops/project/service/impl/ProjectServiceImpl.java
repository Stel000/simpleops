package com.stelo.simpleops.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.common.enums.BusinessError;
import com.stelo.simpleops.common.exception.BusinessException;
import com.stelo.simpleops.common.security.utils.SessionHelper;
import com.stelo.simpleops.project.domain.Project;
import com.stelo.simpleops.project.enums.ProjectStatus;
import com.stelo.simpleops.project.mapper.ProjectMapper;
import com.stelo.simpleops.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public Project createProject(Project project) {
        project.setUrl(null);
        project.setSecret(project.getSecret());
        project.setEid(null);
        project.setOwnerName(null);
        project.setOwnerAvatar(null);
        project.setCloneUrl(null);
        project.setGitUrl(null);
        project.setCurrentEventStatus(null);
        project.setCurrentBuildStatus(null);
        project.setUserId(SessionHelper.getCurrentLoginUserId());
        project.setSecret(UUID.randomUUID().toString().replace("-",""));
        project.setStatus(ProjectStatus.WAITING);
        try {
            projectMapper.insertSelective(project);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("project.create.error.retry", BusinessError.GENERAL_SERVER_ERR);
        }
        return project;
    }

    @Override
    public PageInfo<Project> pageQueryProject(int page, int rows) {
        return PageHelper.startPage(page, rows).doSelectPageInfo(() -> projectMapper.selectAll());
    }

    @Override
    public PageInfo<Project> pageQueryProjectByUserId(long userId, int page, int rows) {
        return PageHelper.startPage(page, rows).doSelectPageInfo(() -> projectMapper.selectByUserId(userId));
    }

    @Override
    public boolean secretProjectExists(String secret) {
        return projectMapper.countBySecret(secret).equals(1L);
    }

    @Override
    public Project queryProjectBySecret(String secret) {
        return projectMapper.selectBySecret(secret);
    }

    @Override
    public ProjectStatus queryProjectStatusByProjectId(Long projectId) {
        return projectMapper.selectStatusById(projectId);
    }

    @Override
    public Project completeProject(Project project) {
        projectMapper.updateByPrimaryKeySelective(project);
        return project;
    }

    @Override
    public Project updateProject(long projectId, String projectName) {
        Project project = projectMapper.selectByPrimaryKey(projectId);
        if (!project.getUserId().equals(SessionHelper.getCurrentLoginUserId())) {
            throw new BusinessException("project.update.no_authorize", BusinessError.GENERAL_FORBID_ERR);
        }
        project.setName(projectName);
        projectMapper.updateByPrimaryKeySelective(project);
        return project;
    }

    @Override
    public void deleteProject(long projectId) {
        Project project = projectMapper.selectByPrimaryKey(projectId);
        if (!project.getUserId().equals(SessionHelper.getCurrentLoginUserId())) {
            throw new BusinessException("project.delete.no-permission", BusinessError.GENERAL_FORBID_ERR);
        }
        projectMapper.deleteByPrimaryKey(projectId);
    }
}
