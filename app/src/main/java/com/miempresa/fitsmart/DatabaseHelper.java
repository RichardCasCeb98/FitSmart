package com.miempresa.fitsmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "fitsmart.db";

    private static final int db_version = 3;

    public DatabaseHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT," +
                        "email TEXT UNIQUE," +
                        "password TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE user_profile (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_id INTEGER UNIQUE," +
                        "age INTEGER," +
                        "weight REAL," +
                        "height REAL," +
                        "level TEXT," +
                        "goal TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE exercises (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "muscle_group TEXT NOT NULL," +
                        "level TEXT NOT NULL," +
                        "goal TEXT NOT NULL," +
                        "sets INTEGER NOT NULL," +
                        "reps INTEGER NOT NULL" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE routines (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_id INTEGER," +
                        "name TEXT," +
                        "days_per_week INTEGER" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE routine_exercises (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "routine_id INTEGER," +
                        "exercise_id INTEGER," +
                        "day TEXT," +
                        "sets INTEGER," +
                        "reps INTEGER" +
                        ")"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press banca','Pecho','Principiante','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadilla','Pierna','Principiante','Ganar masa muscular',4,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Burpees','Cardio','Principiante','Perder grasa',3,15)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS user_profile");
        db.execSQL("DROP TABLE IF EXISTS exercises");
        db.execSQL("DROP TABLE IF EXISTS routines");
        db.execSQL("DROP TABLE IF EXISTS routine_exercises");

        onCreate(db);
    }
}