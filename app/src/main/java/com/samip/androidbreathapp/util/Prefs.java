package com.samip.androidbreathapp.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity){
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setBreaths(int breaths){
        // saving our breaths into system file
        preferences.edit().putInt("breaths",breaths).apply();
    }

    public int getBreaths(){
        return preferences.getInt("breaths",0);
    }

    public void setSessions(int sessions){
        preferences.edit().putInt("sessions",sessions).apply();
    }

    public int getSessions(){
        return preferences.getInt("sessions",0);
    }

    public void setDate(long milliseconds){
        preferences.edit().putLong("seconds",milliseconds).apply();
    }

    public String getDate(){
        long milliDate = preferences.getLong("seconds",0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliDate);

        String amOrpm = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        String time = "Last "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+" "+amOrpm;

        return time;
    }
}
