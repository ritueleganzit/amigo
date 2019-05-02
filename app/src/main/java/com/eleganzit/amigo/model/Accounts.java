package com.eleganzit.amigo.model;

import org.json.JSONObject;

import java.io.Serializable;

public class Accounts implements Serializable
{
    String photo,username,id;
    String object;

    public Accounts(String id, String object) {
        this.id = id;
        this.object = object;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public Accounts() {
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
