package com.eleganzit.amigo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class GetSearchData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("contact_person")
    @Expose
    private Object contactPerson;
    @SerializedName("contact_person_number")
    @Expose
    private Object contactPersonNumber;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("hometown")
    @Expose
    private String hometown;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("memberCount")
    @Expose
    private Object memberCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Object contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Object getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(Object contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Object memberCount) {
        this.memberCount = memberCount;
    }
}
