package com.example.asus.helloworld;

import android.app.Application;
import android.content.Context;

/**
 * Created by ASUS on 2018/6/26.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
