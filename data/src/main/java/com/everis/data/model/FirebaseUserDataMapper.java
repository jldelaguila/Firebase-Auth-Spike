package com.everis.data.model;

import com.everis.domain.model.User;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by everis on 25/04/18.
 */

public class FirebaseUserDataMapper {

    public static User transform(FirebaseUser firebaseUser){
        User user = new User();
        user.setUid(firebaseUser!=null?firebaseUser.getUid():"");
        user.setEmail(firebaseUser!=null?firebaseUser.getEmail():"");
        user.setDisplayName(firebaseUser!=null?firebaseUser.getDisplayName():"");
        return user;
    }

}
