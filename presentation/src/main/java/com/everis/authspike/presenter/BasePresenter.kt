package com.everis.authspike.presenter

/**
 * Created by everis on 25/04/18.
 */

interface BasePresenter {

    fun onPause()
    fun onResume()
    fun onStop()
    fun onDestroy()

}
