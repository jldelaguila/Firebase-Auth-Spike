package com.everis.authspike.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import com.everis.authspike.R;
import com.everis.authspike.navigator.Navigator;
import com.everis.authspike.utils.PreferenceManager;

/**
 * Created by everis on 25/04/18.
 */

public class BaseActivity extends AppCompatActivity {

    protected Navigator navigator;
    protected PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new Navigator();
        preferenceManager = new PreferenceManager(this);
    }

    protected void displaySpikeNotification(String title, String message){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, mBuilder.build());

    }

}
