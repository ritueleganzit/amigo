package com.eleganzit.amigo.model.addedu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EduResponse {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("physical_place")
    @Expose
    private String physicalPlace;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_from")
    @Expose
    private String dateFrom;
    @SerializedName("date_to")
    @Expose
    private String dateTo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhysicalPlace() {
        return physicalPlace;
    }

    public void setPhysicalPlace(String physicalPlace) {
        this.physicalPlace = physicalPlace;
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

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
