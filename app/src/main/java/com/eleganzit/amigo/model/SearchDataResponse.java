package com.eleganzit.amigo.model;

import com.eleganzit.amigo.GetSearchData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("countMembers")
    @Expose
    private Integer countMembers;
    @SerializedName("data")
    @Expose
    private List<GetSearchData> data = null;

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

    public Integer getCountMembers() {
        return countMembers;
    }

    public void setCountMembers(Integer countMembers) {
        this.countMembers = countMembers;
    }

    public List<GetSearchData> getData() {
        return data;
    }

    public void setData(List<GetSearchData> data) {
        this.data = data;
    }

}
