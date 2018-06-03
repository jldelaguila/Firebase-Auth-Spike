package com.everis.authspike.navigator

import android.content.Intent
import com.everis.authspike.R
import com.everis.authspike.view.activity.BaseActivity

import com.everis.authspike.view.activity.HomeActivity
import com.everis.authspike.view.activity.RegisterActivity
import com.everis.authspike.view.fragment.ContactFragment

/**
 * Created by everis on 25/04/18.
 */

class Navigator (private val activity: BaseActivity) {

    fun navigateToHomeActivity() {
        val homeIntent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(homeIntent)
        activity.finish()
    }

    fun navigateToRegisterActivity() {
        val registerIntent = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(registerIntent)
        activity.finish()
    }

    fun navigateToContactsFragment() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, ContactFragment.newInstance()).commit()
    }


}
