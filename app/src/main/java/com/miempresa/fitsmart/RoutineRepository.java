package com.miempresa.fitsmart;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

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

    public RoutineEntity getRoutineByUser(int userId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, user_id, name, days_per_week FROM routines WHERE user_id = ? ORDER BY id DESC LIMIT 1", new String[]{String.valueOf(userId)});
        RoutineEntity routine = null;

        if(cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int dbUserId = cursor.getInt(1);
            String name = cursor.getString(2);
            int days = cursor.getInt(3);

            routine = new RoutineEntity(id, dbUserId, name, days);
        }

        cursor.close();
        db.close();

        return routine;
    }

    public List<Exercise> getExercisesByRoutine(int routineId) {

        List<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT e.id, e.name, e.muscle_group, e.level, e.goal, re.sets, re.reps " +
                        "FROM routine_exercises re " +
                        "INNER JOIN exercises e ON re.exercise_id = e.id " +
                        "WHERE re.routine_id = ?", new String[]{String.valueOf(routineId)});

        while(cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String muscleGroup = cursor.getString(2);
            String level = cursor.getString(3);
            String goal = cursor.getString(4);
            int sets = cursor.getInt(5);
            int reps = cursor.getInt(6);

            exercises.add(new Exercise(id, name, muscleGroup, level, goal, sets, reps));
        }

        cursor.close();
        db.close();
        return exercises;
    }

    public List<RoutineExercise> getRoutineExercises(int routineId) {

        List<RoutineExercise> exercises = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, routine_id, exercise_id, day, sets, reps " +
                        "FROM routine_exercises " +
                        "WHERE routine_id = ? " +
                        "ORDER BY id", new String[]{String.valueOf(routineId)});

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            int dbRoutineId = cursor.getInt(1);
            int exerciseId = cursor.getInt(2);
            String day = cursor.getString(3);
            int sets = cursor.getInt(4);
            int reps = cursor.getInt(5);

            exercises.add(new RoutineExercise(id, dbRoutineId, exerciseId, day, sets, reps));
        }

        cursor.close();
        db.close();

        return exercises;
    }

    public List<RoutineExercise> getExercisesByDay(int routineId, String day) {

        List<RoutineExercise> exercises = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, routine_id, exercise_id, day, sets, reps " +
                        "FROM routine_exercises " +
                        "WHERE routine_id=? AND day=?", new String[]{String.valueOf(routineId), day});

        while (cursor.moveToNext()) {
            exercises.add(new RoutineExercise(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5)));

        }

        cursor.close();
        db.close();

        return exercises;
    }
}