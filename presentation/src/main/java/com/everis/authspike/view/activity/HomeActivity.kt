package com.everis.authspike.view.activity

import android.os.Bundle

import com.everis.authspike.R
import android.view.Menu
import android.view.MenuItem
import com.everis.authspike.presenter.HomePresenter
import com.everis.authspike.presenter.HomePresenterImpl
import com.everis.authspike.view.view.HomeView


class HomeActivity : BaseActivity(), HomeView {

    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenterImpl(this)
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
        navigator.navigateToRegisterActivity()
    }


}
