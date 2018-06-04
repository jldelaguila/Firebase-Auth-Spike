package com.everis.authspike

import android.app.Application

import com.google.firebase.database.FirebaseDatabase
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.Crashlytics


/**
 * Created by everis on 3/05/18.
 */

class AuthSpikeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        initCrashlytics()
    }


    private fun initCrashlytics() {
        val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(true)
                .build()
        Fabric.with(fabric)
    }
}
