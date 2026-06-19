package com.miempresa.fitsmart;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class RoutineRepository {

    private DatabaseHelper dbHelper;

    public RoutineRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long saveRoutine(int userId, String name, int daysPerWeek) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("user_id", userId);
        values.put("name", name);
        values.put("days_per_week", daysPerWeek);

        long routineId = db.insert("routines", null, values);

        db.close();

        return routineId;
    }

    public boolean saveRoutineExercise(long routineId, int exerciseId, String day, int sets, int reps) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("routine_id", routineId);
        values.put("exercise_id", exerciseId);
        values.put("day", day);
        values.put("sets", sets);
        values.put("reps", reps);

        long result = db.insert("routine_exercises", null, values);

        db.close();

        return result != -1;
    }
}