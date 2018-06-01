package com.everis.authspike.view.views

import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser

/**
 * Created by everis on 3/05/18.
 */

interface ContactsView : BaseView {

    fun displayBatchContacts(contacts: List<LocalContact>)

    fun setSync(user: P2PUser)
}
