package com.eleganzit.amigo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagUserId {
    @SerializedName("tag_user_id")
    @Expose
    private List<TagUserId_> tagUserId = null;

    public List<TagUserId_> getTagUserId() {
        return tagUserId;
    }

    public void setTagUserId(List<TagUserId_> tagUserId) {
        this.tagUserId = tagUserId;
    }
}
