package com.everis.domain.repository

import rx.Observable

interface AppRepository{

    fun isFilteredList(): Observable<Boolean>

}