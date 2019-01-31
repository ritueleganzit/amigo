package com.eleganzit.volunteerifyngo.model;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class ChatsData implements IMessage
{
    String id,time,username,message,seen;
    Date date;
    User user;

    public ChatsData(String id, Date date, String time, String username, String message, String seen, User user) {
        this.id = id;
        this.time = time;
        this.username = username;
        this.message = message;
        this.seen = seen;
        this.date = date;
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return message;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }
}
