package com.everis.authspike.presenter

import android.util.Log

import com.everis.authspike.UIThread
import com.everis.authspike.view.views.LoginView
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.SignIn
import com.everis.domain.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import rx.Subscriber

class LoginPresenterImpl(private val view: LoginView) : LoginPresenter {

    private val signInUseCase: SignIn = SignIn(UIThread(), UserDataRepository())

    override fun onPause() {
        //default implementation
    }

    override fun onResume() {
        //default implementation
    }

    override fun onStop() {
        //default implementation
    }

    override fun onDestroy() {
        signInUseCase.unsuscribe()
    }


    override fun signInUser(email: String, password: String) {
        this.view.showLoading()
        signInUseCase.bindParams(email, password)
        signInUseCase.execute(SignInUserSubscriber())
    }

    override fun signInGoogle(account: GoogleSignInAccount) {

        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)

        FirebaseAuth.getInstance().signInWithCredential(credentials).addOnCompleteListener {
            view.hideLoading()
            if(it.isSuccessful){
                view.safeActiveSession(true)
                view.showLoggedInScreen()
            }
        }

    }

    private inner class SignInUserSubscriber : Subscriber<User>() {

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, e.localizedMessage)
            view.hideLoading()
        }

        override fun onNext(user: User) {
            view.showLoggedInScreen()
        }
    }

    companion object {

        private val TAG = LoginPresenterImpl::class.java.simpleName
    }
}
