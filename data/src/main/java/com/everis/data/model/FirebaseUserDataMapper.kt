package com.everis.data.model

import com.everis.domain.model.User
import com.google.firebase.auth.FirebaseUser

object FirebaseUserDataMapper {

    fun transform(firebaseUser: FirebaseUser?): User {
        val user = User()
        user.uid = firebaseUser?.uid ?: ""
        user.email = if (firebaseUser != null) firebaseUser.email else ""
        user.displayName = if (firebaseUser != null) firebaseUser.displayName else ""
        return user
    }

}
