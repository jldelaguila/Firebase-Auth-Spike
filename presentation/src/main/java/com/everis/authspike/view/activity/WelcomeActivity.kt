package com.everis.authspike.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.everis.authspike.R
import com.everis.authspike.utils.Event
import com.everis.authspike.view.view.BaseView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import kotlinx.android.synthetic.main.activity_register.*

class WelcomeActivity : BaseActivity(), BaseView, GoogleApiClient.OnConnectionFailedListener {

    lateinit var gso : GoogleSignInOptions
    var mGoogleSignInClient : GoogleApiClient? = null
    val GOOGLE_SIGN_IN_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        navigator.navigateToLoginFragment(this)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        
        mGoogleSignInClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build()
    }

    fun googleSignIn(){
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient)
        startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    override fun showLoading() {
        this.progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN_REQUEST_CODE){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            val signInEvent = Event.GoogleSignInEvent()
            signInEvent.signInResult = result
            rxBus.send(signInEvent)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}
