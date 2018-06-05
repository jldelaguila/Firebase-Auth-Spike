package com.everis.authspike.view.activity

import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity

import com.everis.authspike.R
import com.everis.authspike.navigator.Navigator
import com.everis.authspike.utils.PreferenceManager
import com.everis.authspike.utils.RxBus
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

open class BaseActivity : AppCompatActivity() {

    lateinit protected var navigator: Navigator
    lateinit protected var preferenceManager: PreferenceManager
    var rxBus: RxBus? = null
    var subscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = Navigator()
        preferenceManager = PreferenceManager(this)
    }

    protected fun displaySpikeNotification(title: String, message: String) {

        val mBuilder = NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, mBuilder.build())

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
            subscription?.add(rxBus?.toObservable()?.subscribe(action))
        } else {
            throw NullPointerException("Action must not be null. Override getBusAction method to provide an action to the bus.")
        }
    }

    internal fun unsubscribeBus() {
        subscription?.clear()
    }

}
