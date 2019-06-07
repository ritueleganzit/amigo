package com.eleganzit.amigo.model.donation;

import com.eleganzit.amigo.api.RetrofitInterface;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDonation{
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
}
