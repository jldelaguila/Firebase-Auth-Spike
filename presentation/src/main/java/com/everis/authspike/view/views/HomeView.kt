package com.everis.authspike.view.views

import com.everis.domain.model.IntroMessage

/**
 * Created by everis on 25/04/18.
 */

interface HomeView : BaseView {

    fun changeUserStateText(userStateString: String)
    fun setIntroMessageText(introMessage: IntroMessage)
    fun logOutView()

}
