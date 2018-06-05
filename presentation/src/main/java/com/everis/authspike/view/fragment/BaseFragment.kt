package com.everis.authspike.view.fragment

import android.support.v4.app.Fragment
import com.everis.authspike.utils.RxBus
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription


abstract class BaseFragment(): Fragment() {

    var rxBus: RxBus? = RxBus()
    var subscription: CompositeSubscription? = null

    open fun getBusAction(): Action1<Any>? {
        return null
    }

    internal fun subscribeBus() {
        val action = getBusAction()
        if (action != null) {
            if (subscription == null) {
                subscription = CompositeSubscription()
            }
            subscription?.add(rxBus?.toObservable()?.subscribe(action))
        } else {
            throw NullPointerException("Action must not be null. Override getBusAction method to provide an action to the bus.")
        }
    }

    internal fun unsubscribeBus() {
        subscription?.clear()
    }

}