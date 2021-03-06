package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetEvents implements Serializable{


    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("event_photo")
    @Expose
    private String eventPhoto;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("event_address")
    @Expose
    private String eventAddress;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("event_details")
    @Expose
    private String eventDetails;
    @SerializedName("co_host")
    @Expose
    private String coHost;
    @SerializedName("event_status")
    @Expose
    private String eventStatus;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(String eventPhoto) {
        this.eventPhoto = eventPhoto;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getCoHost() {
        return coHost;
    }

    public void setCoHost(String coHost) {
        this.coHost = coHost;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public GetEvents(String eventId, String eventName, String userId, String eventPhoto, String eventDate, String eventTime, String eventAddress, String lat, String lng, String eventDetails, String coHost, String eventStatus) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.userId = userId;
        this.eventPhoto = eventPhoto;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventAddress = eventAddress;
        this.lat = lat;
        this.lng = lng;
        this.eventDetails = eventDetails;
        this.coHost = coHost;
        this.eventStatus = eventStatus;
    }
}
