package com.everis.authspike.presenter

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface LoginPresenter : BasePresenter {

    fun signInUser(email: String, password: String)
    fun signInGoogle(account: GoogleSignInAccount)

}
