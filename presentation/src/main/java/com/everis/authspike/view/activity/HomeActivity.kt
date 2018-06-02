package com.everis.authspike.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View

import com.everis.authspike.R
import com.everis.authspike.presenter.HomePresenter
import com.everis.authspike.presenter.HomePresenterImpl
import com.everis.authspike.utils.PreferenceManager
import com.everis.authspike.view.views.HomeView
import com.everis.domain.model.IntroMessage
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), HomeView, View.OnClickListener {

    private var presenter: HomePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userUid = FirebaseAuth.getInstance().currentUser!!.uid

        presenter = HomePresenterImpl(this)
        preferenceManager = PreferenceManager(this)
        presenter!!.loadUserState()
        presenter!!.loadIntroMessage()
        presenter!!.loadUserEnabled(userUid)
        //presenter.loadUserByPhoneQuery("903534032");
        presenter!!.loadUserByPhoneReference("99099")

        log_out_button.setOnClickListener(this)
        delete_user_button.setOnClickListener(this)
        open_contacts_button.setOnClickListener(this)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        if (!preferenceManager.activeSessionPreference) {
            presenter!!.logOut()
        }
        presenter!!.onDestroy()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v) {
            log_out_button -> presenter!!.logOut()
            delete_user_button -> presenter!!.deleteUser()
            else -> navigator.navigateToContactsActivity(this)
        }
    }

    override fun showLoading() {
        //no loading for now
    }

    override fun hideLoading() {
        //no loading for now
    }

    override fun changeUserStateText(userStateString: String) {
        user_state_tv.text = userStateString
    }

    override fun setIntroMessageText(introMessage: IntroMessage) {
        intro_message_tv.text = introMessage.displayMessage
    }

    override fun logOutView() {
        navigator.navigateToRegisterActivity(this)
    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}
