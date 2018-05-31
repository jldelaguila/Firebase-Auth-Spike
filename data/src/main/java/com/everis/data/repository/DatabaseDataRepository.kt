package com.everis.data.repository

import com.everis.data.model.IntroMessageEntity
import com.everis.data.model.IntroMessageEntityDataMapper
import com.everis.data.network.firebase.FirebaseDBImpl
import com.everis.domain.model.IntroMessage
import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser
import com.everis.domain.repository.DatabaseRepository

import com.google.firebase.database.FirebaseDatabase

import rx.Observable

class DatabaseDataRepository : DatabaseRepository {

    private val database: FirebaseDBImpl = FirebaseDBImpl()

    override fun getIntroMessage(): Observable<IntroMessage> {
        return database.observeValueEvent(FirebaseDatabase.getInstance().reference.child("intro_message").child("asd123")).map { dataSnapshot ->
            val introMessageEntity = dataSnapshot.getValue(IntroMessageEntity::class.java)
            introMessageEntity!!.uid = dataSnapshot.key
            IntroMessageEntityDataMapper.transform(introMessageEntity)
        }
    }

    override fun getUserEnabled(userUid: String): Observable<Boolean> {
        return database.observeValueEvent(FirebaseDatabase.getInstance().reference.child("structure").child("users").child(userUid)).map { dataSnapshot ->
            if (dataSnapshot.exists()) {
                dataSnapshot.child("enable").value as Boolean
            } else
                false
        }
    }

    override fun getUserByPhoneReference(phoneNumber: String): Observable<P2PUser> {

        return database.observeValueEvent(FirebaseDatabase.getInstance().reference.child("p2p_users").child(phoneNumber)).map { snapshot -> snapshot.getValue(P2PUser::class.java) }
    }

    override fun getUserByPhoneQuery(phoneNumber: String): Observable<P2PUser> {
        return database.observeValueEvent(FirebaseDatabase.getInstance().reference.child("p2p_users").orderByChild("phoneNumber").equalTo(phoneNumber)).map { snapshot -> snapshot.getValue(P2PUser::class.java) }
    }

    override fun observeRemoveValue(collection: String, node: String): Observable<Void> {
        return Observable.create { subscriber ->
            FirebaseDatabase.getInstance().reference.child(collection).child(node).removeValue { databaseError, databaseReference ->
                if (databaseError != null) {
                    subscriber.onError(databaseError.toException())
                } else {
                    subscriber.onNext(null)
                }
            }
        }
    }

    override fun syncUserContacts(localContacts: List<LocalContact>, isByQuery: Boolean): Observable<P2PUser> {
        return if (isByQuery) {
            database.observeBatchContactsValueEventListener(localContacts, isByQuery).map { snapshot -> snapshot.children.iterator().next().getValue(P2PUser::class.java) }
        } else {
            database.observeBatchContactsValueEventListener(localContacts, isByQuery).map { snapshot ->
                if (snapshot.exists())
                    snapshot.getValue(P2PUser::class.java)
                else {
                    val inactiveUser = P2PUser()
                    inactiveUser.phoneNumber = snapshot.key
                    inactiveUser
                }
            }
        }
    }
}
