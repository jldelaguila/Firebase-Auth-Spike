package com.everis.data.repository;

import android.support.annotation.Nullable;

import com.everis.data.model.FirebaseUserDataMapper;
import com.everis.data.network.firebase.FireBaseAuthImpl;
import com.everis.domain.model.User;
import com.everis.domain.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by everis on 25/04/18.
 */

public class UserDataRepository implements UserRepository {

    private FireBaseAuthImpl database;

    public UserDataRepository() {
        this.database = new FireBaseAuthImpl();
    }


    @Override
    public Observable<User> createUser(String email, String password) {
        return database.createUser(email, password).map(new Func1<FirebaseUser, User>() {
            @Override
            public User call(FirebaseUser firebaseUser) {
                return FirebaseUserDataMapper.transform(firebaseUser);
            }
        });
    }

    @Override
    public Observable<User> signInUser(String email, String password) {
        return database.signInUser(email, password).map(new Func1<FirebaseUser, User>() {
            @Override
            public User call(FirebaseUser firebaseUser) {
                return FirebaseUserDataMapper.transform(firebaseUser);
            }
        });
    }

    @Override
    public Observable<User> checkUserLoggedIn() {
        return database.checkUserLoggedIn().map(new Func1<FirebaseUser, User>() {
            @Override
            public User call(FirebaseUser firebaseUser) {
                return FirebaseUserDataMapper.transform(firebaseUser);
            }
        });
    }


    @Override
    public Observable<User> getUserState() {
        return database.observeAuthStateChange().map(new Func1<FirebaseUser, User>() {
            @Override
            public User call(@Nullable FirebaseUser firebaseUser) {
                return FirebaseUserDataMapper.transform(firebaseUser);
            }
        });
    }

    @Override
    public Observable<Void> logOut() {
        return database.logOut();
    }

    @Override
    public Observable<Void> deleteUser() {
        return database.deleteUser();
    }


}
