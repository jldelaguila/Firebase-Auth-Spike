package com.everis.domain.model

class P2PUser {

    var id: Long? = null
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var picture_url: String = ""
    var enable: Boolean = false

    override fun toString(): String {
        if(phoneNumber!=null)
            return phoneNumber as String
        else
            return  ""
    }
}
