package com.stelo.simpleops.project.mapper;

import com.stelo.simpleops.project.domain.Project;
import com.stelo.simpleops.project.enums.ProjectStatus;import com.stelo.simpleops.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.util.List;

@Mapper
public interface ProjectMapper extends MyMapper<Project> {
    List<Project> selectByUserId(@Param("userId") Long userId);

    Project selectBySecret(@Param("secret") String secret);

    Long countBySecret(@Param("secret") String secret);

    ProjectStatus selectStatusById(@Param("id") Long id);
}