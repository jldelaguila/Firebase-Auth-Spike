package com.everis.data.model;

import com.everis.domain.model.IntroMessage;

/**
 * Created by everis on 26/04/18.
 */

public class IntroMessageEntityDataMapper {

    public static IntroMessage transform(IntroMessageEntity introMessageEntity){
        IntroMessage introMessage = new IntroMessage();
        introMessage.setDisplayMessage(introMessageEntity.getTitle() + introMessageEntity.getMessage());
        return introMessage;
    }

}
