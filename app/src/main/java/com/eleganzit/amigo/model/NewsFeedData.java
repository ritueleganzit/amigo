package com.eleganzit.amigo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.eleganzit.amigo.model.newsfeed.CommentsData;
import com.eleganzit.amigo.model.newsfeed.LikesData;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsFeedData implements Parcelable,Cloneable {
    String post_id,user_photo,fullname,date_time,content,privacy,user_id,is_like;
    int total_comments,total_likes;
    String is_liked="false";
public static final int HEADER_TYPE=1;
public static final int MAIN_TYPE=10;
public static final int FOOTER_TYPE=11;

int view_type;
    ArrayList<PhotosData> imagesList;
   protected NewsFeedData(Parcel in) {
        post_id = in.readString();
        user_photo = in.readString();
        fullname = in.readString();
        date_time = in.readString();
        content = in.readString();
        privacy = in.readString();
        total_comments = in.readInt();
        total_likes = in.readInt();
        user_id = in.readString();
    }

    public String getIs_liked() {
        return is_liked;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static final Creator<NewsFeedData> CREATOR = new Creator<NewsFeedData>() {
        @Override
        public NewsFeedData createFromParcel(Parcel in) {
            return new NewsFeedData(in);
        }

        @Override
        public NewsFeedData[] newArray(int size) {
            return new NewsFeedData[size];
        }
    };

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    @Override
    public Object clone() {
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();

        Parcel parcel2 = Parcel.obtain();
        parcel2.unmarshall(bytes, 0, bytes.length);
        parcel2.setDataPosition(0);
        return NewsFeedData.CREATOR.createFromParcel(parcel2);
    }

    public NewsFeedData(int view_type,String post_id, String user_photo, String fullname, String date_time, String content, ArrayList<PhotosData> imagesList, int total_comments, int total_likes,String privacy,String is_liked) {
        this.view_type = view_type;
        this.post_id = post_id;
        this.user_photo = user_photo;
        this.fullname = fullname;
        this.date_time = date_time;
        this.content = content;
        this.imagesList = imagesList;

        this.total_comments = total_comments;
        this.total_likes = total_likes;
        this.privacy = privacy;
        this.is_liked = is_liked;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public ArrayList<PhotosData> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<PhotosData> imagesList) {
        this.imagesList = imagesList;
    }



    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(post_id);
        parcel.writeString(user_photo);
        parcel.writeString(fullname);
        parcel.writeString(date_time);
        parcel.writeString(content);
        parcel.writeString(privacy);
        parcel.writeInt(total_comments);
        parcel.writeInt(total_likes);
    }
}
