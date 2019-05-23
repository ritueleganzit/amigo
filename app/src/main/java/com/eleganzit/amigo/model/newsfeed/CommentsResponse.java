package com.eleganzit.amigo.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("commentsData")
    @Expose
    private List<Object> commentsData = null;

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

    public List<Object> getCommentsData() {
        return commentsData;
    }

    public void setCommentsData(List<Object> commentsData) {
        this.commentsData = commentsData;
    }


}
