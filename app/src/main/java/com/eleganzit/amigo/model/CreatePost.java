package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatePost {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("tag_user_id")
    @Expose
    private TagUserId tagUserId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TagUserId getTagUserId() {
        return tagUserId;
    }

    public void setTagUserId(TagUserId tagUserId) {
        this.tagUserId = tagUserId;
    }


}
