package com.everis.authspike.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by everis on 26/04/18.
 */

public class PreferenceManager {

    private static final String USER_PREFERENCES = "AUTH_SPIKE_USER_PREFERENCES";

    private static final String ACTIVE_SESSION = "USER_ACTIVE_SESION";

    private Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public boolean getActiveSessionPreference(){
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE).getBoolean(ACTIVE_SESSION,false);
    }

    public void setActiveSession(boolean activeSesion){
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE).edit().putBoolean(ACTIVE_SESSION, activeSesion).apply();
    }

}
