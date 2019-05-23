package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Followdata {
    @SerializedName("fail_status")
    @Expose
    private Integer failStatus;

    public Integer getFailStatus() {
        return failStatus;
    }

    public void setFailStatus(Integer failStatus) {
        this.failStatus = failStatus;
    }


}
