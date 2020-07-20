package com.omidipoor.cla.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String SHARED_PREFERENCES_NAME = "user_shared_preferences";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
    }

    public UserSharedPreferences get_shared_preferences() {
        UserSharedPreferences user = new UserSharedPreferences();
        user.setFirst_time_run(sharedPreferences.getBoolean("first_time_run", true));
        user.setFirst_name(sharedPreferences.getString("first_name", ""));
        user.setLast_name(sharedPreferences.getString("last_name", ""));
        user.setImage_url(sharedPreferences.getString("image_url", ""));
        return user;
    }

    public void set_false_first_time(UserSharedPreferences user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first_time_run", user.getFirst_time_run());
        editor.apply();
    }

    public void set_image_url(UserSharedPreferences user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image_url", user.getImage_url());
        editor.apply();
    }

    public void set_name(UserSharedPreferences user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", user.getFirst_name());
        editor.putString("last_name", user.getLast_name());
        editor.apply();
    }

}
