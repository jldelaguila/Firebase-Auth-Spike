package com.everis.authspike.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View

import com.everis.authspike.R
import com.everis.authspike.presenter.RegisterPresenter
import com.everis.authspike.presenter.RegisterPresenterImpl
import com.everis.authspike.utils.PreferenceManager
import com.everis.authspike.view.views.RegisterView

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterView, View.OnClickListener {

    private var presenter: RegisterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenterImpl(this)
        preferenceManager = PreferenceManager(this)
        login_button.setOnClickListener(this)
        register_button.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter!!.onDestroy()
    }

    override fun showLoading() {
        this.progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.GONE
    }

    override fun showLoggedInScreen() {
        navigator.navigateToHomeActivity(this)
    }

    private fun emailPasswordRegisterClicked() {
        val email = tiet_email!!.text.toString()
        val password = tiet_password!!.text.toString()
        preferenceManager.setActiveSession(keep_session_checkbox!!.isChecked)
        Log.d(TAG, "Sesion activa: " + keep_session_checkbox!!.isChecked)
        presenter!!.createUser(email, password)

    }

    private fun emailPasswordLogin() {
        val email = tiet_email!!.text.toString()
        val password = tiet_password!!.text.toString()
        preferenceManager.setActiveSession(keep_session_checkbox!!.isChecked)
        Log.d(TAG, "Sesion activa: " + keep_session_checkbox!!.isChecked)
        presenter!!.signInUser(email, password)
    }

    override fun onClick(v: View?) {
        when (v) {
            login_button -> emailPasswordLogin()
            register_button -> emailPasswordRegisterClicked()
        }
    }

    companion object {

        private val TAG = RegisterActivity::class.java.simpleName
    }
}
