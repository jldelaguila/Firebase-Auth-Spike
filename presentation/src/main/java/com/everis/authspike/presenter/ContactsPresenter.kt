package com.everis.authspike.presenter

import com.everis.domain.model.LocalContact

/**
 * Created by everis on 3/05/18.
 */

interface ContactsPresenter : BasePresenter {

    fun getLocalContactBatch()
    fun syncUserContactsByQuery(contacts: List<LocalContact>)

    fun syncUserContactsByRef(contacts: List<LocalContact>)
}
