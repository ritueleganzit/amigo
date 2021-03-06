package com.eleganzit.amigo.model;

import com.eleganzit.amigo.model.searchDataClasses.GetFollowdata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherUserData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("hometown")
    @Expose
    private String hometown;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;
    @SerializedName("proof")
    @Expose
    private String proof;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("web")
    @Expose
    private String web;
    @SerializedName("mission")
    @Expose
    private String mission;
    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("register_date")
    @Expose
    private String registerDate;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_token")
    @Expose
    private Object deviceToken;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("countFollow")
    @Expose
    private Integer countFollow;
    @SerializedName("countPost")
    @Expose
    private Integer countPost;
    @SerializedName("countFollowing")
    @Expose
    private Integer countFollowing;
    @SerializedName("is_follow")
    @Expose
    private String isFollow;
    @SerializedName("followdata")
    @Expose
    private GetFollowdata followdata;

    @SerializedName("is_block")
    @Expose
    private String is_block;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCountFollow() {
        return countFollow;
    }

    public void setCountFollow(Integer countFollow) {
        this.countFollow = countFollow;
    }

    public Integer getCountPost() {
        return countPost;
    }

    public void setCountPost(Integer countPost) {
        this.countPost = countPost;
    }

    public Integer getCountFollowing() {
        return countFollowing;
    }

    public void setCountFollowing(Integer countFollowing) {
        this.countFollowing = countFollowing;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public GetFollowdata getFollowdata() {
        return followdata;
    }

    public void setFollowdata(GetFollowdata followdata) {
        this.followdata = followdata;
    }


    public String getIs_block() {
        return is_block;
    }

    public void setIs_block(String is_block) {
        this.is_block = is_block;
    }

}
