package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}