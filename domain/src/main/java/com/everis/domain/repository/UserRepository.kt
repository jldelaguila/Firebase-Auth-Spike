package com.everis.domain.repository

import com.everis.domain.model.User

import rx.Observable

/**
 * Created by everis on 25/04/18.
 */

interface UserRepository {

    fun createUser(email: String, password: String): Observable<User>
    fun signInUser(email: String, password: String): Observable<User>
    fun checkUserLoggedIn(): Observable<User>
    fun logOut(): Observable<Void>
    fun deleteUser(): Observable<Void>
    fun getUserState(): Observable<User>

}
