package com.everis.domain.model

/**
 * Created by everis on 3/05/18.
 */

class P2PUser {

    var id: Long? = null
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var isEnable: Boolean = false
    var picture_url: String = ""

    override fun toString(): String {
        return phoneNumber!!
    }
}
