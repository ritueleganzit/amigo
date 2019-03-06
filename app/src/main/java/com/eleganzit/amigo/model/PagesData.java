package com.eleganzit.amigo.model;

public class PagesData
{
    String id,photo,title,likes,liked;

    public PagesData(String id, String photo, String title, String likes, String liked) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.likes = likes;
        this.liked = liked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }
}
