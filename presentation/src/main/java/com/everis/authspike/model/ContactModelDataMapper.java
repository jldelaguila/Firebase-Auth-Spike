package com.everis.authspike.model;

import android.util.Log;

import com.everis.domain.model.LocalContact;

import java.util.ArrayList;
import java.util.List;

public class ContactModelDataMapper {

    private static ContactModel transform(LocalContact contact){
        ContactModel contactModel = new ContactModel();
        contactModel.setName(contact.getName());
        contactModel.setNumber(contact.getNumber());
        contactModel.setCloudUser(false);
        Log.d("MAPPER", "Nombre: " + contactModel.getName() + " y numero: " + contactModel.getNumber());
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
