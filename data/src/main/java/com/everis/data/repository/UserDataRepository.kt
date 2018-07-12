package com.everis.data.repository

import com.everis.data.model.FirebaseUserDataMapper
import com.everis.data.network.firebase.FireBaseAuth
import com.everis.data.network.firebase.FireBaseAuthImpl
import com.everis.data.network.firebase.FireBaseStorage
import com.everis.data.network.firebase.FireBaseStorageImpl
import com.everis.domain.model.User
import com.everis.domain.repository.UserRepository
import com.google.firebase.storage.FirebaseStorage

import rx.Observable
import java.io.InputStream

class UserDataRepository : UserRepository {


    private val database: FireBaseAuth = FireBaseAuthImpl()
    private val storage: FireBaseStorage = FireBaseStorageImpl()

    override fun createUser(email: String, password: String): Observable<User> {
        return database.createUser(email, password).map { firebaseUser -> FirebaseUserDataMapper.transform(firebaseUser) }
    }

    override fun signInUser(email: String, password: String): Observable<User> {
        return database.signInUser(email, password).map { firebaseUser -> FirebaseUserDataMapper.transform(firebaseUser) }
    }

    override fun checkUserLoggedIn(): Observable<User> {
        return database.checkUserLoggedIn().map { firebaseUser -> FirebaseUserDataMapper.transform(firebaseUser) }
    }

    override fun getUserState(): Observable<User> {
        return database.observeAuthStateChange().map { firebaseUser -> FirebaseUserDataMapper.transform(firebaseUser) }
    }

    override fun logOut(): Observable<Void> {
        return database.logOut()
    }

    override fun deleteUser(): Observable<Void> {
        return database.deleteUser()
    }

    override fun uploadPicture(id: String,stream : InputStream): Observable<String> {
        return storage.uploadFile(FirebaseStorage.getInstance().reference.child("pictures").child(id),stream)
    }



}
