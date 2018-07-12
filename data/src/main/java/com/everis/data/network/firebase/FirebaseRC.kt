package com.everis.data.network.firebase

import rx.Observable

interface FirebaseRC {

    fun observeIsFilteredList(): Observable<Boolean>

}
