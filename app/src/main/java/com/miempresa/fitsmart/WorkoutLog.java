package com.miempresa.fitsmart;

public class WorkoutLog {

    private int id;
    private int userId;
    private int exerciseId;
    private String date;
    private double weight;
    private int reps;
    private int sets;

    public WorkoutLog(int id, int userId, int exerciseId, String date, double weight, int reps, int sets) {

        this.id = id;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.date = date;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getDate() {
        return date;
    }

    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }
}