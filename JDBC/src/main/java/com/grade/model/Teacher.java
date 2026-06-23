package com.grade.model;

public class Teacher {
    private String id;
    private String name;
    private String title;
    private String address;
    private String phone;
    private String position;
    private String email;
    private String majorId;

    public Teacher() {}

    public Teacher(String id, String name, String title, String address, String phone, String position, String email, String majorId) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.address = address;
        this.phone = phone;
        this.position = position;
        this.email = email;
        this.majorId = majorId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMajorId() { return majorId; }
    public void setMajorId(String majorId) { this.majorId = majorId; }
}
