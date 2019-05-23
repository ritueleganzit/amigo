package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagUserId_ {
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("tag_user_id")
    @Expose
    private String tagUserId;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTagUserId() {
        return tagUserId;
    }

    public void setTagUserId(String tagUserId) {
        this.tagUserId = tagUserId;
    }

}
