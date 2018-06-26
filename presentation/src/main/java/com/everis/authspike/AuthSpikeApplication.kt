package com.everis.authspike

import android.app.Application

import com.google.firebase.database.FirebaseDatabase
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.Crashlytics
import com.everis.authspike.utils.RxBus


class AuthSpikeApplication : Application() {

    var rxBus: RxBus = RxBus()

    override fun onCreate() {
        super.onCreate()
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
