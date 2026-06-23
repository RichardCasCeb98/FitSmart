package com.miempresa.fitsmart;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        String routineName;
        int days;
        List<Exercise> selectedExercises = new ArrayList<>();
        Set<Integer> usedExercises = new HashSet<>();

        if (level.equals("Principiante")) {

            routineName = "Full Body";
            days = 3;

            for (int i = 0; i < days; i++) {

                addExercises(selectedExercises, usedExercises, "Pecho", level, goal, 1);
                addExercises(selectedExercises, usedExercises, "Espalda", level, goal, 1);
                addExercises(selectedExercises, usedExercises, "Pierna", level, goal, 1);
                addExercises(selectedExercises, usedExercises, "Hombro", level, goal, 1);

                if (i % 2 == 0) {
                    addExercises(selectedExercises, usedExercises, "Abdomen", level, goal, 1);
                } else {
                    addExercises(selectedExercises, usedExercises, "Cardio", level, goal, 1);
                }
            }
        }
        else if (level.equals("Intermedio")) {

            routineName = "Push Pull Legs";
            days = 4;

            addExercises(selectedExercises, usedExercises, "Pecho", level, goal, 3);
            addExercises(selectedExercises, usedExercises, "Triceps", level, goal, 2);

            addExercises(selectedExercises, usedExercises, "Espalda", level, goal, 3);
            addExercises(selectedExercises, usedExercises, "Biceps", level, goal, 2);

            addExercises(selectedExercises, usedExercises, "Pierna", level, goal, 3);
            addExercises(selectedExercises, usedExercises, "Abdomen", level, goal, 2);

            addExercises(selectedExercises, usedExercises, "Hombro", level, goal, 3);
            addExercises(selectedExercises, usedExercises, "Cardio", level, goal, 2);
        }
        else {

            routineName = "Avanzada";
            days = 5;

            addExercises(selectedExercises, usedExercises, "Pecho", level, goal, 5);

            addExercises(selectedExercises, usedExercises, "Espalda", level, goal, 5);

            addExercises(selectedExercises, usedExercises, "Pierna", level, goal, 5);

            addExercises(selectedExercises, usedExercises, "Hombro", level, goal, 3);
            addExercises(selectedExercises, usedExercises, "Abdomen", level, goal, 2);

            addExercises(selectedExercises, usedExercises, "Biceps", level, goal, 2);
            addExercises(selectedExercises, usedExercises, "Triceps", level, goal, 2);
            addExercises(selectedExercises, usedExercises, "Cardio", level, goal, 1);
        }

        if (age < 18) {
            days = Math.max(2, days - 1);
        }

        int totalNeeded = days * exercises_per_day;
        List<Exercise> allExercises = exerciseRepo.getExercises(level, goal);

        if (!allExercises.isEmpty()) {

            Collections.shuffle(allExercises);
            int index = 0;

            while (selectedExercises.size() < totalNeeded) {

                if (index >= allExercises.size()) {
                    index = 0;
                    Collections.shuffle(allExercises);
                }

                selectedExercises.add(allExercises.get(index));
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

    private void addExercises(List<Exercise> routineExercises, Set<Integer> usedExercises, String muscleGroup, String level, String goal, int amount) {

        List<Exercise> exercises = exerciseRepo.getExercisesByMuscleGroup(muscleGroup, level, goal);
        if (exercises.isEmpty()) {
            return;
        }

        Collections.shuffle(exercises);
        int added = 0;

        for (Exercise exercise : exercises) {

            if (!usedExercises.contains(exercise.getId())) {

                routineExercises.add(exercise);
                usedExercises.add(exercise.getId());
                added++;

                if (added >= amount) {
                    return;
                }
            }
        }

        int index = 0;
        while (added < amount) {

            if (index >= exercises.size()) {
                index = 0;
                Collections.shuffle(exercises);
            }

            routineExercises.add(exercises.get(index));
            added++;
            index++;
        }
    }
}
