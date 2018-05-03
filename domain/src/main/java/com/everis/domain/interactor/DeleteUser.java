package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.UserRepository;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public class DeleteUser extends UseCase {

    private UserRepository repository;

    public DeleteUser(PostExecutorThread postExecutorThread, UserRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.deleteUser();
    }
}
