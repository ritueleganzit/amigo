package com.eleganzit.amigo.model;

public class UsersData
{
    String id,name,photo;

    public UsersData(String id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
