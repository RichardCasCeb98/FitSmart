package com.miempresa.fitsmart;

public class Exercise {

    private int id;
    private String name;
    private String muscleGroup;
    private String level;
    private String goal;
    private int sets;
    private int reps;

    public Exercise(int id, String name, String muscleGroup, String level, String goal, int sets, int reps) {

        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.level = level;
        this.goal = goal;
        this.sets = sets;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }
}
