package com.everis.domain.repository

import com.everis.domain.model.LocalContact

import rx.Observable

/**
 * Created by everis on 3/05/18.
 */

interface ContentProviderRepository {

    fun getLocalContactsBatch():Observable<List<LocalContact>>

}
