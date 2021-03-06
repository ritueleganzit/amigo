package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOpportunities implements Serializable{


    @SerializedName("opportunity_id")
    @Expose
    private String opportunityId;
    @SerializedName("opportunity_name")
    @Expose
    private String opportunityName;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("opportunity_image")
    @Expose
    private String opportunityImage;
    @SerializedName("opportunity_date")
    @Expose
    private String opportunityDate;
    @SerializedName("opportunity_time")
    @Expose
    private String opportunityTime;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("positions")
    @Expose
    private String positions;
    @SerializedName("looking_for")
    @Expose
    private String lookingFor;
    @SerializedName("opportunity_status")
    @Expose
    private String opportunityStatus;

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpportunityImage() {
        return opportunityImage;
    }

    public void setOpportunityImage(String opportunityImage) {
        this.opportunityImage = opportunityImage;
    }

    public String getOpportunityDate() {
        return opportunityDate;
    }

    public void setOpportunityDate(String opportunityDate) {
        this.opportunityDate = opportunityDate;
    }

    public String getOpportunityTime() {
        return opportunityTime;
    }

    public void setOpportunityTime(String opportunityTime) {
        this.opportunityTime = opportunityTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getOpportunityStatus() {
        return opportunityStatus;
    }

    public void setOpportunityStatus(String opportunityStatus) {
        this.opportunityStatus = opportunityStatus;
    }

    public GetOpportunities(String opportunityId, String opportunityName, String userId, String opportunityImage, String opportunityDate, String opportunityTime, String address, String lat, String lng, String details, String positions, String lookingFor, String opportunityStatus) {
        this.opportunityId = opportunityId;
        this.opportunityName = opportunityName;
        this.userId = userId;
        this.opportunityImage = opportunityImage;
        this.opportunityDate = opportunityDate;
        this.opportunityTime = opportunityTime;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.details = details;
        this.positions = positions;
        this.lookingFor = lookingFor;
        this.opportunityStatus = opportunityStatus;
    }
}
