package com.everis.data.network.firebase;

import com.google.firebase.auth.FirebaseUser;

import rx.Observable;

/**
 * Created by everis on 25/04/18.
 */

public interface FireBaseAuth {

    Observable<FirebaseUser> createUser(String emaail, String password);
    Observable<FirebaseUser> signInUser(String emaail, String password);
    Observable<FirebaseUser> observeAuthStateChange();
    Observable<Void> logOut();
    Observable<Void> deleteUser();
    Observable<FirebaseUser> checkUserLoggedIn();

}
