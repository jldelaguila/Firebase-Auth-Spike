package com.everis.data.network.firebase

import com.google.firebase.auth.FirebaseUser

import rx.Observable


interface FireBaseAuth {

    fun createUser(email: String, password: String): Observable<FirebaseUser>
    fun signInUser(email: String, password: String): Observable<FirebaseUser>
    fun observeAuthStateChange(): Observable<FirebaseUser>
    fun logOut(): Observable<Void>
    fun deleteUser(): Observable<Void>
    fun checkUserLoggedIn(): Observable<FirebaseUser>

}
