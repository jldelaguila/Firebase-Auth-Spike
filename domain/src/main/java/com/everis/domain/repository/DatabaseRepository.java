package com.everis.domain.repository;

import com.everis.domain.model.IntroMessage;
import com.everis.domain.model.User;

import rx.Observable;

/**
 * Created by everis on 26/04/18.
 */

public interface DatabaseRepository {

    Observable<IntroMessage> getIntroMessage();
    Observable<Boolean> getUserEnabled(String userUid);

}
