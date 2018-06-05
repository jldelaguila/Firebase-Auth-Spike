package com.everis.authspike.view.activity


import android.os.Bundle
import android.view.View

import com.everis.authspike.R
import com.everis.authspike.presenter.SplashPresenter
import com.everis.authspike.presenter.SplashPresenterImpl
import com.everis.authspike.view.view.SplashView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), SplashView {

    internal lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenterImpl(this)

        /**
         * First approach: SplashActiviy starts a monitoring service which
         * will get notified when application has been terminated */
        //startService(new Intent(this, LifecycleService.class));

        /**
         * Second approach: SplashActiviy checks if the user wanted to
         * maintaing his session active. If not, we perform the cleanup in this section */
        if (!preferenceManager.activeSessionPreference) {
            FirebaseAuth.getInstance().signOut()
        }

        presenter.checkUserLoggeIn()

    }


    override fun showLoading() {
        splash_progress!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        splash_progress!!.visibility = View.GONE
    }

    override fun navigateToRegisterScreen() {
        navigator.navigateToRegisterActivity()
    }

    override fun navigateToHomeScreen() {
        navigator.navigateToHomeActivity()
    }
}
