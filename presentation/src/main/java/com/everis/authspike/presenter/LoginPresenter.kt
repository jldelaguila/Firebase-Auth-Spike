package com.everis.authspike.presenter

interface LoginPresenter : BasePresenter {

    fun signInUser(email: String, password: String)

}
