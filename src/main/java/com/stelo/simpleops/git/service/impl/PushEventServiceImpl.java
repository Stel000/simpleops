package com.stelo.simpleops.git.service.impl;

import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.git.domain.PushEvent;
import com.stelo.simpleops.git.service.PushEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushEventServiceImpl implements PushEventService {
    @Override
    public PushEvent createPushEvent(PushEvent pushEvent) {

        return null;
    }

    @Override
    public PushEvent queryPushEventById(Long id) {
        return null;
    }

    @Override
    public PageInfo<PushEvent> pageQueryPushEventByProjectId(Long projectId) {
        return null;
    }
}
