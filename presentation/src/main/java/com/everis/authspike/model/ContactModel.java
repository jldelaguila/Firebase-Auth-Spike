package com.everis.authspike.model;


public class ContactModel {

    private String name;
    private String number;
    private boolean isCloudUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public boolean isCloudUser() {
        return isCloudUser;
    }

    public void setCloudUser(boolean cloudUser) {
        isCloudUser = cloudUser;
    }
}
