package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.repository.DatabaseRepository
import rx.Observable

class UpdateUser(postExecutorThread: PostExecutorThread, private val repository: DatabaseRepository) : UseCase<Unit>(postExecutorThread) {

    private var phoneNumber: String? = null
    private var url: String? = null

    fun bindParams(phoneNumber: String, url: String) {
        this.phoneNumber = phoneNumber
        this.url = url
    }

    override fun buildUseCaseObservable(): Observable<Unit> {
        return this.repository.updateUser(url!!, phoneNumber!!)
    }
}
