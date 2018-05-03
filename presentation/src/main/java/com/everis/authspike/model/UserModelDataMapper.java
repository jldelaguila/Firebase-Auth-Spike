package com.everis.authspike.model;

import android.text.TextUtils;

import com.everis.domain.model.User;

/**
 * Created by everis on 25/04/18.
 */

public class UserModelDataMapper {

    public static UserModel transform(User user){
        UserModel userModel = new UserModel();
        userModel.setUid(user.getUid());
        userModel.setDisplayName(user.getDisplayName());
        userModel.setEmail(user.getEmail());
        userModel.setState(user.getUid()!=null && !TextUtils.isEmpty(user.getUid()));
        return userModel;
    }

}
