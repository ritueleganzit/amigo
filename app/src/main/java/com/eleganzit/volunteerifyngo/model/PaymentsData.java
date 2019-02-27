package com.eleganzit.volunteerifyngo.model;

public class PaymentsData
{
    String packages,renewals,events,ads,date;

    public PaymentsData(String packages, String renewals, String events, String ads, String date) {
        this.packages = packages;
        this.renewals = renewals;
        this.events = events;
        this.ads = ads;
        this.date = date;
    }


    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getRenewals() {
        return renewals;
    }

    public void setRenewals(String renewals) {
        this.renewals = renewals;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
