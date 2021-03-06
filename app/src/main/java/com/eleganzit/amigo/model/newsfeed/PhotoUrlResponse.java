package com.eleganzit.amigo.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoUrlResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("PhotoUrl")
    @Expose
    private List<PhotoUrlData> photoUrl = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PhotoUrlData> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(List<PhotoUrlData> photoUrl) {
        this.photoUrl = photoUrl;
    }
}
