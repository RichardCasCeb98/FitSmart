package com.miempresa.fitsmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "fitsmart.db";

    private static final int db_version = 4;

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

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press inclinado','Pecho','Principiante','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Aperturas con mancuernas','Pecho','Principiante','Ganar masa muscular',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Remo con barra','Espalda','Principiante','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Jalón al pecho','Espalda','Principiante','Ganar masa muscular',4,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Prensa de piernas','Pierna','Principiante','Ganar masa muscular',4,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Peso muerto rumano','Pierna','Principiante','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press militar','Hombro','Principiante','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Elevaciones laterales','Hombro','Principiante','Ganar masa muscular',3,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Curl de bíceps','Biceps','Principiante','Ganar masa muscular',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Extensiones de tríceps','Triceps','Principiante','Ganar masa muscular',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Jumping Jacks','Cardio','Principiante','Perder grasa',4,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Mountain Climbers','Cardio','Principiante','Perder grasa',4,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Saltar a la comba','Cardio','Principiante','Perder grasa',5,30)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Plancha','Abdomen','Principiante','Perder grasa',3,45)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Abdominales','Abdomen','Principiante','Perder grasa',3,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Flexiones','Pecho','Principiante','Perder grasa',3,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Zancadas','Pierna','Principiante','Perder grasa',3,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Remo con mancuerna','Espalda','Principiante','Mantenimiento',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadillas','Pierna','Principiante','Mantenimiento',3,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Flexiones','Pecho','Principiante','Mantenimiento',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Plancha','Abdomen','Principiante','Mantenimiento',3,45)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Peso muerto','Espalda','Principiante','Fuerza',5,5)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press banca','Pecho','Principiante','Fuerza',5,5)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadilla','Pierna','Principiante','Fuerza',5,5)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press militar','Hombro','Principiante','Fuerza',5,5)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Dominadas','Espalda','Intermedio','Ganar masa muscular',4,8)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Fondos en paralelas','Pecho','Intermedio','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Hip Thrust','Pierna','Intermedio','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Curl martillo','Biceps','Intermedio','Ganar masa muscular',4,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press Arnold','Hombro','Intermedio','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Burpees','Cardio','Intermedio','Perder grasa',4,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sprint en cinta','Cardio','Intermedio','Perder grasa',10,1)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Russian Twist','Abdomen','Intermedio','Perder grasa',4,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press banca','Pecho','Intermedio','Fuerza',5,3)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Peso muerto','Espalda','Intermedio','Fuerza',5,3)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadilla','Pierna','Intermedio','Fuerza',5,3)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Dominadas lastradas','Espalda','Avanzado','Ganar masa muscular',5,6)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press banca inclinado','Pecho','Avanzado','Ganar masa muscular',5,8)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadilla búlgara','Pierna','Avanzado','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Face Pull','Hombro','Avanzado','Ganar masa muscular',4,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Peso muerto convencional','Espalda','Avanzado','Fuerza',6,2)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press banca competitivo','Pecho','Avanzado','Fuerza',6,2)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadilla profunda','Pierna','Avanzado','Fuerza',6,2)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Remo Pendlay','Espalda','Avanzado','Ganar masa muscular',5,8)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press declinado','Pecho','Avanzado','Ganar masa muscular',5,8)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Zancadas con barra','Pierna','Avanzado','Ganar masa muscular',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Elevaciones posteriores','Hombro','Avanzado','Ganar masa muscular',4,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Curl concentrado','Biceps','Avanzado','Ganar masa muscular',4,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press francés','Triceps','Avanzado','Ganar masa muscular',4,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Burpees con salto','Cardio','Avanzado','Perder grasa',5,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Battle Ropes','Cardio','Avanzado','Perder grasa',6,30)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sprint en cuesta','Cardio','Avanzado','Perder grasa',10,1)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Mountain Climbers','Cardio','Avanzado','Perder grasa',5,30)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Plancha con peso','Abdomen','Avanzado','Perder grasa',4,60)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Ab Wheel','Abdomen','Avanzado','Perder grasa',4,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Thrusters','Full Body','Avanzado','Perder grasa',5,15)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Dominadas','Espalda','Avanzado','Mantenimiento',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press banca','Pecho','Avanzado','Mantenimiento',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Sentadilla','Pierna','Avanzado','Mantenimiento',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press militar','Hombro','Avanzado','Mantenimiento',4,10)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Curl de bíceps','Biceps','Avanzado','Mantenimiento',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Fondos en paralelas','Triceps','Avanzado','Mantenimiento',3,12)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Elevaciones de piernas','Abdomen','Avanzado','Mantenimiento',3,20)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press militar estricto','Hombro','Avanzado','Fuerza',6,2)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Dominadas lastradas','Espalda','Avanzado','Fuerza',5,3)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Hip Thrust pesado','Pierna','Avanzado','Fuerza',5,3)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Press cerrado','Triceps','Avanzado','Fuerza',5,4)"
        );

        db.execSQL(
                "INSERT INTO exercises " +
                        "(name, muscle_group, level, goal, sets, reps) VALUES " +
                        "('Curl con barra Z','Biceps','Avanzado','Fuerza',5,5)"
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