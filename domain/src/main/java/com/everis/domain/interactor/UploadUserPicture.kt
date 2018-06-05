package com.everis.domain.interactor

import com.everis.domain.executor.PostExecutorThread
import com.everis.domain.repository.UserRepository
import rx.Observable
import java.io.InputStream

class UploadUserPicture(postExecutorThread: PostExecutorThread, private val repository: UserRepository) : UseCase<String>(postExecutorThread) {

    private var id: String? = null
    private var stream: InputStream? = null

    fun bindParams(email: String, stream: InputStream) {
        this.id = email
        this.stream = stream
    }

    override fun buildUseCaseObservable(): Observable<String> {
        return repository.uploadPicture(id!!,stream!!)
    }
}