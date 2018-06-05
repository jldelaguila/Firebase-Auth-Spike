package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.IntroMessage
import com.everis.domain.repository.AppRepository
import com.everis.domain.repository.DatabaseRepository

import rx.Observable

class GetFilterListConfig(postExecutorThread: PostExecutorThread, private val repository: AppRepository) : UseCase<Boolean>(postExecutorThread) {

    override fun buildUseCaseObservable(): Observable<Boolean> {
        return repository.isFilteredList()
    }
}
