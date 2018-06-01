package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.User
import com.everis.domain.repository.UserRepository

import rx.Observable

class CheckLoggedIn(postExecutorThread: PostExecutorThread, private val repository: UserRepository) : UseCase<User>(postExecutorThread) {

    override fun buildUseCaseObservable(): Observable<User> {
        return repository.checkUserLoggedIn()
    }
}
