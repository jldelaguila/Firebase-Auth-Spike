package com.everis.authspike

import com.everis.domain.executor.PostExecutorThread

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by everis on 25/04/18.
 */

class UIThread : PostExecutorThread {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
