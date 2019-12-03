package com.stelo.simpleops.git.mapper;

import com.stelo.simpleops.git.domain.PushEvent;
import com.stelo.simpleops.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PushEventMapper extends MyMapper<PushEvent> {
}