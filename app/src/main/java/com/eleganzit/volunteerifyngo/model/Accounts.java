package com.eleganzit.volunteerifyngo.model;

public class Accounts
{
    String photo,username;

    public Accounts(String photo, String username) {
        this.photo = photo;
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
