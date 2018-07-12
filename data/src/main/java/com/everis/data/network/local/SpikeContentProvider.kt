package com.everis.data.network.local

import com.everis.data.model.LocalContactEntity

import rx.Observable

/**
 * Created by everis on 3/05/18.
 */

interface SpikeContentProvider {

    fun getLocalContactsBatch(): Observable<List<LocalContactEntity>>

}
