package com.grade.model;

import java.sql.Date;

public class Student {
    private String id;
    private int gender; // 1: male, 0: female
    private String name;
    private String address;
    private String zipCode;
    private String contactAddress;
    private String email;
    private String phone;
    private Date birthDate;
    private String classNo;
    private String majorId;

    public Student() {}

    public Student(String id, int gender, String name, String address, String zipCode, String contactAddress, String email, String phone, Date birthDate, String classNo, String majorId) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.contactAddress = contactAddress;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.classNo = classNo;
        this.majorId = majorId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getContactAddress() { return contactAddress; }
    public void setContactAddress(String contactAddress) { this.contactAddress = contactAddress; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getClassNo() { return classNo; }
    public void setClassNo(String classNo) { this.classNo = classNo; }

    public String getMajorId() { return majorId; }
    public void setMajorId(String majorId) { this.majorId = majorId; }
}
