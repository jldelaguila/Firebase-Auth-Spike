package com.everis.authspike.view.activity

import android.os.Bundle

import com.everis.authspike.R

class HomeActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigator.navigateToContactsFragment()
    }
}
