package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.repository.UserRepository

import rx.Observable

class LogOut(postExecutorThread: PostExecutorThread, private val repository: UserRepository) : UseCase<Void>(postExecutorThread) {

    override fun buildUseCaseObservable(): Observable<Void> {
        return repository.logOut()
    }
}
