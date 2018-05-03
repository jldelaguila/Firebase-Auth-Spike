package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.model.P2PUser;
import com.everis.domain.repository.DatabaseRepository;

import rx.Observable;
import rx.functions.Func1;

public class SyncUser extends UseCase {

    private DatabaseRepository repository;
    private String phoneNumber;

    public SyncUser(PostExecutorThread postExecutorThread, DatabaseRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    public void bindParams(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.getUserByPhoneQuery(this.phoneNumber);
    }
}
