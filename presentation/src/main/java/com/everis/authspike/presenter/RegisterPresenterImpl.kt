package com.everis.authspike.presenter

import android.text.TextUtils
import com.everis.authspike.UIThread
import com.everis.authspike.view.view.LoginView
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.CreateUser
import com.everis.domain.model.User
import rx.Subscriber

class RegisterPresenterImpl(val view : LoginView) : RegisterPresenter{
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
        }

        override fun onNext(user: User) {
            if (user.uid != null && !TextUtils.isEmpty(user.uid)) {
                view.showLoggedInScreen()
            }
        }
    }

}