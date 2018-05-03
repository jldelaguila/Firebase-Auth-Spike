package com.everis.authspike.presenter;

/**
 * Created by everis on 25/04/18.
 */

public interface HomePresenter extends BasePresenter {

    void loadUserState();
    void loadUserEnabled(String userUid);
    void loadIntroMessage();
    void logOut();
    void deleteUser();
    void loadUserByPhoneReference(String phoneNumber);
    void loadUserByPhoneQuery(String phoneNumber);

}
