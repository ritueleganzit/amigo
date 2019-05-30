package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Followdata {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("request_user_id")
    @Expose
    private String requestUserId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fail_status")
    @Expose
    private String fail_status;
    @SerializedName("request_id")
    @Expose
    private String request_id;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFail_status() {
        return fail_status;
    }

    public void setFail_status(String fail_status) {
        this.fail_status = fail_status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }


}
