package com.eleganzit.volunteerifyngo.model;

public class CampaignsData
{
    String date,title,subtitle,campaigns,time,banner;

    public CampaignsData(String date, String title, String subtitle, String campaigns, String time, String banner) {
        this.date = date;
        this.title = title;
        this.subtitle = subtitle;
        this.campaigns = campaigns;
        this.time = time;
        this.banner = banner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(String campaigns) {
        this.campaigns = campaigns;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
