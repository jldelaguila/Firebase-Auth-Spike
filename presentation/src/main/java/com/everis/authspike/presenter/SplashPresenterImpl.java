package com.everis.authspike.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.everis.authspike.UIThread;
import com.everis.authspike.view.views.SplashView;
import com.everis.data.repository.UserDataRepository;
import com.everis.domain.interactor.CheckLoggedIn;
import com.everis.domain.model.User;

import rx.Subscriber;

/**
 * Created by everis on 26/04/18.
 */

public class SplashPresenterImpl implements SplashPresenter {

    private static final String TAG = SplashPresenterImpl.class.getSimpleName();
    private CheckLoggedIn useCase;
    private SplashView view;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
        this.useCase = new CheckLoggedIn(new UIThread(), new UserDataRepository());
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
        useCase.unsuscribe();
    }

    @Override
    public void checkUserLoggeIn() {
        view.showLoading();
        useCase.execute(new CheckLoggedInSubscriber());
    }

    private class CheckLoggedInSubscriber extends Subscriber<User>{

        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error al revisar si hay usuario");
            view.hideLoading();
        }

        @Override
        public void onNext(User user) {
            if(user.getUid()!=null && !TextUtils.isEmpty(user.getUid())){
                view.navigateToHomeScreen();
            }
            else{
                view.navigateToRegisterScreen();
            }
        }
    }

}
