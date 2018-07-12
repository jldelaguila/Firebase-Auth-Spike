package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.P2PUser
import com.everis.domain.repository.DatabaseRepository

import rx.Observable

class GetUserByPhoneReference(postExecutorThread: PostExecutorThread, private val repository: DatabaseRepository) : UseCase<P2PUser>(postExecutorThread) {
    private var phoneNumber: String? = null

    fun bindParams(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    override fun buildUseCaseObservable(): Observable<P2PUser> {
        return this.repository.getUserByPhoneReference(this.phoneNumber!!)
    }
}
