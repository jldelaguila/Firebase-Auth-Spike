package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.User
import com.everis.domain.repository.UserRepository

import rx.Observable
import rx.Subscription

class GetUserState(postExecutorThread: PostExecutorThread, private val repository: UserRepository) : UseCase<User>(postExecutorThread) {

    override fun buildUseCaseObservable(): Observable<User> {
        return repository.getUserState()
    }
}
