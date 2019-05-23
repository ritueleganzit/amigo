package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddEventResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("countMembers")
    @Expose
    private Object countMembers;
    @SerializedName("data")
    @Expose
    private List<AddEvent> data = null;

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

    public Object getCountMembers() {
        return countMembers;
    }

    public void setCountMembers(Object countMembers) {
        this.countMembers = countMembers;
    }

    public List<AddEvent> getData() {
        return data;
    }

    public void setData(List<AddEvent> data) {
        this.data = data;
    }
}
