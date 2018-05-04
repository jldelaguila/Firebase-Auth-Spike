package com.everis.data.network.firebase;

import com.everis.domain.model.LocalContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public interface FirebaseDB {

    Observable<DataSnapshot> observeValueEvent(DatabaseReference reference);
    Observable<DataSnapshot> observeValueEvent(Query reference);
    Observable<DataSnapshot> observeBatchContactsValueEventListener(List<LocalContact> contacts, boolean byQuery);

}
