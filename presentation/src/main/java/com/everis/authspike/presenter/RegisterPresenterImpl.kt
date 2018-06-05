package com.everis.authspike.presenter

import android.text.TextUtils
import com.everis.authspike.UIThread
import com.everis.authspike.view.view.RegisterView
import com.everis.authspike.view.views.LoginView
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.CreateUser
import com.everis.domain.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import rx.Subscriber

class RegisterPresenterImpl(val view :LoginView) : RegisterPresenter{

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
        createUserUseCase.unsuscribe()
    }

    private val createUserUseCase: CreateUser = CreateUser(UIThread(), UserDataRepository())

    override fun createUser(email: String, password: String) {
        createUserUseCase.bindParams(email, password)
        createUserUseCase.execute(CreateUserSubscriber())
    }

    override fun signInGoogle(account: GoogleSignInAccount) {
        val accountToken = account.idToken
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)

        FirebaseAuth.getInstance().signInWithCredential(credentials).addOnCompleteListener {
            if(it.isSuccessful){
                view.hideLoading()
            }
            else{
                view.showLoggedInScreen()
            }
        }
    }

    private inner class CreateUserSubscriber : Subscriber<User>() {

        override fun onStart() {
            view.showLoading()
        }

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
        }

        override fun onNext(user: User) {
            if (user.uid != null && !TextUtils.isEmpty(user.uid)) {
                view.showLoggedInScreen()
            }
        }
    }

}