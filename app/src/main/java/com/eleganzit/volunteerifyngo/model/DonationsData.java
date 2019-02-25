package com.eleganzit.volunteerifyngo.model;

public class DonationsData
{
    String id, date, title, amount;

    public DonationsData(String id, String date, String title, String amount) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
