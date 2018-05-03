package com.everis.authspike.presenter;

import android.util.Log;

import com.everis.authspike.UIThread;
import com.everis.authspike.model.UserModel;
import com.everis.authspike.model.UserModelDataMapper;
import com.everis.authspike.view.views.HomeView;
import com.everis.data.repository.DatabaseDataRepository;
import com.everis.data.repository.UserDataRepository;
import com.everis.domain.interactor.DeleteUser;
import com.everis.domain.interactor.GetEnabledUser;
import com.everis.domain.interactor.GetIntroMessage;
import com.everis.domain.interactor.GetUserByPhoneQuery;
import com.everis.domain.interactor.GetUserByPhoneReference;
import com.everis.domain.interactor.GetUserState;
import com.everis.domain.interactor.LogOut;
import com.everis.domain.model.IntroMessage;
import com.everis.domain.model.P2PUser;
import com.everis.domain.model.User;

import rx.Subscriber;

/**
 * Created by everis on 25/04/18.
 */

public class HomePresenterImpl implements HomePresenter {

    private static final String TAG = HomePresenterImpl.class.getSimpleName();

    private HomeView view;
    private GetUserState userStateUseCase;
    private LogOut logOutUseCase;
    private DeleteUser deleteUserUseCase;
    private GetIntroMessage getIntroMessageUseCase;
    private GetEnabledUser getEnabledUserUseCase;
    private GetUserByPhoneReference userByRefUseCase;
    private GetUserByPhoneQuery userByQueryUseCase;

    public HomePresenterImpl(HomeView view) {
        this.view = view;
        userStateUseCase = new GetUserState(new UIThread(), new UserDataRepository());
        logOutUseCase = new LogOut(new UIThread(), new UserDataRepository());
        deleteUserUseCase = new DeleteUser(new UIThread(), new UserDataRepository());
        getIntroMessageUseCase = new GetIntroMessage(new UIThread(), new DatabaseDataRepository());
        getEnabledUserUseCase = new GetEnabledUser(new UIThread(), new DatabaseDataRepository());
        userByRefUseCase = new GetUserByPhoneReference(new UIThread(), new DatabaseDataRepository());
        userByQueryUseCase = new GetUserByPhoneQuery(new UIThread(), new DatabaseDataRepository());
    }

    @Override
    public void onPause() {
        //default implementation
    }

    @Override
    public void onResume() {
        //default implementation
    }

    @Override
    public void onStop() {
        //default implementation
    }

    @Override
    public void onDestroy() {
        userStateUseCase.unsuscribe();
        logOutUseCase.unsuscribe();
        deleteUserUseCase.unsuscribe();
        getIntroMessageUseCase.unsuscribe();
        getEnabledUserUseCase.unsuscribe();
        userByRefUseCase.unsuscribe();
        userByQueryUseCase.unsuscribe();
    }

    @Override
    public void loadUserState() {
        userStateUseCase.execute(new UserStateSubscriber());
    }

    @Override
    public void loadUserEnabled(String userUid) {
        getEnabledUserUseCase.bindParams(userUid);
        getEnabledUserUseCase.execute(new GetUserEnabledSubscriber());
    }

    @Override
    public void loadIntroMessage() {
        getIntroMessageUseCase.execute(new GetIntroMessageSubscriber());
    }

    @Override
    public void logOut() {
        logOutUseCase.execute(new LogOutSubscriber());
    }

    @Override
    public void deleteUser() {
        view.showLoading();
        deleteUserUseCase.execute(new DeleteUserSubscriber());
    }

    @Override
    public void loadUserByPhoneReference(String phoneNumber) {
        userByRefUseCase.bindParams(phoneNumber);
        userByRefUseCase.execute(new GetUserByPhoneRefSubscriber());
    }

    @Override
    public void loadUserByPhoneQuery(String phoneNumber) {
        userByQueryUseCase.bindParams(phoneNumber);
        userByQueryUseCase.execute(new GetUserByPhoneQuerySubscriber());
    }

    private class UserStateSubscriber extends Subscriber<User> {

        @Override
        public void onCompleted() {
            //default implementation
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, e.getLocalizedMessage());
        }

        @Override
        public void onNext(User user) {
            UserModel userModel = UserModelDataMapper.transform(user);
            Log.d(TAG, "Se recibio un cambio en el usuario con uid: " + userModel.getUid());
            view.hideLoading();
            view.changeUserStateText(userModel.isState()?"Usuario habilitado":"Usuario deshabilitado");

        }
    }

    private class LogOutSubscriber extends Subscriber<Void>{

        @Override
        public void onCompleted() {
            view.logOutView();
        }

        @Override
        public void onError(Throwable e) {
            //default implementation
        }

        @Override
        public void onNext(Void aVoid) {
            //default implementation
        }
    }

    private class DeleteUserSubscriber extends Subscriber<Void>{

        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            Log.d(TAG, "onError: " + e.getLocalizedMessage());
        }

        @Override
        public void onNext(Void aVoid) {
            //default implementation
        }
    }

    private class GetIntroMessageSubscriber extends Subscriber<IntroMessage>{

        @Override
        public void onCompleted() {
            //there is never a complete
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "IntroMessageError: " + e.getLocalizedMessage());
        }

        @Override
        public void onNext(IntroMessage introMessage) {
            view.setIntroMessageText(introMessage);
        }
    }

    private class GetUserEnabledSubscriber extends Subscriber<Boolean>{

        @Override
        public void onCompleted() {
            //default implementation
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, e.getLocalizedMessage());
        }

        @Override
        public void onNext(Boolean enabled) {
            if(!enabled)
                logOut();
        }
    }

    private class GetUserByPhoneRefSubscriber extends Subscriber<P2PUser>{

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "*******************Inicio de busqueda por referencia*******************");
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "*******************Ocurrio un error en la busqueda por referencia*******************");
            Log.d(TAG, e.getLocalizedMessage());
        }

        @Override
        public void onNext(P2PUser user) {
            Log.d(TAG, "Usuario encontrado por referencia: " + user.toString());

        }
    }

    private class GetUserByPhoneQuerySubscriber extends Subscriber<P2PUser>{

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "*******************Inicio de busqueda por Query*******************");
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "*******************Ocurrio un error en la busqueda por Query*******************");
            Log.d(TAG, e.getLocalizedMessage());
        }

        @Override
        public void onNext(P2PUser user) {
            Log.d(TAG, "Usuario encontrado por query: " + user.toString());

        }
    }

}
