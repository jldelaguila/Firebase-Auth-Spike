package com.everis.data.network.firebase;

import android.util.Log;

import com.everis.domain.model.LocalContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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

    @Override
    public Observable<DataSnapshot> observeValueEvent(final Query reference) {
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
                        //default implementation
                    }
                });
            }
        });
    }

    @Override
    public Observable<DataSnapshot> observeBatchContactsValueEventListener(final List<LocalContact> contacts, final boolean byQuery) {

        return Observable.create(new Observable.OnSubscribe<DataSnapshot>() {
            @Override
            public void call(final Subscriber<? super DataSnapshot> subscriber) {

                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("FirebaseBatch", "Se recibio el cambio de un usuario");
                        if(dataSnapshot.exists()){
                            subscriber.onNext(dataSnapshot);
                        }
                        else{
                            subscriber.onNext(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        subscriber.onError(new Throwable("Se cancelo la consulta p2p"));
                    }
                };

                for(LocalContact localContact : contacts){
                    if(byQuery){
                        Query query = FirebaseDatabase.getInstance().getReference().child("p2p_users").orderByChild("phoneNumber").equalTo(localContact.getNumber());
                        query.addListenerForSingleValueEvent(listener);
                    }
                    else{
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("p2p_users").child(localContact.getNumber());
                        Log.d("REFERENCEQ", ref.toString());
                        ref.addValueEventListener(listener);
                    }

                    Log.d("FirebaseBatch", localContact.getName());
                }
            }
        });
    }
}
