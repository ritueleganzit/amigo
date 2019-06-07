package com.eleganzit.amigo.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNewsFeedData {
    @SerializedName("is_like")
    @Expose
    private String isLike;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("countPostdata")
    @Expose
    private Integer countPostdata;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("photo_url")
    @Expose
    private PhotoUrlResponse photoUrl;
    @SerializedName("countComment")
    @Expose
    private Integer countComment;
    @SerializedName("comments")
    @Expose
    private CommentsResponse comments;
    @SerializedName("countLikes")
    @Expose
    private Integer countLikes;
    @SerializedName("likes")
    @Expose
    private LikesResponse likes;

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

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

    public Integer getCountPostdata() {
        return countPostdata;
    }

    public void setCountPostdata(Integer countPostdata) {
        this.countPostdata = countPostdata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public PhotoUrlResponse getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(PhotoUrlResponse photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getCountComment() {
        return countComment;
    }

    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
    }

    public CommentsResponse getComments() {
        return comments;
    }

    public void setComments(CommentsResponse comments) {
        this.comments = comments;
    }

    public Integer getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(Integer countLikes) {
        this.countLikes = countLikes;
    }

    public LikesResponse getLikes() {
        return likes;
    }

    public void setLikes(LikesResponse likes) {
        this.likes = likes;
    }
}
