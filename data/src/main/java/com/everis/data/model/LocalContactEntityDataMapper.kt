package com.everis.data.model

import com.everis.domain.model.LocalContact

object LocalContactEntityDataMapper {

    fun transform(localContactEntity: LocalContactEntity): LocalContact {
        val localContact = LocalContact()
        localContact.name = localContactEntity.name
        localContact.number = localContactEntity.number!!.replace("[^0-9]".toRegex(), "")
        return localContact
    }

}
