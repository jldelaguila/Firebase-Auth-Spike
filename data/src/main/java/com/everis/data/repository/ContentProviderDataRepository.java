package com.everis.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.everis.data.model.LocalContactEntity;
import com.everis.data.model.LocalContactEntityDataMapper;
import com.everis.data.network.local.SpikeContentProvider;
import com.everis.domain.model.LocalContact;
import com.everis.domain.repository.ContentProviderRepository;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by everis on 3/05/18.
 */

import android.content.Context;

public class ContentProviderDataRepository implements ContentProviderRepository {

    private SpikeContentProvider database;

    public ContentProviderDataRepository(SpikeContentProvider database) {
        this.database = database;
    }

    @Override
    public Observable<List<LocalContact>> getLocalContactsBatch() {
        return database.getLocalContatsBatch().map(new Func1<List<LocalContactEntity>, List<LocalContact>>() {
            @Override
            public List<LocalContact> call(List<LocalContactEntity> localContactEntities) {

                List<LocalContact> localContacts = new ArrayList<>();

                for(LocalContactEntity contactEntity : localContactEntities){
                    LocalContact localContact = LocalContactEntityDataMapper.transform(contactEntity);
                    localContacts.add(localContact);
                }

                return localContacts;
            }
        });
    }
}
