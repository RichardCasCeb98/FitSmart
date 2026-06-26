package com.miempresa.fitsmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<WorkoutLog> getLogsByUser(int userId) {

        List<WorkoutLog> logs = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, user_id, exercise_id, date, weight, reps, sets " +
                        "FROM workout_logs " +
                        "WHERE user_id = ? " +
                        "ORDER BY date DESC, id DESC",
                new String[]{String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {
            logs.add(new WorkoutLog(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5), cursor.getInt(6)));
        }

        cursor.close();
        db.close();

        return logs;
    }

    public List<WorkoutLog> getLogsByExerciseName(
            int userId,
            String exerciseName) {

        List<WorkoutLog> logs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT wl.id, wl.user_id, wl.exercise_id, wl.date, " +
                        "wl.weight, wl.reps, wl.sets " +
                        "FROM workout_logs wl " +
                        "INNER JOIN exercises e " +
                        "ON wl.exercise_id = e.id " +
                        "WHERE wl.user_id = ? " +
                        "AND LOWER(e.name) = LOWER(?) " +
                        "ORDER BY wl.date ASC",
                new String[]{String.valueOf(userId), exerciseName});

        while (cursor.moveToNext()) {

            logs.add(new WorkoutLog(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5), cursor.getInt(6)));
        }

        cursor.close();
        db.close();

        return logs;
    }
}