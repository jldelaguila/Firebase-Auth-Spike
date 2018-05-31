package com.everis.data.network.firebase

import android.util.Log

import com.everis.domain.model.LocalContact
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

import rx.Observable

class FirebaseDBImpl : FirebaseDB {
    override fun observeValueEvent(reference: DatabaseReference): Observable<DataSnapshot> {
        return Observable.create { subscriber ->
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        subscriber.onNext(dataSnapshot)
                    } else {
                        subscriber.onError(Throwable("No existe el snapshot"))
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    subscriber.onError(databaseError.toException())
                }
            })
        }
    }

    override fun observeValueEvent(reference: Query): Observable<DataSnapshot> {
        return Observable.create { subscriber ->
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        subscriber.onNext(dataSnapshot)
                    } else {
                        subscriber.onError(Throwable("No existe el snapshot"))
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //default implementation
                }
            })
        }
    }

    override fun observeBatchContactsValueEventListener(contacts: List<LocalContact>, byQuery: Boolean): Observable<DataSnapshot> {

        return Observable.create { subscriber ->
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("FirebaseBatch", "Se recibio el cambio de un usuario")
                    if (dataSnapshot.exists()) {
                        subscriber.onNext(dataSnapshot)
                    } else {
                        Log.d("KEYS NULLOS?", dataSnapshot.key)
                        subscriber.onNext(dataSnapshot)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    subscriber.onError(Throwable("Se cancelo la consulta p2p"))
                }
            }

            for (localContact in contacts) {
                if (byQuery) {
                    val query = FirebaseDatabase.getInstance().reference.child("p2p_users").orderByChild("phoneNumber").equalTo(localContact.number)
                    query.addListenerForSingleValueEvent(listener)
                } else {
                    val ref = FirebaseDatabase.getInstance().reference.child("p2p_users").child(localContact.number)
                    Log.d("REFERENCEQ", ref.toString())
                    ref.addValueEventListener(listener)
                }

                Log.d("FirebaseBatch", localContact.name)
            }
        }
    }
}
