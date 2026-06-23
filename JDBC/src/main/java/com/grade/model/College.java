package com.grade.model;

public class College {
    private String id;
    private String name;
    private int plannedCount;
    private int actualCount;
    private int majorCount;

    public College() {}

    public College(String id, String name, int plannedCount, int actualCount, int majorCount) {
        this.id = id;
        this.name = name;
        this.plannedCount = plannedCount;
        this.actualCount = actualCount;
        this.majorCount = majorCount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPlannedCount() { return plannedCount; }
    public void setPlannedCount(int plannedCount) { this.plannedCount = plannedCount; }

    public int getActualCount() { return actualCount; }
    public void setActualCount(int actualCount) { this.actualCount = actualCount; }

    public int getMajorCount() { return majorCount; }
    public void setMajorCount(int majorCount) { this.majorCount = majorCount; }
}
