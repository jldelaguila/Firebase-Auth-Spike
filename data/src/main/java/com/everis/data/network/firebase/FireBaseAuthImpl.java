package com.everis.data.network.firebase;

import android.support.annotation.NonNull;

import com.everis.data.model.FirebaseUserDataMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by everis on 25/04/18.
 */

public class FireBaseAuthImpl implements FireBaseAuth {

    @Override
    public Observable<FirebaseUser> createUser(final String email, final String password) {
        return Observable.create(new Observable.OnSubscribe<FirebaseUser>() {
            @Override
            public void call(final Subscriber<? super FirebaseUser> subscriber) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            subscriber.onNext(task.getResult().getUser());
                        }
                        else{
                            subscriber.onError(new Exception());
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    @Override
    public Observable<FirebaseUser> signInUser(final String emaail, final String password) {
        return Observable.create(new Observable.OnSubscribe<FirebaseUser>() {
            @Override
            public void call(final Subscriber<? super FirebaseUser> subscriber) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emaail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            subscriber.onNext(task.getResult().getUser());
                            subscriber.onCompleted();
                        }
                        else{
                             subscriber.onError(task.getException());
                        }
                    }
                });
            }
        });
    }

    @Override
    public Observable<FirebaseUser> observeAuthStateChange() {
        return Observable.create(new Observable.OnSubscribe<FirebaseUser>() {
            @Override
            public void call(final Subscriber<? super FirebaseUser> subscriber) {
                FirebaseAuth.getInstance().addIdTokenListener(new FirebaseAuth.IdTokenListener() {
                    @Override
                    public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                        subscriber.onNext(firebaseAuth.getCurrentUser());

                    }
                });
            }
        });
    }

    @Override
    public Observable<Void> logOut() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                FirebaseAuth.getInstance().signOut();
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> deleteUser() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                subscriber.onCompleted();
                            }
                            else{
                                subscriber.onError(task.getException());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public Observable<FirebaseUser> checkUserLoggedIn() {
        return Observable.create(new Observable.OnSubscribe<FirebaseUser>() {
            @Override
            public void call(Subscriber<? super FirebaseUser> subscriber) {
                subscriber.onNext(FirebaseAuth.getInstance().getCurrentUser());
                subscriber.onCompleted();
            }
        });
    }
}
