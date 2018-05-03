package com.everis.domain.repository;

import com.everis.domain.model.User;

import rx.Observable;

/**
 * Created by everis on 25/04/18.
 */

public interface UserRepository {

    Observable<User> createUser(String email, String password);
    Observable<User> signInUser(String email, String password);
    Observable<User> checkUserLoggedIn();
    Observable<User> getUserState();
    Observable<Void> logOut();
    Observable<Void> deleteUser();

}
