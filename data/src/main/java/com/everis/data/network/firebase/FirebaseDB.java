package com.everis.data.network.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public interface FirebaseDB {

    Observable<DataSnapshot> observeValueEvent(DatabaseReference reference);
    Observable<DataSnapshot> observeValueEvent(Query reference);

}
