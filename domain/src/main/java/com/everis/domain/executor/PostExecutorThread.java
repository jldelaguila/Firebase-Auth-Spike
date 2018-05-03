package com.everis.domain.executor;

import rx.Scheduler;

/**
 * Created by everis on 25/04/18.
 */

public interface PostExecutorThread {

    Scheduler getScheduler();

}
