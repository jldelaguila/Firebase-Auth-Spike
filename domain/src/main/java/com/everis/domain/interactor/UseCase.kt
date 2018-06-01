package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread

import java.util.concurrent.TimeUnit

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

abstract class UseCase<T>(private val postExecutorThread: PostExecutorThread) {

    private var subscription = Subscriptions.empty()

    protected abstract fun buildUseCaseObservable(): Observable<T>

    fun execute(subscriber: Subscriber<T>) {
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutorThread.scheduler)
                .subscribe(subscriber)
    }

    fun unsuscribe() {
        if (!subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

}
