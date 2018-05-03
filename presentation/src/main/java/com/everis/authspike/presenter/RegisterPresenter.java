package com.everis.authspike.presenter;

/**
 * Created by everis on 25/04/18.
 */

public interface RegisterPresenter extends BasePresenter {

    void createUser(String email, String password);
    void signInUser(String email, String password);

}
