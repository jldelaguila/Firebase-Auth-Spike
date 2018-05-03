package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.repository.ContentProviderRepository;

import rx.Observable;

/**
 * Created by everis on 3/05/18.
 */

public class GetLocalContactsBatch extends UseCase {

    private ContentProviderRepository repository;

    public GetLocalContactsBatch(PostExecutorThread postExecutorThread, ContentProviderRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.getLocalContactsBatch();
    }
}
