package com.eleganzit.amigo.model;

public class GUsersdata
{
    String id,userPhoto,username,tagline,device,isAdded;

    public GUsersdata(String id, String userPhoto, String username, String tagline, String device, String isAdded) {
        this.id = id;
        this.userPhoto = userPhoto;
        this.username = username;
        this.tagline = tagline;
        this.device = device;
        this.isAdded = isAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }
}
