package com.everis.authspike.presenter

/**
 * Created by everis on 25/04/18.
 */

interface RegisterPresenter : BasePresenter {

    fun createUser(email: String, password: String)
    fun signInUser(email: String, password: String)

}
