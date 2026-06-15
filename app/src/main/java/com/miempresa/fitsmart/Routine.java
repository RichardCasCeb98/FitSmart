package com.miempresa.fitsmart;

import java.util.List;

public class Routine {

    private String name;
    private int days;
    private List<Exercise> exercises;

    public Routine(String name, int days, List<Exercise> exercises) {
        this.name = name;
        this.days = days;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
