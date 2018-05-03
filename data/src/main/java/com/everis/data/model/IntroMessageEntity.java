package com.everis.data.model;

/**
 * Created by everis on 26/04/18.
 */

public class IntroMessageEntity {

    private String uid;
    private String title;
    private String message;

    public IntroMessageEntity() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
