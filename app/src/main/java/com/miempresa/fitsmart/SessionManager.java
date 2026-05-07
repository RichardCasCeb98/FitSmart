package com.miempresa.fitsmart;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUser(int userId, String email) {
        editor.putInt("user_id", userId);
        editor.putString("email", email);
        editor.putBoolean("is_logged", true);
        editor.apply();
    }

    public boolean isLogged() {
        return prefs.getBoolean("is_logged", false);
    }

    public int getUserId() {
        return prefs.getInt("user_id", -1);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}