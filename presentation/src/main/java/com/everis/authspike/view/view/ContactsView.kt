package com.everis.authspike.view.view

import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser

interface ContactsView : BaseView {

    fun displayBatchContacts(contacts: List<LocalContact>)
    fun setSync(user: P2PUser)
    fun setListConfig(listConfig: Boolean)
}
