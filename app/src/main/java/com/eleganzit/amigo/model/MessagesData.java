package com.eleganzit.amigo.model;

public class MessagesData
{
    String chat_photo,username,time,events,people,message_count;

    public MessagesData(String chat_photo, String username, String time, String events, String people, String message_count) {
        this.chat_photo = chat_photo;
        this.username = username;
        this.time = time;
        this.events = events;
        this.people = people;
        this.message_count = message_count;
    }

    public String getChat_photo() {
        return chat_photo;
    }

    public void setChat_photo(String chat_photo) {
        this.chat_photo = chat_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getMessage_count() {
        return message_count;
    }

    public void setMessage_count(String message_count) {
        this.message_count = message_count;
    }
}
