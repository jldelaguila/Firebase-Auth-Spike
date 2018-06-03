package com.everis.authspike.navigator

import android.app.Activity
import android.content.Intent
import com.everis.authspike.R

import com.everis.authspike.view.activity.ContactsActivity
import com.everis.authspike.view.activity.HomeActivity
import com.everis.authspike.view.activity.WelcomeActivity
import com.everis.authspike.view.fragment.LoginFragment
import com.everis.authspike.view.fragment.RegisterFragment


class Navigator {

    fun navigateToHomeActivity(activity: Activity) {
        val homeIntent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(homeIntent)
        activity.finish()
    }

    fun navigateToRegisterActivity(activity: Activity) {
        val registerIntent = Intent(activity, WelcomeActivity::class.java)
        activity.startActivity(registerIntent)
        activity.finish()
    }

    fun navigateToContactsActivity(activity: Activity) {
        val contactsIntent = Intent(activity, ContactsActivity::class.java)
        activity.startActivity(contactsIntent)
    }

    fun navigateToRegisterFragment(activity: WelcomeActivity) {
        activity.supportFragmentManager.beginTransaction().replace(R.id.welcome_container, RegisterFragment.newInstance(activity)).addToBackStack(RegisterFragment::class.java.simpleName).commit()
    }

    fun navigateToLoginFragment(activity: WelcomeActivity) {
        activity.supportFragmentManager.beginTransaction().replace(R.id.welcome_container, LoginFragment.newInstance(activity)).commit()
    }
}
