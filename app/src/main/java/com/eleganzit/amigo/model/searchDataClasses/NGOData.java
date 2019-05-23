package com.eleganzit.amigo.model.searchDataClasses;

public class NGOData {
    String amigo_id,request_user_id,request_id,fullname,photo,followers,isFollowed,request_status,fail_status;

    public NGOData(String amigo_id, String request_user_id, String request_id, String fullname, String photo, String followers, String isFollowed, String request_status, String fail_status) {
        this.amigo_id = amigo_id;
        this.request_user_id = request_user_id;
        this.request_id = request_id;
        this.fullname = fullname;
        this.photo = photo;
        this.followers = followers;
        this.isFollowed = isFollowed;
        this.request_status = request_status;
        this.fail_status = fail_status;
    }

    public String getAmigo_id() {
        return amigo_id;
    }

    public void setAmigo_id(String amigo_id) {
        this.amigo_id = amigo_id;
    }

    public String getRequest_user_id() {
        return request_user_id;
    }

    public void setRequest_user_id(String request_user_id) {
        this.request_user_id = request_user_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getFail_status() {
        return fail_status;
    }

    public void setFail_status(String fail_status) {
        this.fail_status = fail_status;
    }
}
