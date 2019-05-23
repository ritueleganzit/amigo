package com.eleganzit.amigo.model.searchDataClasses;

import java.io.Serializable;

public class EventsData implements Serializable
{
    String event_id,date,title,time,location,banner;


    public EventsData(String event_id, String date, String title, String time, String location, String banner) {
        this.event_id = event_id;
        this.date = date;
        this.title = title;
        this.time = time;
        this.location = location;
        this.banner = banner;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
