package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.UserRepository;

import rx.Observable;
import rx.Subscription;

/**
 * Created by everis on 25/04/18.
 */

public class GetUserState extends UseCase {

    private UserRepository repository;

    public GetUserState(PostExecutorThread postExecutorThread, UserRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.getUserState();
    }
}
