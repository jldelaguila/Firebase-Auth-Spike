package com.everis.authspike.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.everis.authspike.UIThread;
import com.everis.authspike.view.views.RegisterView;
import com.everis.data.repository.UserDataRepository;
import com.everis.domain.interactor.CreateUser;
import com.everis.domain.interactor.SignIn;
import com.everis.domain.model.User;

import rx.Subscriber;

/**
 * Created by everis on 25/04/18.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private static final String TAG = RegisterPresenterImpl.class.getSimpleName();
    private RegisterView view;
    private CreateUser createUserUseCase;
    private SignIn signInUseCase;

    public RegisterPresenterImpl(RegisterView view) {
        this.view = view;
        this.createUserUseCase = new CreateUser(new UIThread(), new UserDataRepository());
        this.signInUseCase = new SignIn(new UIThread(), new UserDataRepository());
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

        createUserUseCase.unsuscribe();
        signInUseCase.unsuscribe();
    }


    @Override
    public void signInUser(String email, String password) {
        this.view.showLoading();
        signInUseCase.bindParams(email, password);
        signInUseCase.execute(new SignInUserSubscriber());
    }

    @Override
    public void createUser(String email, String password) {
        this.view.showLoading();
        createUserUseCase.bindParams(email, password);
        createUserUseCase.execute(new CreateUserSubscriber());
    }



    private class CreateUserSubscriber extends Subscriber<User>{

        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            Log.d(TAG, "Error al crear el usuario");
        }

        @Override
        public void onNext(User user) {
            if(user.getUid()!=null && !TextUtils.isEmpty(user.getUid())){
                view.showLoggedInScreen();
            }
        }
    }

    private class SignInUserSubscriber extends Subscriber<User>{

        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, e.getLocalizedMessage());
            view.hideLoading();
        }

        @Override
        public void onNext(User user) {
            view.showLoggedInScreen();
        }
    }
}
