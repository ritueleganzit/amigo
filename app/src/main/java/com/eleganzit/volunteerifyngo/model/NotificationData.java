package com.eleganzit.volunteerifyngo.model;

public class NotificationData
{
    String userPhoto,username,notification,time;

    public NotificationData(String userPhoto, String username, String notification, String time) {
        this.userPhoto = userPhoto;
        this.username = username;
        this.notification = notification;
        this.time = time;
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

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
