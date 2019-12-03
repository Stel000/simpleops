package com.stelo.simpleops.git.service;

import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.git.domain.PushEvent;

public interface PushEventService {

    PushEvent createPushEvent(PushEvent pushEvent);

    PushEvent queryPushEventById(Long id);

    PageInfo<PushEvent> pageQueryPushEventByProjectId(Long projectId);
}
