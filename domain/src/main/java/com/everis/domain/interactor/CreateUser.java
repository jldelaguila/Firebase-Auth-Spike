package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.UserRepository;

import rx.Observable;

/**
 * Created by everis on 25/04/18.
 */

public class CreateUser extends UseCase {

    private UserRepository repository;
    private String email;
    private String password;

    public CreateUser(PostExecutorThread postExecutorThread, UserRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    public void bindParams(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.createUser(this.email, this.password);
    }
}
