package com.everis.authspike.view.activity

import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity

import com.everis.authspike.R
import com.everis.authspike.navigator.Navigator
import com.everis.authspike.utils.PreferenceManager

/**
 * Created by everis on 25/04/18.
 */

abstract class BaseActivity : AppCompatActivity() {

    protected var navigator: Navigator = Navigator()
    protected lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)
    }

    protected fun displaySpikeNotification(title: String, message: String) {
        val mBuilder = NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0, mBuilder.build())
    }
}
