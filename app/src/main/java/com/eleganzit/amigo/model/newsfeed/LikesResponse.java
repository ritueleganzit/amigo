package com.eleganzit.amigo.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LikesResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("likesData")
    @Expose
    private List<Object> likesData = null;

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

    public List<Object> getLikesData() {
        return likesData;
    }

    public void setLikesData(List<Object> likesData) {
        this.likesData = likesData;
    }
}
