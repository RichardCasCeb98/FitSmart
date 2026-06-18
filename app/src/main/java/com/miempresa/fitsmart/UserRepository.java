package com.miempresa.fitsmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {

    private DatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean register(String username, String email, String password) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);

        return result != -1;
    }

    public int login(String email, String password) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE email=? AND password=?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }

        cursor.close();
        return -1;
    }

    public boolean saveProfile(int userId, int age, double weight, double height, String level, String goal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("age", age);
        values.put("weight", weight);
        values.put("height", height);
        values.put("level", level);
        values.put("goal", goal);

        Cursor cursor = db.rawQuery("SELECT id FROM user_profile WHERE user_id = ?", new String[]{String.valueOf(userId)});

        boolean exists = cursor.moveToFirst();
        cursor.close();

        long result;

        if(exists) {
            result = db.update("user_profile", values, "user_id = ?", new String[]{String.valueOf(userId)});

        } else {
            values.put("user_id", userId);
            result = db.insert("user_profile", null, values);
        }

        db.close();
        return result != -1;
    }

    public Profile getProfile(int userId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT age, weight, height, level, goal FROM user_profile WHERE user_id = ?", new String[]{String.valueOf(userId)});

        Profile profile = null;

        if(cursor.moveToFirst()) {

            int age = cursor.getInt(0);
            double weight = cursor.getDouble(1);
            double height = cursor.getDouble(2);
            String level = cursor.getString(3);
            String goal = cursor.getString(4);

            profile = new Profile(age, weight, height, level, goal);
        }

        cursor.close();
        db.close();

        return profile;
    }

}
