package com.everis.authspike.presenter

import com.crashlytics.android.Crashlytics
import com.everis.authspike.UIThread
import com.everis.authspike.view.view.HomeView
import com.everis.data.repository.DatabaseDataRepository
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.DeleteUser
import com.everis.domain.interactor.GetEnabledUser
import com.everis.domain.interactor.GetUserState
import com.everis.domain.interactor.LogOut
import com.everis.domain.model.User

import rx.Subscriber

/**
 * Created by everis on 25/04/18.
 */

class HomePresenterImpl(private val view: HomeView) : HomePresenter {

    private val userStateUseCase: GetUserState = GetUserState(UIThread(), UserDataRepository())
    private val logOutUseCase: LogOut = LogOut(UIThread(), UserDataRepository())
    private val deleteUserUseCase: DeleteUser = DeleteUser(UIThread(), UserDataRepository())
    private val getEnabledUserUseCase: GetEnabledUser = GetEnabledUser(UIThread(), DatabaseDataRepository())

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
        userStateUseCase.unsuscribe()
        logOutUseCase.unsuscribe()
        deleteUserUseCase.unsuscribe()
        getEnabledUserUseCase.unsuscribe()
    }

    override fun loadUserState() {
        userStateUseCase.execute(UserStateSubscriber())
    }

    override fun loadUserEnabled(userUid: String) {
        getEnabledUserUseCase.bindParams(userUid)
        getEnabledUserUseCase.execute(GetUserEnabledSubscriber())
    }

    override fun logOut() {
        logOutUseCase.execute(LogOutSubscriber())
    }

    override fun deleteUser() {
        view.showLoading()
        deleteUserUseCase.execute(DeleteUserSubscriber())
    }

    override fun crashApp() {
       throw NullPointerException()
    }


    private inner class UserStateSubscriber : Subscriber<User>() {

        override fun onCompleted() {
            //default implementation
        }

        override fun onError(e: Throwable) {
            //default implementation
        }

        override fun onNext(user: User) {
            //default implementation
        }
    }

    private inner class LogOutSubscriber : Subscriber<Void>() {

        override fun onCompleted() {
            view.logOut()
        }

        override fun onError(e: Throwable) {
            //default implementation
        }

        override fun onNext(aVoid: Void) {
            //default implementation
        }
    }

    private inner class DeleteUserSubscriber : Subscriber<Void>() {

        override fun onCompleted() {
            //default implementation
        }

        override fun onError(e: Throwable) {
            //default implementation
        }

        override fun onNext(aVoid: Void) {
            //default implementation
        }
    }

    private inner class GetUserEnabledSubscriber : Subscriber<Boolean>() {

        override fun onCompleted() {
            //default implementation
        }

        override fun onError(e: Throwable) {
            //default implementation
        }

        override fun onNext(enabled: Boolean?) {
            if (!enabled!!)
                logOut()
        }
    }

}
