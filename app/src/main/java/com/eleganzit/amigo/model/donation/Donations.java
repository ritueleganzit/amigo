package com.eleganzit.amigo.model.donation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donations {

    @SerializedName("donation_id")
    @Expose
    private String donationId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("donation_image")
    @Expose
    private String donationImage;
    @SerializedName("need_amount")
    @Expose
    private String needAmount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("donation_start_date")
    @Expose
    private String donationStartDate;
    @SerializedName("donation_end_date")
    @Expose
    private String donationEndDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDonationImage() {
        return donationImage;
    }

    public void setDonationImage(String donationImage) {
        this.donationImage = donationImage;
    }

    public String getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(String needAmount) {
        this.needAmount = needAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDonationStartDate() {
        return donationStartDate;
    }

    public void setDonationStartDate(String donationStartDate) {
        this.donationStartDate = donationStartDate;
    }

    public String getDonationEndDate() {
        return donationEndDate;
    }

    public void setDonationEndDate(String donationEndDate) {
        this.donationEndDate = donationEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
