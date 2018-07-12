package com.everis.data.model

import com.everis.domain.model.IntroMessage

object IntroMessageEntityDataMapper {

    fun transform(introMessageEntity: IntroMessageEntity): IntroMessage {
        val introMessage = IntroMessage()
        introMessage.displayMessage = introMessageEntity.title!! + introMessageEntity.message!!
        return introMessage
    }

}
