package com.everis.authspike.navigator

import android.content.Intent
import com.everis.authspike.R
import com.everis.authspike.view.activity.BaseActivity
import com.everis.authspike.view.activity.HomeActivity
import com.everis.authspike.view.activity.ProfileActivity
import com.everis.authspike.view.activity.WelcomeActivity
import com.everis.authspike.view.fragment.ContactFragment
import com.everis.authspike.view.fragment.LoginFragment
import com.everis.authspike.view.fragment.RegisterFragment

class Navigator(private val activity: BaseActivity) {

    fun navigateToHomeActivity() {
        val homeIntent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(homeIntent)
        activity.finish()
    }

    fun navigateToRegisterActivity() {
        val registerIntent = Intent(activity, WelcomeActivity::class.java)
        activity.startActivity(registerIntent)
        activity.finish()
    }

    fun navigateToContactsFragment() {
        activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, ContactFragment.newInstance()).commit()
    }

    fun navigateToRegisterFragment() {
        activity.supportFragmentManager.beginTransaction().replace(R.id.welcome_container, RegisterFragment.newInstance()).addToBackStack(RegisterFragment::class.java.simpleName).commit()
    }

    fun navigateToLoginFragment(activity: WelcomeActivity) {
        activity.supportFragmentManager.beginTransaction().replace(R.id.welcome_container, LoginFragment.newInstance(activity)).commit()
    }

    fun navigateToProfileActivity(phone: String, url: String) {
        activity.startActivity(ProfileActivity.getCallingIntent(activity, phone, url))
    }

    fun navigateToGalley() {
        val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

}
