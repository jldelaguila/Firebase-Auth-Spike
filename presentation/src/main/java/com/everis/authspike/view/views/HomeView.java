package com.everis.authspike.view.views;

import com.everis.domain.model.IntroMessage;

/**
 * Created by everis on 25/04/18.
 */

public interface HomeView extends BaseView {

    void changeUserStateText(String userStateString);
    void setIntroMessageText(IntroMessage introMessage);
    void logOutView();

}
