package com.everis.authspike.presenter

import com.everis.domain.model.LocalContact

interface ContactsPresenter : BasePresenter {

    fun getLocalContactBatch()
    fun syncUserContactsByQuery(contacts: List<LocalContact>)
    fun syncUserContactsByRef(contacts: List<LocalContact>)
    fun getContactsListConfig()
}
