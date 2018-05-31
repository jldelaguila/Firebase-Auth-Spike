package com.everis.data.repository
import com.everis.data.model.LocalContactEntityDataMapper
import com.everis.data.network.local.SpikeContentProvider
import com.everis.domain.model.LocalContact
import com.everis.domain.repository.ContentProviderRepository

import java.util.ArrayList

import rx.Observable

class ContentProviderDataRepository(private val database: SpikeContentProvider) : ContentProviderRepository {

    override fun getLocalContactsBatch(): Observable<List<LocalContact>> {
        return database.getLocalContactsBatch().map { localContactEntities ->
            val localContacts = ArrayList<LocalContact>()

            for (contactEntity in localContactEntities) {
                val localContact = LocalContactEntityDataMapper.transform(contactEntity)
                localContacts.add(localContact)
            }

            localContacts
        }
    }
}
