package com.everis.authspike.view.activity

import android.os.Bundle

import com.everis.authspike.R
import android.view.Menu
import android.view.MenuItem
import com.everis.authspike.presenter.HomePresenter
import com.everis.authspike.presenter.HomePresenterImpl
import com.everis.authspike.view.view.HomeView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : BaseActivity(), HomeView {

    private lateinit var presenter: HomePresenter
    var mGoogleSignInClient : GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()

        mGoogleSignInClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,null)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build()

        presenter = HomePresenterImpl(this)
        presenter.loadUserEnabled(FirebaseAuth.getInstance().currentUser!!.uid)

        navigator.navigateToContactsFragment()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when {
            item!!.itemId == R.id.log_out -> {
                presenter.logOut()
                true
            }
            item.itemId == R.id.crash ->{
                presenter.crashApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun logOut() {
        Auth.GoogleSignInApi.signOut(mGoogleSignInClient)
        navigator.navigateToRegisterActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


}
