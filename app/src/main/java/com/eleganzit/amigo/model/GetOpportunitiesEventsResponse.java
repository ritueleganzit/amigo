package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOpportunitiesEventsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("event_data")
    @Expose
    private List<GetEvents> data = null;

    @SerializedName("opportunity_data")
    @Expose
    private List<GetOpportunities> opportunity_data = null;

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

    public List<GetEvents> getData() {
        return data;
    }

    public void setData(List<GetEvents> data) {
        this.data = data;
    }
    public List<GetOpportunities> getopportunity_data() {
        return opportunity_data;
    }

    public void setopportunity_data(List<GetOpportunities> opportunity_data) {
        this.opportunity_data = opportunity_data;
    }
}
