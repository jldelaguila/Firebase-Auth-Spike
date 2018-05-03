package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by everis on 25/04/18.
 */

public abstract class UseCase {

    private Subscription subscription = Subscriptions.empty();
    private PostExecutorThread postExecutorThread;

    public UseCase(PostExecutorThread postExecutorThread) {
        this.postExecutorThread = postExecutorThread;
    }

    protected abstract Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute (Subscriber subscriber){
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutorThread.getScheduler())
                .subscribe(subscriber);
    }

    public void unsuscribe(){
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

}
