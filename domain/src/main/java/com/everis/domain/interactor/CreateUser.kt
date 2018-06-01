package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.User
import com.everis.domain.repository.UserRepository

import rx.Observable

class CreateUser(postExecutorThread: PostExecutorThread, private val repository: UserRepository) : UseCase<User>(postExecutorThread) {
    private var email: String? = null
    private var password: String? = null

    fun bindParams(email: String, password: String) {
        this.email = email
        this.password = password
    }

    override fun buildUseCaseObservable(): Observable<User> {
        return repository.createUser(this.email!!, this.password!!)
    }
}
