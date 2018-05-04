package com.everis.data.repository;

import com.everis.data.model.IntroMessageEntity;
import com.everis.data.model.IntroMessageEntityDataMapper;
import com.everis.data.network.firebase.FirebaseDBImpl;
import com.everis.domain.model.IntroMessage;
import com.everis.domain.model.LocalContact;
import com.everis.domain.model.P2PUser;
import com.everis.domain.repository.DatabaseRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
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

    @Override
    public Observable<P2PUser> getUserByPhoneReference(String phoneNumber) {

        return database.observeValueEvent(FirebaseDatabase.getInstance().getReference().child("p2p_users").child(phoneNumber)).map(
                new Func1<DataSnapshot, P2PUser>() {
                    @Override
                    public P2PUser call(DataSnapshot snapshot) {
                        return snapshot.getValue(P2PUser.class);
                    }
                }
        );
    }

    @Override
    public Observable<P2PUser> getUserByPhoneQuery(String phoneNumber) {
        return database.observeValueEvent(FirebaseDatabase.getInstance().getReference().child("p2p_users").orderByChild("phoneNumber").equalTo(phoneNumber)).map(new Func1<DataSnapshot, P2PUser>() {
            @Override
            public P2PUser call(DataSnapshot snapshot) {
                return snapshot.getValue(P2PUser.class);
            }
        });
    }

    @Override
    public Observable<P2PUser> syncUserContacts(List<LocalContact> localContacts, boolean isByQuery) {
        if(isByQuery) {
            return database.observeBatchContactsValueEventListener(localContacts, isByQuery).map(new Func1<DataSnapshot, P2PUser>() {
                @Override
                public P2PUser call(DataSnapshot snapshot) {
                    return snapshot.getChildren().iterator().next().getValue(P2PUser.class);
                }
            });
        }
        else{
            return database.observeBatchContactsValueEventListener(localContacts, isByQuery).map(new Func1<DataSnapshot, P2PUser>() {
                @Override
                public P2PUser call(DataSnapshot snapshot) {
                    if(snapshot.exists())
                        return snapshot.getValue(P2PUser.class);
                    else{
                        P2PUser inactiveUser = new P2PUser();
                        inactiveUser.setPhoneNumber(snapshot.getKey());
                        return inactiveUser;
                    }
                }
            });
        }
    }

    @Override
    public Observable<Void> observeRemoveValue(final String collection, final String innerNode) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                FirebaseDatabase.getInstance().getReference().child(collection).child(innerNode).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            subscriber.onError(databaseError.toException());
                        }
                        else{
                            subscriber.onNext(null);
                        }
                    }
                });
            }
        });
    }

}
