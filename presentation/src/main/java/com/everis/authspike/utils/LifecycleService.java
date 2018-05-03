package com.everis.authspike.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.everis.authspike.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by everis on 26/04/18.
 */

public class LifecycleService extends Service {

    private PreferenceManager preferenceManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Servicio iniciado")
                .setContentText("El servicio para monitorear la app se inicio.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, mBuilder.build());

        preferenceManager = new PreferenceManager(getApplicationContext());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Me mataste")
                .setContentText("Solo dime por qué?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, mBuilder.build());

        if(!preferenceManager.getActiveSessionPreference())
            FirebaseAuth.getInstance().signOut();

        stopSelf();
    }
}
