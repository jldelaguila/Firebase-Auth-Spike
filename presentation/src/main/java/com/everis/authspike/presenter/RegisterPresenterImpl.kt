package com.everis.authspike.presenter

import android.text.TextUtils
import android.util.Log

import com.everis.authspike.UIThread
import com.everis.authspike.view.views.RegisterView
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.CreateUser
import com.everis.domain.interactor.SignIn
import com.everis.domain.model.User

import rx.Subscriber

/**
 * Created by everis on 25/04/18.
 */

class RegisterPresenterImpl(private val view: RegisterView) : RegisterPresenter {
    private val createUserUseCase: CreateUser = CreateUser(UIThread(), UserDataRepository())
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

        createUserUseCase.unsuscribe()
        signInUseCase.unsuscribe()
    }


    override fun signInUser(email: String, password: String) {
        this.view.showLoading()
        signInUseCase.bindParams(email, password)
        signInUseCase.execute(SignInUserSubscriber())
    }

    override fun createUser(email: String, password: String) {
        this.view.showLoading()
        createUserUseCase.bindParams(email, password)
        createUserUseCase.execute(CreateUserSubscriber())
    }


    private inner class CreateUserSubscriber : Subscriber<User>() {

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
            Log.d(TAG, "Error al crear el usuario")
        }

        override fun onNext(user: User) {
            if (user.uid != null && !TextUtils.isEmpty(user.uid)) {
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

        private val TAG = RegisterPresenterImpl::class.java.simpleName
    }
}
