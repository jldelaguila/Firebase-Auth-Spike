package com.everis.data.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import rx.Observable

class FireBaseAuthImpl : FireBaseAuth {

    override fun createUser(email: String, password: String): Observable<FirebaseUser> {
        return Observable.create { subscriber ->
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    subscriber.onNext(task.result.user)
                } else {
                    subscriber.onError(Exception())
                }
                subscriber.onCompleted()
            }
        }
    }

    override fun signInUser(email: String, password: String): Observable<FirebaseUser> {
        return Observable.create { subscriber ->
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    subscriber.onNext(task.result.user)
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(task.exception)
                }
            }
        }
    }

    override fun observeAuthStateChange(): Observable<FirebaseUser> {
        return Observable.create { subscriber -> FirebaseAuth.getInstance().addIdTokenListener { firebaseAuth -> subscriber.onNext(firebaseAuth.currentUser) } }
    }

    override fun logOut(): Observable<Void> {
        return Observable.create { subscriber ->
            FirebaseAuth.getInstance().signOut()
            subscriber.onCompleted()
        }
    }

    override fun deleteUser(): Observable<Void> {
        return Observable.create { subscriber ->
            if (FirebaseAuth.getInstance().currentUser != null) {
                FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        subscriber.onCompleted()
                    } else {
                        subscriber.onError(task.exception)
                    }
                }
            }
        }
    }

    override fun checkUserLoggedIn(): Observable<FirebaseUser> {
        return Observable.create { subscriber ->
            subscriber.onNext(FirebaseAuth.getInstance().currentUser)
            subscriber.onCompleted()
        }
    }
}
