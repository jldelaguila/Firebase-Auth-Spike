package com.everis.authspike.presenter

/**
 * Created by everis on 25/04/18.
 */

interface HomePresenter : BasePresenter {

    fun loadUserState()
    fun loadUserEnabled(userUid: String)
    fun logOut()
    fun deleteUser()
    fun crashApp()
}
