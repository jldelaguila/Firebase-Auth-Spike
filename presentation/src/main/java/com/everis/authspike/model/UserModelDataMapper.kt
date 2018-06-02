package com.everis.authspike.model

import android.text.TextUtils

import com.everis.domain.model.User

/**
 * Created by everis on 25/04/18.
 */

object UserModelDataMapper {

    fun transform(user: User): UserModel = UserModel(user.uid, user.displayName, user.email, user.uid != null && !TextUtils.isEmpty(user.uid))

}
