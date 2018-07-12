package com.everis.authspike.presenter

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface RegisterPresenter : BasePresenter{
    fun createUser(email: String, password: String)
    fun signInGoogle(account: GoogleSignInAccount)
}