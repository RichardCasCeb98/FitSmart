package com.miempresa.fitsmart;

public class RoutineEntity {

    private int id;
    private int userId;
    private String name;
    private int daysPerWeek;

    public RoutineEntity(int id, int userId, String name, int daysPerWeek) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.daysPerWeek = daysPerWeek;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getDaysPerWeek() {
        return daysPerWeek;
    }
}