package com.eleganzit.amigo.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoUrlData {
    @SerializedName("post_data_id")
    @Expose
    private String postDataId;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    public String getPostDataId() {
        return postDataId;
    }

    public void setPostDataId(String postDataId) {
        this.postDataId = postDataId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
