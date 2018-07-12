package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.repository.DatabaseRepository

import rx.Observable

class GetEnabledUser(postExecutorThread: PostExecutorThread, private val repository: DatabaseRepository) : UseCase<Boolean>(postExecutorThread) {
    private var userUid: String? = null

    fun bindParams(userUid: String) {
        this.userUid = userUid
    }

    override fun buildUseCaseObservable(): Observable<Boolean> {
        return repository.getUserEnabled(this.userUid!!)
    }
}
