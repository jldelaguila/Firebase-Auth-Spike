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
import com.everis.authspike.utils.PreferenceManager

import kotlinx.android.synthetic.main.fragment_login.*


import com.everis.authspike.view.activity.WelcomeActivity
import com.everis.authspike.view.views.LoginView

class LoginFragment : Fragment() , LoginView{

    lateinit var activity: WelcomeActivity

    private var preferenceManager: PreferenceManager? = null
    private var presenter: LoginPresenter? = null
    private var navigator: Navigator? = null

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
        navigator = Navigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter!!.onDestroy()
    }

    override fun showLoading() {
        activity.showLoading()
    }

    override fun hideLoading() {
        activity.hideLoading()
    }

    private fun initUI() {
        login_button.setOnClickListener {
            emailPasswordLogin()
        }

        tv_not_registered.setOnClickListener {
            navigator!!.navigateToRegisterFragment(activity)
        }
    }

    internal fun emailPasswordLogin() {
        val email = tiet_email!!.text.toString()
        val password = tiet_password!!.text.toString()
        preferenceManager!!.setActiveSession(keep_session_checkbox!!.isChecked)
        presenter!!.signInUser(email, password)
    }

    override fun showLoggedInScreen() {
        navigator!!.navigateToHomeActivity(activity)
    }

}
