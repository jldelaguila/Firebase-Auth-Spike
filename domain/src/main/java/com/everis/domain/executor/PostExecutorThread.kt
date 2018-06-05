package com.everis.domain.executor

import rx.Scheduler

interface PostExecutorThread {

    fun getScheduler() : Scheduler

}
