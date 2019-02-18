package com.eleganzit.volunteerifyngo.model;

public class FollowersData
{
    String photo,username,followers,isFollowing;

    public FollowersData(String photo, String username, String followers, String isFollowing) {
        this.photo = photo;
        this.username = username;
        this.followers = followers;
        this.isFollowing = isFollowing;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(String isFollowing) {
        this.isFollowing = isFollowing;
    }
}
