package com.everis.authspike.view.views


import com.everis.authspike.view.view.BaseView

interface LoginView : BaseView {

    fun showLoggedInScreen()
    fun safeActiveSession(active: Boolean)
}
