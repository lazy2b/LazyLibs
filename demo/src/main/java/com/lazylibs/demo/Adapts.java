package com.lazylibs.demo;

import android.app.Application;

import androidx.annotation.Nullable;

import com.lazylibs.adser.Adser;
import com.lazylibs.adser.base.AdsChannel;

public class Adapts extends Application {

    protected @Nullable AdsChannel adsChannel() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Adser.onCreate(this, adsChannel());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Adser.onTerminate(this);
    }
}
