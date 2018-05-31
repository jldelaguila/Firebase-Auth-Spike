package com.everis.domain.repository

import com.everis.domain.model.IntroMessage
import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser

import rx.Observable

interface DatabaseRepository {

    fun getIntroMessage(): Observable<IntroMessage>
    fun getUserEnabled(userUid: String): Observable<Boolean>
    fun getUserByPhoneReference(phoneNumber: String): Observable<P2PUser>
    fun getUserByPhoneQuery(phoneNumber: String): Observable<P2PUser>
    fun syncUserContacts(localContacts: List<LocalContact>, isByQuery: Boolean): Observable<P2PUser>
    fun observeRemoveValue(collection: String, node: String): Observable<Void>

}
