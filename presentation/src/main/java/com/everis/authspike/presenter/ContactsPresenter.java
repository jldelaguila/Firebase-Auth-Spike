package com.everis.authspike.presenter;

import com.everis.domain.model.LocalContact;

import java.util.List;

/**
 * Created by everis on 3/05/18.
 */

public interface ContactsPresenter extends BasePresenter {

    void getLocalContactBatch();
    void syncUserContactsByQuery(List<LocalContact> contacts);

    void syncUserContactsByRef(List<LocalContact> contacts);
}
