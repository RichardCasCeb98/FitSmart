package com.miempresa.fitsmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "fitsmart.db";

    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
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
                        "user_id INTEGER," +
                        "age INTEGER," +
                        "weight REAL," +
                        "height REAL," +
                        "level TEXT," +
                        "goal TEXT" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS user_profile");
        onCreate(db);
    }
}