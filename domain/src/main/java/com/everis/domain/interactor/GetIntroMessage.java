package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.DatabaseRepository;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public class GetIntroMessage extends UseCase {

    private DatabaseRepository repository;

    public GetIntroMessage(PostExecutorThread postExecutorThread, DatabaseRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.getIntroMessage();
    }
}
