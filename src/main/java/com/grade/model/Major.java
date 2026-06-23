package com.grade.model;

public class Major {
    private String id;
    private String name;
    private String collegeId;

    public Major() {}

    public Major(String id, String name, String collegeId) {
        this.id = id;
        this.name = name;
        this.collegeId = collegeId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCollegeId() { return collegeId; }
    public void setCollegeId(String collegeId) { this.collegeId = collegeId; }
}
