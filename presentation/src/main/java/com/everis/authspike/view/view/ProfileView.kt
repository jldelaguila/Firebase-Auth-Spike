package com.everis.authspike.view.view

import java.io.InputStream

interface ProfileView : BaseView{

    fun getUserId():String

    fun getPicture():InputStream?

    fun displayPicture(url: String)

    fun getUrl(): String
    fun updateSuccessful()

}