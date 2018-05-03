package com.everis.authspike.presenter;

/**
 * Created by everis on 3/05/18.
 */

public interface ContactsPresenter extends BasePresenter {

    void getLocalContactBatch();
    void getLocalContactsIndiv();
    void syncUser(String number);
}
