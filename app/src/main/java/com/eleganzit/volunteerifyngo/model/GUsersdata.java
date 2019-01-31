package com.eleganzit.volunteerifyngo.model;

public class GUsersdata
{
    String userPhoto,username,tagline,device;

    public GUsersdata(String userPhoto, String username, String tagline, String device) {
        this.userPhoto = userPhoto;
        this.username = username;
        this.tagline = tagline;
        this.device = device;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
