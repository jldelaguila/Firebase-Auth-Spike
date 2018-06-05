package com.everis.authspike.view.activity

import android.os.Bundle
import android.view.View

import com.everis.authspike.R
import com.everis.authspike.view.view.BaseView

import kotlinx.android.synthetic.main.activity_register.*

class WelcomeActivity : BaseActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        navigator.navigateToLoginFragment(this)
    }

    override fun showLoading() {
        this.progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.GONE
    }
}
