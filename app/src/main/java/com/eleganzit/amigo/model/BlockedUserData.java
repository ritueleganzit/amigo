package com.eleganzit.amigo.model;

public class BlockedUserData
{
    String photo,username,status;

    public BlockedUserData(String photo, String username, String status) {
        this.photo = photo;
        this.username = username;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
