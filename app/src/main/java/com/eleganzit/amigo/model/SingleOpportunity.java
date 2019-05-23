package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleOpportunity {
    @SerializedName("opportunity_id")
    @Expose
    private String opportunityId;
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
    @SerializedName("applycount")
    @Expose
    private String applycount;
    @SerializedName("rejectcount")
    @Expose
    private String rejectcount;

    @SerializedName("opportunity_name")
    @Expose
    private String opportunity_name;


    public String getOpportunity_name() {
        return opportunity_name;
    }

    public void setOpportunity_name(String opportunity_name) {
        this.opportunity_name = opportunity_name;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
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

    public String getApplycount() {
        return applycount;
    }

    public void setApplycount(String applycount) {
        this.applycount = applycount;
    }

    public String getRejectcount() {
        return rejectcount;
    }

    public void setRejectcount(String rejectcount) {
        this.rejectcount = rejectcount;
    }
}
