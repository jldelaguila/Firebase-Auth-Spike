package com.everis.data.network.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.everis.data.BuildConfig
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

import rx.Observable
import rx.Subscriber

class FirebaseRCImpl(private val context: Context) : FirebaseRC {
    private var cacheExpiration: Long = 0

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        setupFirebaseRC()
    }

    override fun observeIsFilteredList(): Observable<Boolean> {
        return Observable.create { subscriber ->
            if (isThereNetworkConnection(subscriber)) {
                val fetchTask = remoteConfig.fetch(cacheExpiration)
                fetchTask.addOnSuccessListener {
                    remoteConfig.activateFetched()
                    subscriber.onNext(remoteConfig.getBoolean(REMOTE_CONFIG_IS_FILTERED_LIST))
                }

                fetchTask.addOnFailureListener {
                    subscriber.onError(Exception())
                }
            }
        }
    }


    private fun setupFirebaseRC() {
        val settings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        cacheExpiration = 3600
        if (settings.isDeveloperModeEnabled) {
            cacheExpiration = 60
        }
        remoteConfig.setConfigSettings(settings)
    }

    private fun isThereNetworkConnection(subscriber: Subscriber<*>): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnectedOrConnecting) return true
        subscriber.onError(Exception())
        return false
    }

    companion object {

        private val FLAG_FILTERED = 1
        private val REMOTE_CONFIG_IS_FILTERED_LIST = "is_filtered_list"
    }

}
