package com.everis.authspike.view.views;

import com.everis.domain.model.LocalContact;

import java.util.List;

/**
 * Created by everis on 3/05/18.
 */

public interface ContactsView extends BaseView {

    void displayBatchContacts(List<LocalContact> contacts);

    void setSync(String phoneNumber);
}
