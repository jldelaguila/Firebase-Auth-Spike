package com.everis.authspike;

import com.everis.domain.executor.PostExecutorThread;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by everis on 25/04/18.
 */

public class UIThread implements PostExecutorThread {
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
