package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.UserRepository;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public class SignIn extends UseCase{

    private UserRepository repository;
    private String email;
    private String password;

    public SignIn(PostExecutorThread postExecutorThread, UserRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    public void bindParams(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.signInUser(this.email, this.password);
    }
}
