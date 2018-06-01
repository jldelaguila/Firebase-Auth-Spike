package com.everis.authspike.navigator

import android.app.Activity
import android.content.Intent

import com.everis.authspike.view.activity.ContactsActivity
import com.everis.authspike.view.activity.HomeActivity
import com.everis.authspike.view.activity.RegisterActivity

/**
 * Created by everis on 25/04/18.
 */

class Navigator {

    fun navigateToHomeActivity(activity: Activity) {
        val homeIntent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(homeIntent)
        activity.finish()
    }

    fun navigateToRegisterActivity(activity: Activity) {
        val registerIntent = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(registerIntent)
        activity.finish()
    }

    fun navigateToContactsActivity(activity: Activity) {
        val contactsIntent = Intent(activity, ContactsActivity::class.java)
        activity.startActivity(contactsIntent)
    }
}
