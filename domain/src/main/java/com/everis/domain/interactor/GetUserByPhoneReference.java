package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.DatabaseRepository;

import rx.Observable;

/**
 * Created by everis on 3/05/18.
 */

public class GetUserByPhoneReference extends UseCase {

    private DatabaseRepository repository;
    private String phoneNumber;

    public GetUserByPhoneReference(PostExecutorThread postExecutorThread, DatabaseRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    public void bindParams(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.getUserByPhoneReference(this.phoneNumber);
    }
}
