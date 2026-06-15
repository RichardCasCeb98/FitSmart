package com.miempresa.fitsmart;

import android.content.Context;

import java.util.List;

public class RoutineGenerator {

    private ExerciseRepository exerciseRepo;

    public RoutineGenerator(Context context) {
        exerciseRepo = new ExerciseRepository(context);
    }

    public Routine generateRoutine(int age, String level, String goal) {

        List<Exercise> exercises = exerciseRepo.getExercises(level, goal);

        String routineName;
        int days;

        if(level.equals("Principiante")) {
            routineName = "Full Body";
            days = 3;
        }
        else if(level.equals("Intermedio")) {
            routineName = "Push Pull Legs";
            days = 4;
        }
        else {
            routineName = "Avanzada";
            days = 5;
        }

        if(age < 18) {
            days = Math.max(2, days - 1);
        }

        return new Routine(routineName, days, exercises);
    }
}
