package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eleganz on 30/4/19.
 */

public class GetUserResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<UserResponse> data = null;

    private List<GetWorkList> getWorkLists = null;



    public List<GetWorkList> getWorkLists() {
        return getWorkLists;
    }

    public void setWorkList(List<GetWorkList> data) {
        this.getWorkLists = getWorkLists;
    }

    private List<AddWorkResponse> getworkdata = null;

    public List<AddWorkResponse> getWorkResponse() {
        return getworkdata;
    }

    public void setWorkData(List<AddWorkResponse> getworkdata) {
        this.getworkdata = getworkdata;
    }

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

    public List<UserResponse> getData() {
        return data;
    }

    public void setData(List<UserResponse> data) {
        this.data = data;
    }
}
