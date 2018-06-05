package com.everis.authspike.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everis.authspike.R
import com.everis.authspike.navigator.Navigator
import com.everis.authspike.presenter.LoginPresenter
import com.everis.authspike.presenter.LoginPresenterImpl
import com.everis.authspike.utils.Event
import com.everis.authspike.utils.PreferenceManager

import kotlinx.android.synthetic.main.fragment_login.*


import com.everis.authspike.view.activity.WelcomeActivity
import com.everis.authspike.view.views.LoginView
import kotlinx.android.synthetic.main.fragment_register.*
import rx.functions.Action1

class LoginFragment : BaseFragment() , LoginView{

    lateinit var activity: WelcomeActivity

    lateinit var preferenceManager: PreferenceManager
    lateinit var presenter: LoginPresenter
    lateinit var navigator: Navigator

    companion object {

        fun newInstance(activity: WelcomeActivity): LoginFragment {
            val fragment = LoginFragment()
            fragment.activity = activity
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferenceManager = PreferenceManager(activity)
        presenter = LoginPresenterImpl(this)
        navigator = activity.navigator
    }

    override fun onStart() {
        super.onStart()
        subscribeBus()
    }

    override fun onStop() {
        super.onStop()
        unsubscribeBus()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter.onDestroy()
    }

    override fun getBusAction(): Action1<Any>? {
        return Action1{
            if(it is Event.GoogleSignInEvent){
                val event: Event.GoogleSignInEvent = it
                val result = event.signInResult

                if(result!!.isSuccess){
                    val account = result.signInAccount
                    presenter.signInGoogle(account!!)
                }
                else{
                    activity.hideLoading()
                }
            }
        }
    }


    private fun initUI() {
        login_button.setOnClickListener {
            emailPasswordLogin()
        }

        google_login_button.setOnClickListener{
            showLoading()
            activity.googleSignIn()
        }

        tv_not_registered.setOnClickListener {
            navigator!!.navigateToRegisterFragment()
        }
    }

    private fun emailPasswordLogin() {
        val email = tiet_login_email!!.text.toString()
        val password = tiet_login_password!!.text.toString()
        preferenceManager!!.setActiveSession(keep_session_checkbox!!.isChecked)
        presenter!!.signInUser(email, password)
    }

    override fun showLoading() {
        activity.showLoading()
    }

    override fun hideLoading() {
        activity.hideLoading()
    }


    override fun showLoggedInScreen() {
        navigator!!.navigateToHomeActivity()
    }

}
