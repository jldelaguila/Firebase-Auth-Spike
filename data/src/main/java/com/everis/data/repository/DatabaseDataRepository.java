package com.everis.data.repository;

import com.everis.data.model.IntroMessageEntity;
import com.everis.data.model.IntroMessageEntityDataMapper;
import com.everis.data.network.firebase.FirebaseDBImpl;
import com.everis.domain.model.IntroMessage;
import com.everis.domain.repository.DatabaseRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by everis on 26/04/18.
 */

public class DatabaseDataRepository implements DatabaseRepository{

    private FirebaseDBImpl database;

    public DatabaseDataRepository() {
        this.database = new FirebaseDBImpl();
    }

    @Override
    public Observable<IntroMessage> getIntroMessage() {
        return database.observeValueEvent(FirebaseDatabase.getInstance().getReference().child("intro_message").child("asd123")).map(new Func1<DataSnapshot, IntroMessage>() {
            @Override
            public IntroMessage call(DataSnapshot dataSnapshot) {
                IntroMessageEntity introMessageEntity = dataSnapshot.getValue(IntroMessageEntity.class);
                introMessageEntity.setUid(dataSnapshot.getKey());
                return IntroMessageEntityDataMapper.transform(introMessageEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> getUserEnabled(String userUid) {
        return database.observeValueEvent(FirebaseDatabase.getInstance().getReference().child("structure").child("users").child(userUid)).map(new Func1<DataSnapshot, Boolean>() {
            @Override
            public Boolean call(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    HashMap<String, Object> userValue = (HashMap<String, Object>) dataSnapshot.getValue();
                    return (Boolean) userValue.get("enable");
                }
                else
                    return false;
            }
        });
    }

}
