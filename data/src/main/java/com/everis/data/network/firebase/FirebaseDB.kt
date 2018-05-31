package com.everis.data.network.firebase

import com.everis.domain.model.LocalContact
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

import rx.Observable

interface FirebaseDB {

    fun observeValueEvent(reference: DatabaseReference): Observable<DataSnapshot>
    fun observeValueEvent(reference: Query): Observable<DataSnapshot>
    fun observeBatchContactsValueEventListener(contacts: List<LocalContact>, byQuery: Boolean): Observable<DataSnapshot>

}
