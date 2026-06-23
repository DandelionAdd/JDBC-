package com.grade.model;

public class Course {
    private String id;
    private String name;
    private String semester;
    private String academicYear;
    private int credits;
    private int plannedHours;
    private int labHours;
    private int weeklyHours;
    private String nature;
    private String examCategory;
    private String majorId;
    private String remark;

    public Course() {}

    public Course(String id, String name, String semester, String academicYear, int credits, int plannedHours, int labHours, int weeklyHours, String nature, String examCategory, String majorId, String remark) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.academicYear = academicYear;
        this.credits = credits;
        this.plannedHours = plannedHours;
        this.labHours = labHours;
        this.weeklyHours = weeklyHours;
        this.nature = nature;
        this.examCategory = examCategory;
        this.majorId = majorId;
        this.remark = remark;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getPlannedHours() { return plannedHours; }
    public void setPlannedHours(int plannedHours) { this.plannedHours = plannedHours; }

    public int getLabHours() { return labHours; }
    public void setLabHours(int labHours) { this.labHours = labHours; }

    public int getWeeklyHours() { return weeklyHours; }
    public void setWeeklyHours(int weeklyHours) { this.weeklyHours = weeklyHours; }

    public String getNature() { return nature; }
    public void setNature(String nature) { this.nature = nature; }

    public String getExamCategory() { return examCategory; }
    public void setExamCategory(String examCategory) { this.examCategory = examCategory; }

    public String getMajorId() { return majorId; }
    public void setMajorId(String majorId) { this.majorId = majorId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
