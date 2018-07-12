package com.everis.authspike.utils

import android.content.Context

/**
 * Created by everis on 26/04/18.
 */

class PreferenceManager(private val context: Context) {

    val activeSessionPreference: Boolean
        get() = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE).getBoolean(ACTIVE_SESSION, false)

    fun setActiveSession(activeSession: Boolean) {
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE).edit().putBoolean(ACTIVE_SESSION, activeSession).apply()
    }

    companion object {
        private const val USER_PREFERENCES = "AUTH_SPIKE_USER_PREFERENCES"

        private const val ACTIVE_SESSION = "USER_ACTIVE_SESION"
    }

}
