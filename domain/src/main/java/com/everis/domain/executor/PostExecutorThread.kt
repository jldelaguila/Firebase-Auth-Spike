package com.everis.domain.executor

import rx.Scheduler

interface PostExecutorThread {

    val scheduler: Scheduler

}
