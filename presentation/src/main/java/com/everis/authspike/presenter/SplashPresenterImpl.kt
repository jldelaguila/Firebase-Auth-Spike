package com.everis.authspike.presenter

import android.text.TextUtils
import android.util.Log

import com.everis.authspike.UIThread
import com.everis.authspike.view.views.SplashView
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.CheckLoggedIn
import com.everis.domain.model.User

import rx.Subscriber

/**
 * Created by everis on 26/04/18.
 */

class SplashPresenterImpl(private val view: SplashView) : SplashPresenter {

    private val useCase: CheckLoggedIn = CheckLoggedIn(UIThread(), UserDataRepository())

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
        useCase.unsuscribe()
    }

    override fun checkUserLoggeIn() {
        view.showLoading()
        useCase.execute(CheckLoggedInSubscriber())
    }

    private inner class CheckLoggedInSubscriber : Subscriber<User>() {

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Error al revisar si hay usuario")
            view.hideLoading()
        }

        override fun onNext(user: User) {
            if (user.uid != null && !TextUtils.isEmpty(user.uid)) {
                view.navigateToHomeScreen()
            } else {
                view.navigateToRegisterScreen()
            }
        }
    }

    companion object {
        private val TAG = SplashPresenterImpl::class.java.simpleName
    }

}
