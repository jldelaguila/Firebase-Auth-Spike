package com.everis.data.repository

import android.content.Context
import com.everis.data.network.firebase.FirebaseRC
import com.everis.data.network.firebase.FirebaseRCImpl
import com.everis.domain.repository.AppRepository
import rx.Observable

class AppDataRepository(val context: Context) : AppRepository{

    val firebaseRC: FirebaseRC

    init {
        firebaseRC = FirebaseRCImpl(context)
    }

    override fun isFilteredList(): Observable<Boolean> {
        return firebaseRC.observeIsFilteredList()
    }

}