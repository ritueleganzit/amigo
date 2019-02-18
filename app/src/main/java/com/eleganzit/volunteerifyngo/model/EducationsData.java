package com.eleganzit.volunteerifyngo.model;

public class EducationsData
{
    String id,university;

    public EducationsData(String id, String university) {
        this.id = id;
        this.university = university;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
