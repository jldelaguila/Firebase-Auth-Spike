package com.everis.authspike.presenter

interface RegisterPresenter : BasePresenter{
    fun createUser(email: String, password: String)
}