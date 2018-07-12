package com.everis.authspike.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.everis.authspike.AuthSpikeApplication

import com.everis.authspike.navigator.Navigator
import com.everis.authspike.utils.PreferenceManager
import com.everis.authspike.utils.RxBus
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

abstract class BaseActivity : AppCompatActivity() {

    lateinit var navigator: Navigator
    lateinit var preferenceManager: PreferenceManager
    lateinit var rxBus: RxBus
    var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxBus = (application as AuthSpikeApplication).rxBus
        navigator = Navigator(this)
        preferenceManager = PreferenceManager(this)
    }


    internal fun getBusAction(): Action1<Any>? {
        return null
    }

    internal fun subscribeBus() {
        val action = getBusAction()
        if (action != null) {
            if (subscription == null) {
                subscription = CompositeSubscription()
            }
            subscription?.add(rxBus.toObservable().subscribe(action))
        } else {
            throw NullPointerException("Action must not be null. Override getBusAction method to provide an action to the bus.")
        }
    }

    internal fun unsubscribeBus() {
        subscription?.clear()
    }

}
