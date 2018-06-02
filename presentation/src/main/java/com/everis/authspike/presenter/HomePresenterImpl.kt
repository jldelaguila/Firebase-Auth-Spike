package com.everis.authspike.presenter

import android.util.Log

import com.everis.authspike.UIThread
import com.everis.authspike.model.UserModelDataMapper
import com.everis.authspike.view.views.HomeView
import com.everis.data.repository.DatabaseDataRepository
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.DeleteUser
import com.everis.domain.interactor.GetEnabledUser
import com.everis.domain.interactor.GetIntroMessage
import com.everis.domain.interactor.GetUserByPhoneQuery
import com.everis.domain.interactor.GetUserByPhoneReference
import com.everis.domain.interactor.GetUserState
import com.everis.domain.interactor.LogOut
import com.everis.domain.model.IntroMessage
import com.everis.domain.model.P2PUser
import com.everis.domain.model.User

import rx.Subscriber

/**
 * Created by everis on 25/04/18.
 */

class HomePresenterImpl(private val view: HomeView) : HomePresenter {
    private val userStateUseCase: GetUserState = GetUserState(UIThread(), UserDataRepository())
    private val logOutUseCase: LogOut = LogOut(UIThread(), UserDataRepository())
    private val deleteUserUseCase: DeleteUser = DeleteUser(UIThread(), UserDataRepository())
    private val getIntroMessageUseCase: GetIntroMessage = GetIntroMessage(UIThread(), DatabaseDataRepository())
    private val getEnabledUserUseCase: GetEnabledUser = GetEnabledUser(UIThread(), DatabaseDataRepository())
    private val userByRefUseCase: GetUserByPhoneReference = GetUserByPhoneReference(UIThread(), DatabaseDataRepository())
    private val userByQueryUseCase: GetUserByPhoneQuery = GetUserByPhoneQuery(UIThread(), DatabaseDataRepository())

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
        getIntroMessageUseCase.unsuscribe()
        getEnabledUserUseCase.unsuscribe()
        userByRefUseCase.unsuscribe()
        userByQueryUseCase.unsuscribe()
    }

    override fun loadUserState() {
        userStateUseCase.execute(UserStateSubscriber())
    }

    override fun loadUserEnabled(userUid: String) {
        getEnabledUserUseCase.bindParams(userUid)
        getEnabledUserUseCase.execute(GetUserEnabledSubscriber())
    }

    override fun loadIntroMessage() {
        getIntroMessageUseCase.execute(GetIntroMessageSubscriber())
    }

    override fun logOut() {
        logOutUseCase.execute(LogOutSubscriber())
    }

    override fun deleteUser() {
        view.showLoading()
        deleteUserUseCase.execute(DeleteUserSubscriber())
    }

    override fun loadUserByPhoneReference(phoneNumber: String) {
        userByRefUseCase.bindParams(phoneNumber)
        userByRefUseCase.execute(GetUserByPhoneRefSubscriber())
    }

    override fun loadUserByPhoneQuery(phoneNumber: String) {
        userByQueryUseCase.bindParams(phoneNumber)
        userByQueryUseCase.execute(GetUserByPhoneQuerySubscriber())
    }

    private inner class UserStateSubscriber : Subscriber<User>() {

        override fun onCompleted() {
            //default implementation
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, e.localizedMessage)
        }

        override fun onNext(user: User) {
            val userModel = UserModelDataMapper.transform(user)
            Log.d(TAG, "Se recibio un cambio en el usuario con uid: " + userModel.uid)
            view.hideLoading()
            view.changeUserStateText(if (userModel.isState) "Usuario habilitado" else "Usuario deshabilitado")

        }
    }

    private inner class LogOutSubscriber : Subscriber<Void>() {

        override fun onCompleted() {
            view.logOutView()
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
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
            Log.d(TAG, "onError: " + e.localizedMessage)
        }

        override fun onNext(aVoid: Void) {
            //default implementation
        }
    }

    private inner class GetIntroMessageSubscriber : Subscriber<IntroMessage>() {

        override fun onCompleted() {
            //there is never a complete
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "IntroMessageError: " + e.localizedMessage)
        }

        override fun onNext(introMessage: IntroMessage) {
            view.setIntroMessageText(introMessage)
        }
    }

    private inner class GetUserEnabledSubscriber : Subscriber<Boolean>() {

        override fun onCompleted() {
            //default implementation
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, e.localizedMessage)
        }

        override fun onNext(enabled: Boolean?) {
            if (!enabled!!)
                logOut()
        }
    }

    private inner class GetUserByPhoneRefSubscriber : Subscriber<P2PUser>() {

        override fun onStart() {
            super.onStart()
            Log.d(TAG, "*******************Inicio de busqueda por referencia*******************")
        }

        override fun onCompleted() {}

        override fun onError(e: Throwable) {
            Log.d(TAG, "*******************Ocurrio un error en la busqueda por referencia*******************")
            Log.d(TAG, e.localizedMessage)
        }

        override fun onNext(user: P2PUser) {
            Log.d(TAG, "Usuario encontrado por referencia: " + user.toString())

        }
    }

    private inner class GetUserByPhoneQuerySubscriber : Subscriber<P2PUser>() {

        override fun onStart() {
            super.onStart()
            Log.d(TAG, "*******************Inicio de busqueda por Query*******************")
        }

        override fun onCompleted() {}

        override fun onError(e: Throwable) {
            Log.d(TAG, "*******************Ocurrio un error en la busqueda por Query*******************")
            Log.d(TAG, e.localizedMessage)
        }

        override fun onNext(user: P2PUser) {
            Log.d(TAG, "Usuario encontrado por query: " + user.toString())

        }
    }

    companion object {

        private val TAG = HomePresenterImpl::class.java.simpleName
    }

}
