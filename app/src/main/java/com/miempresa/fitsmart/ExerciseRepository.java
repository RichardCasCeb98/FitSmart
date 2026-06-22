package com.miempresa.fitsmart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {

    private DatabaseHelper dbHelper;

    public ExerciseRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Exercise> getExercises(String level, String goal) {

        List<Exercise> exercises = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE level = ? AND goal = ?", new String[]{level, goal});

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String muscleGroup = cursor.getString(2);
            String exerciseLevel = cursor.getString(3);
            String exerciseGoal = cursor.getString(4);
            int sets = cursor.getInt(5);
            int reps = cursor.getInt(6);

            Exercise exercise = new Exercise(id, name, muscleGroup, exerciseLevel, exerciseGoal, sets, reps);

            exercises.add(exercise);
        }

        cursor.close();
        db.close();

        return exercises;
    }

    public Exercise getExerciseById(int exerciseId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE id = ?", new String[]{String.valueOf(exerciseId)});
        Exercise exercise = null;

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String muscleGroup = cursor.getString(2);
            String level = cursor.getString(3);
            String goal = cursor.getString(4);
            int sets = cursor.getInt(5);
            int reps = cursor.getInt(6);

            exercise = new Exercise(id, name, muscleGroup, level, goal, sets, reps);
        }

        cursor.close();
        db.close();

        return exercise;
    }
}
