package com.everis.data.model;

import com.everis.domain.model.LocalContact;

/**
 * Created by everis on 3/05/18.
 */

public class LocalContactEntityDataMapper {

    public static LocalContact transform(LocalContactEntity localContactEntity){
        LocalContact localContact = new LocalContact();
        localContact.setName(localContactEntity.getName());
        localContact.setNumber(localContactEntity.getNumber());
        return localContact;
    }

}
