package com.everis.authspike.model

import com.everis.domain.model.LocalContact

object ContactModelDataMapper {

    private fun transform(contact: LocalContact): ContactModel = ContactModel(contact.name!!, contact.number!!, false)

    fun transform(contacts: List<LocalContact>): List<ContactModel> = contacts.map { transform(it) }
}