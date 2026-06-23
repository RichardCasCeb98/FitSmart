package com.miempresa.fitsmart;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutineGenerator {

    private ExerciseRepository exerciseRepo;
    private RoutineRepository routineRepo;
    private static final int exercises_per_day = 5;
    private final String[] weekDays = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    public RoutineGenerator(Context context) {
        exerciseRepo = new ExerciseRepository(context);
        routineRepo = new RoutineRepository(context);
    }

    public Routine generateRoutine(int age, String level, String goal) {

        List<Exercise> availableExercises = exerciseRepo.getExercises(level, goal);

        String routineName;
        int days;

        if (level.equals("Principiante")) {
            routineName = "Full Body";
            days = 3;
        }
        else if (level.equals("Intermedio")) {
            routineName = "Push Pull Legs";
            days = 4;
        }
        else {
            routineName = "Avanzada";
            days = 5;
        }

        if (age < 18) {
            days = Math.max(2, days - 1);
        }

        int totalExercisesNeeded = days * exercises_per_day;
        List<Exercise> selectedExercises = new ArrayList<>();

        if (!availableExercises.isEmpty()) {

            Collections.shuffle(availableExercises);
            int index = 0;

            while (selectedExercises.size() < totalExercisesNeeded) {

                if (index >= availableExercises.size()) {
                    index = 0;
                    Collections.shuffle(availableExercises);
                }

                selectedExercises.add(availableExercises.get(index));
                index++;
            }
        }
        return new Routine(routineName, days, selectedExercises);
    }

    public void saveRoutine(int userId, Routine routine) {

        long routineId = routineRepo.saveRoutine(userId, routine.getName(), routine.getDays());
        int currentDay = 0;
        int counter = 0;

        for (Exercise exercise : routine.getExercises()) {

            String day = weekDays[currentDay];
            routineRepo.saveRoutineExercise(routineId, exercise.getId(), day, exercise.getSets(), exercise.getReps());
            counter++;

            if (counter >= exercises_per_day && currentDay < routine.getDays() - 1) {
                currentDay++;
                counter = 0;
            }
        }
    }

    private void addExercises(List<Exercise> routineExercises, String muscleGroup, String level, String goal, int amount) {

        List<Exercise> exercises = exerciseRepo.getExercisesByMuscleGroup(muscleGroup, level, goal);
        Collections.shuffle(exercises);

        int limit = Math.min(amount, exercises.size());

        for (int i = 0; i < limit; i++) {
            routineExercises.add(exercises.get(i));
        }
    }
}
