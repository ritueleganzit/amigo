package com.eleganzit.amigo.model.searchDataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchAllDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("datango")
    @Expose
    private GetNGOList datango;
    @SerializedName("dataevent")
    @Expose
    private GetEventList dataevent;
    @SerializedName("dataVolunteer")
    @Expose
    private GetVolunteerList dataVolunteer;

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

    public GetNGOList getDataNGO() {
        return datango;
    }

    public void setNGOData(GetNGOList datango) {
        this.datango = datango;
    }

    public GetEventList getDataevent() {
        return dataevent;
    }

    public void setDataevent(GetEventList dataevent) {
        this.dataevent = dataevent;
    }

    public GetVolunteerList getDataVolunteer() {
        return dataVolunteer;
    }

    public void setDataVolunteer(GetVolunteerList dataVolunteer) {
        this.dataVolunteer = dataVolunteer;
    }
}
