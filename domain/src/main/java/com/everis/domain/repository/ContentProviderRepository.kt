package com.everis.domain.repository

import com.everis.domain.model.LocalContact

import rx.Observable

interface ContentProviderRepository {

    fun getLocalContactsBatch():Observable<List<@JvmSuppressWildcards LocalContact>>

}
