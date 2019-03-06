package com.eleganzit.amigo.model;

public class EventsData
{
    String date,title,time,location,banner;

    public EventsData(String date, String title, String time, String location, String banner) {
        this.date = date;
        this.title = title;
        this.time = time;
        this.location = location;
        this.banner = banner;
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
