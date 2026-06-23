package com.grade.model;

public class TeachingTask {
    private String courseId;
    private String teacherId;
    private String actualYear;
    private String actualSemester;
    private String completionStatus;

    public TeachingTask() {}

    public TeachingTask(String courseId, String teacherId, String actualYear, String actualSemester, String completionStatus) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.actualYear = actualYear;
        this.actualSemester = actualSemester;
        this.completionStatus = completionStatus;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getActualYear() { return actualYear; }
    public void setActualYear(String actualYear) { this.actualYear = actualYear; }

    public String getActualSemester() { return actualSemester; }
    public void setActualSemester(String actualSemester) { this.actualSemester = actualSemester; }

    public String getCompletionStatus() { return completionStatus; }
    public void setCompletionStatus(String completionStatus) { this.completionStatus = completionStatus; }
}
