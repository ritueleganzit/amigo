package com.eleganzit.amigo.model;

public class SearchData
{
    String id, photo, username;

    public SearchData(String id, String photo, String username) {
        this.id = id;
        this.photo = photo;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
