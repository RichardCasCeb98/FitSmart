package com.miempresa.fitsmart;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutineGenerator {

    private ExerciseRepository exerciseRepo;
    private RoutineRepository routineRepo;
    private final String[] weekDays = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    public RoutineGenerator(Context context) {
        exerciseRepo = new ExerciseRepository(context);
        routineRepo = new RoutineRepository(context);
    }

    public Routine generateRoutine(int age,String level, String goal) {

        List<Exercise> availableExercises = exerciseRepo.getExercises(level, goal);

        String routineName;
        int days;
        int exerciseCount;

        if (level.equals("Principiante")) {
            routineName = "Full Body";
            days = 3;
            exerciseCount = 6;
        }
        else if (level.equals("Intermedio")) {
            routineName = "Push Pull Legs";
            days = 4;
            exerciseCount = 8;
        }
        else {
            routineName = "Avanzada";
            days = 5;
            exerciseCount = 10;
        }

        if (age < 18) {
            days = Math.max(2, days - 1);
        }

        Collections.shuffle(availableExercises);
        List<Exercise> selectedExercises = new ArrayList<>();
        int limit = Math.min(exerciseCount, availableExercises.size());

        for (int i = 0; i < limit; i++) {
            selectedExercises.add(availableExercises.get(i));

        }

        return new Routine(routineName, days, selectedExercises);
    }

    public void saveRoutine(int userId, Routine routine) {

        long routineId = routineRepo.saveRoutine(userId, routine.getName(), routine.getDays());
        int dayIndex = 0;

        for (Exercise exercise : routine.getExercises()) {

            String day = weekDays[dayIndex];
            routineRepo.saveRoutineExercise(routineId, exercise.getId(), day, exercise.getSets(), exercise.getReps());
            dayIndex++;

            if (dayIndex >= routine.getDays()) {
                dayIndex = 0;
            }
        }
    }
}
