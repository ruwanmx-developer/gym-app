package com.ruwan.reacongym.models;

public class ScheduleModel {
    private int id;
    private int type;
    private String name;
    private int reps;

    public ScheduleModel(int type) {
        this.type = type;
    }

    public ScheduleModel(int id, int type, String name, int reps) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.reps = reps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
