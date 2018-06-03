package com.everis.authspike.view.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.everis.authspike.R;
import com.everis.authspike.presenter.SplashPresenter;
import com.everis.authspike.presenter.SplashPresenterImpl;
import com.everis.authspike.view.views.SplashView;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity  implements SplashView{

    @BindView(R.id.splash_progress)
    ProgressBar splashProgress;

    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter = new SplashPresenterImpl(this);

        displaySpikeNotification("Me creaste!!", "Muchas gracias amo :)");


        /**
         * First approach: SplashActiviy starts a monitoring service which
         * will get notified when application has been terminated*/
        //startService(new Intent(this, LifecycleService.class));

        /**
         * Second approach: SplashActiviy checks if the user wanted to
         * maintaing his session active. If not, we perform the cleanup in this section*/
        if(!getPreferenceManager().getActiveSessionPreference()){
            FirebaseAuth.getInstance().signOut();
        }

        presenter.checkUserLoggeIn();

    }


    @Override
    public void showLoading() {
        splashProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        splashProgress.setVisibility(View.GONE);
    }

    @Override
    public void navigateToRegisterScreen() {
        getNavigator().navigateToRegisterActivity(this);
    }

    @Override
    public void navigateToHomeScreen() {
        getNavigator().navigateToHomeActivity(this);
    }
}
