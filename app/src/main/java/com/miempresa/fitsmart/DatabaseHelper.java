package com.miempresa.fitsmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "fitsmart.db";

    private static final int db_version = 2;

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

        onCreate(db);
    }
}