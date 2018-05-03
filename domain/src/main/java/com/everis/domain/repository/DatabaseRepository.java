package com.everis.domain.repository;

import com.everis.domain.model.IntroMessage;
import com.everis.domain.model.P2PUser;
import com.everis.domain.model.User;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public interface DatabaseRepository {

    Observable<IntroMessage> getIntroMessage();
    Observable<Boolean> getUserEnabled(String userUid);
    Observable<P2PUser> getUserByPhoneReference(String phoneNumber);
    Observable<Void> observeRemoveValue(String collection, String node);

}
