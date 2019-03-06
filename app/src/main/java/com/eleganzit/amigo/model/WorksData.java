package com.eleganzit.amigo.model;

public class WorksData
{
    String id,company;

    public WorksData(String id, String company) {
        this.id = id;
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
