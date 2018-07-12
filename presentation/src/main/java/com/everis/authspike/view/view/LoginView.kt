package com.everis.authspike.view.view


interface LoginView : BaseView {

    fun showLoggedInScreen()
    fun safeActiveSession(active: Boolean)
}
