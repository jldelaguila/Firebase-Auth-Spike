package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.LocalContact
import com.everis.domain.repository.ContentProviderRepository

import rx.Observable

class GetLocalContactsBatch(postExecutorThread: PostExecutorThread, private val repository: ContentProviderRepository) : UseCase<List<@JvmSuppressWildcards LocalContact>>(postExecutorThread) {

    override fun buildUseCaseObservable(): Observable<List<@JvmSuppressWildcards LocalContact>> {
        return repository.getLocalContactsBatch()
    }
}
