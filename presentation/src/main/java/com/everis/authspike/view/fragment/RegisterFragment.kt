package com.everis.authspike.view.fragment


import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.everis.authspike.R
import com.everis.authspike.presenter.RegisterPresenter
import com.everis.authspike.presenter.RegisterPresenterImpl
import com.everis.authspike.utils.Event
import com.everis.authspike.view.activity.WelcomeActivity
import com.everis.authspike.view.view.LoginView
import kotlinx.android.synthetic.main.fragment_register.*
import rx.functions.Action1

class RegisterFragment : BaseFragment() , LoginView{

    lateinit var activity : WelcomeActivity
    lateinit var presenter : RegisterPresenter

    companion object {

        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = getActivity() as WelcomeActivity
        presenter = RegisterPresenterImpl(this)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        subscribeBus()
    }

    override fun onStop() {
        super.onStop()
        unsubscribeBus()
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

    override fun showLoggedInScreen() {
        activity.navigator.navigateToHomeActivity()
    }

    override fun showLoading() {
        activity.showLoading()
    }

    override fun hideLoading() {
        activity.hideLoading()
    }

    override fun safeActiveSession(active: Boolean) {
        preferenceManager.setActiveSession(active)
    }

    private fun initUI() {
        register_button.setOnClickListener {
            emailPasswordRegisterClicked()
        }

        google_button.setOnClickListener{
            showLoading()
            activity.googleSignIn()
        }

        tiet_password.onChange {
            tiet_password.error = null
        }

        tiet_repassword.onChange {
            tiet_repassword.error = null
        }

        tiet_email.onChange {
            tiet_email.error = null
        }

    }

    private fun emailPasswordRegisterClicked() {
        val email = tiet_email!!.text.toString()
        val password = tiet_password!!.text.toString()
        val repassword = tiet_repassword.text.toString()

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tiet_email.error = "Invalid email"
        }
        else if(!password.equals(repassword) || password.length <= 6){
            tiet_repassword.error = "Invalid password"
            tiet_password.error = "Invalid password"
        }
        else{
            presenter.createUser(email, password)
        }

    }


    private fun TextInputEditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { cb(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


}
