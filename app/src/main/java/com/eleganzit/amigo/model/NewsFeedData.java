package com.eleganzit.amigo.model;

import java.util.ArrayList;

public class NewsFeedData {
    String username;
    ArrayList<String> arrayList;

    public NewsFeedData(String username, ArrayList<String> arrayList) {
        this.username = username;
        this.arrayList = arrayList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
