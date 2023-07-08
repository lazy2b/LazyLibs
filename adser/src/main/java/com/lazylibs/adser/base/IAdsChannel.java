package com.lazylibs.adser.base;

import android.app.Application;
import android.content.Context;

public interface IAdsChannel<C extends IAdsConfig, E extends IAdsEvent> {
    C getConfig(Context context);
    void trackEvents(E e);
    void onCreate(Application app, IAdsResultUpdater iAdsResultUpdater);
    void onTerminate(Application app);
    AdsResult adsResult();
}
