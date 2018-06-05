package com.everis.authspike.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.everis.authspike.AuthSpikeApplication
import com.everis.authspike.utils.PreferenceManager
import com.everis.authspike.utils.RxBus
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription


abstract class BaseFragment: Fragment() {

    lateinit var rxBus: RxBus
    var subscription: CompositeSubscription? = null
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxBus = (activity?.application as AuthSpikeApplication).rxBus
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferenceManager = PreferenceManager(activity as AppCompatActivity)
    }

    open fun getBusAction(): Action1<Any>? {
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