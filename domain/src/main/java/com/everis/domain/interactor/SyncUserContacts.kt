package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser
import com.everis.domain.repository.DatabaseRepository
import java.util.concurrent.TimeUnit

import rx.Observable
import rx.functions.Func1

class SyncUserContacts(postExecutorThread: PostExecutorThread, private val repository: DatabaseRepository) : UseCase<P2PUser>(postExecutorThread) {
    private var queryContacts: List<LocalContact>? = null
    private var byQuery: Boolean = false

    fun bindParams(queryContacts: List<LocalContact>, byQuery: Boolean) {
        this.queryContacts = queryContacts
        this.byQuery = byQuery
    }

    override fun buildUseCaseObservable(): Observable<P2PUser> {
        return this.repository.syncUserContacts(this.queryContacts!!, this.byQuery).onBackpressureBuffer()
    }
}
