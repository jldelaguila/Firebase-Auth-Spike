package com.everis.authspike.model;

import com.everis.domain.model.LocalContact;

import java.util.ArrayList;
import java.util.List;

public class ContactModelDataMapper {

    private static ContactModel transform(LocalContact contact){
        ContactModel contactModel = new ContactModel();
        contactModel.setName(contact.getName());
        contactModel.setNumber(contact.getNumber());
        contactModel.setCloudUser(false);
        return contactModel;
    }

    public static List<ContactModel> transform(List<LocalContact> contacts){
        List<ContactModel> contactsModels = new ArrayList<>();
        for (LocalContact contact:contacts){
            contactsModels.add(transform(contact));
        }
        return contactsModels;

    }

}
