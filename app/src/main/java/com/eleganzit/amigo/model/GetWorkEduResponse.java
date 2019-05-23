package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWorkEduResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("datawork")
    @Expose
    private Datawork datawork;
    @SerializedName("dataeducation")
    @Expose
    private Dataeducation dataeducation;

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

    public Datawork getDatawork() {
        return datawork;
    }

    public void setDatawork(Datawork datawork) {
        this.datawork = datawork;
    }

    public Dataeducation getDataeducation() {
        return dataeducation;
    }

    public void setDataeducation(Dataeducation dataeducation) {
        this.dataeducation = dataeducation;
    }
}
