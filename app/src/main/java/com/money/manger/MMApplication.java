package com.money.manger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MMApplication extends Application {


    private static Context context;
    SharedPreferences sharedPreferences;

    public static Context getAppContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
