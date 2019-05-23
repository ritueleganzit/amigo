package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddWorkResponse {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("place_name")
    @Expose
    private String placeName;

    @SerializedName("physical_place")
    @Expose
    private String physical_place;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_from")
    @Expose
    private String dateFrom;
    @SerializedName("date_to")
    @Expose
    private Object dateTo;
    @SerializedName("work_here")
    @Expose
    private String workHere;

    public String getPhysical_place() {
        return physical_place;
    }

    public void setPhysical_place(String physical_place) {
        this.physical_place = physical_place;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Object getDateTo() {
        return dateTo;
    }

    public void setDateTo(Object dateTo) {
        this.dateTo = dateTo;
    }

    public String getWorkHere() {
        return workHere;
    }

    public void setWorkHere(String workHere) {
        this.workHere = workHere;
    }
}
