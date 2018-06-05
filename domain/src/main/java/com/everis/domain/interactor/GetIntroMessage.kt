package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.model.IntroMessage
import com.everis.domain.repository.DatabaseRepository

import rx.Observable

class GetIntroMessage(postExecutorThread: PostExecutorThread, private val repository: DatabaseRepository) : UseCase<IntroMessage>(postExecutorThread) {

    override fun buildUseCaseObservable(): Observable<IntroMessage> {
        return repository.getIntroMessage()
    }
}
