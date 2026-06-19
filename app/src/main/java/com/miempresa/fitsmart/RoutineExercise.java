package com.miempresa.fitsmart;

public class RoutineExercise {

    private int id;
    private int routineId;
    private int exerciseId;
    private String day;
    private int sets;
    private int reps;

    public RoutineExercise(int id, int routineId, int exerciseId, String day, int sets, int reps) {
        this.id = id;
        this.routineId = routineId;
        this.exerciseId = exerciseId;
        this.day = day;
        this.sets = sets;
        this.reps = reps;
    }

    public int getId() {
        return id;
    }

    public int getRoutineId() {
        return routineId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getDay() {
        return day;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }
}