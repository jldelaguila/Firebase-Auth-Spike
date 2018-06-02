package com.everis.authspike

import android.app.Application

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by everis on 3/05/18.
 */

class AuthSpikeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
