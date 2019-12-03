package com.stelo.simpleops.project.controller;

import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.common.enums.BusinessError;
import com.stelo.simpleops.common.exception.BusinessException;
import com.stelo.simpleops.common.security.utils.SessionHelper;
import com.stelo.simpleops.project.domain.Project;
import com.stelo.simpleops.project.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/private/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return Optional.ofNullable(projectService.createProject(project))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException("project.create.error", BusinessError.GENERAL_CREATE_ERR));
    }

    @GetMapping
    public ResponseEntity<PageInfo<Project>> pageQueryProjectByUserId(@RequestParam("page") int page, @RequestParam("rows") int rows) {
        return Optional.ofNullable(projectService.pageQueryProjectByUserId(SessionHelper.getCurrentLoginUserId(), page, rows))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException("project.query.error", BusinessError.GENERAL_CREATE_ERR));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProjectByProjectId(@PathVariable("projectId") Long projectId, @RequestParam("projectName") String projectName) {
        return Optional.ofNullable(projectService.updateProject(projectId, projectName))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException("project.query.error", BusinessError.GENERAL_CREATE_ERR));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteProjectByProjectId(@PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
