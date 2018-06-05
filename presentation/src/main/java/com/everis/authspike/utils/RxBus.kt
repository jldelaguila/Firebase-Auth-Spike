package com.everis.authspike.utils

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject

class RxBus
constructor() {

    private val mSubject = SerializedSubject(PublishSubject.create<Any>())

    fun send(o: Any) {
        mSubject.onNext(o)
    }

    fun toObservable(): Observable<Any> {
        return mSubject
    }
}

