package com.miempresa.fitsmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class WorkoutLogRepository {

    private DatabaseHelper dbHelper;

    public WorkoutLogRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void saveLog(int userId, int exerciseId, String date, double weight, int reps, int sets) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_id", userId);
        values.put("exercise_id", exerciseId);
        values.put("date", date);
        values.put("weight", weight);
        values.put("reps", reps);
        values.put("sets", sets);

        db.insert("workout_logs", null, values);

        db.close();
    }
}