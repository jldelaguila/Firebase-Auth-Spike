package com.everis.authspike.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

import com.everis.authspike.R
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by everis on 26/04/18.
 */

class LifecycleService : Service() {

    private var preferenceManager: PreferenceManager? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val mBuilder = NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Servicio iniciado")
                .setContentText("El servicio para monitorear la app se inicio.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0, mBuilder.build())

        preferenceManager = PreferenceManager(applicationContext)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        val mBuilder = NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Me mataste")
                .setContentText("Solo dime por qué?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0, mBuilder.build())

        if (!preferenceManager!!.activeSessionPreference)
            FirebaseAuth.getInstance().signOut()

        stopSelf()
    }
}
