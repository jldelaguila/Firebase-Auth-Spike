package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.DatabaseRepository;

import rx.Observable;

/**
 * Created by everis on 27/04/18.
 */

public class GetEnabledUser extends UseCase {

    private DatabaseRepository repository;
    private String userUid;

    public GetEnabledUser(PostExecutorThread postExecutorThread, DatabaseRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    public void bindParams(String userUid){
        this.userUid = userUid;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.getUserEnabled(this.userUid);
    }
}
