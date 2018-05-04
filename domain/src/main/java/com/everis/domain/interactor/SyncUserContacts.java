package com.everis.domain.interactor;

import com.everis.domain.executor.PostExecutorThread;
import com.everis.domain.model.LocalContact;
import com.everis.domain.model.P2PUser;
import com.everis.domain.repository.DatabaseRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class SyncUserContacts extends UseCase {

    private DatabaseRepository repository;
    private List<LocalContact> queryContacts;
    private boolean byQuery;

    public SyncUserContacts(PostExecutorThread postExecutorThread, DatabaseRepository repository) {
        super(postExecutorThread);
        this.repository = repository;
    }

    public void bindParams(List<LocalContact> queryContacts, boolean byQuery){
        this.queryContacts = queryContacts;
        this.byQuery = byQuery;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.repository.syncUserContacts(this.queryContacts, this.byQuery).onBackpressureBuffer();
    }
}
