package com.lazylibs.adser.base;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

public interface AdsChannel<C extends AdsConfig, E extends AdsEvent> {
    C getConfig(Context context);
    void trackEvents(E e);
    void onCreate(Application app);
    void onTerminate(Application app);
    AdsResult adsResult();
}
