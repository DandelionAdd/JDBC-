package com.grade.model;

import java.sql.Date;

public class Grade {
    private String studentId;
    private String courseId;
    private String teacherId;
    private int isResit; // 1: yes, 0: no
    private int score;
    private String semester;
    private String academicYear;
    private Date enrollDate;

    public Grade() {}

    public Grade(String studentId, String courseId, String teacherId, int isResit, int score, String semester, String academicYear, Date enrollDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.isResit = isResit;
        this.score = score;
        this.semester = semester;
        this.academicYear = academicYear;
        this.enrollDate = enrollDate;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public int getIsResit() { return isResit; }
    public void setIsResit(int isResit) { this.isResit = isResit; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public Date getEnrollDate() { return enrollDate; }
    public void setEnrollDate(Date enrollDate) { this.enrollDate = enrollDate; }
}
