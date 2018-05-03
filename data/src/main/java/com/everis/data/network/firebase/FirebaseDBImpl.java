package com.everis.data.network.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by everis on 26/04/18.
 */

public class FirebaseDBImpl implements FirebaseDB {
    @Override
    public Observable<DataSnapshot> observeValueEvent(final DatabaseReference reference) {
        return Observable.create(new Observable.OnSubscribe<DataSnapshot>() {
            @Override
            public void call(final Subscriber<? super DataSnapshot> subscriber) {

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            subscriber.onNext(dataSnapshot);
                        }
                        else{
                            subscriber.onError(new Throwable("No existe el snapshot"));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        subscriber.onError(databaseError.toException());
                    }
                });
            }
        });
    }
}
