package com.lazylibs.demo;

import android.app.Application;

import com.lazylibs.adser.Adser;

public class Adapts extends Application {

    protected String AdsToken(){
        return "";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Adser.onCreate(this, AdsToken());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Adser.onTerminate(this);
    }
}
