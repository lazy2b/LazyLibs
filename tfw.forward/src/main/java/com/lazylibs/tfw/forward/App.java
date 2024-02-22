package com.lazylibs.tfw.forward;

import android.app.Application;
import android.content.Context;

import com.lazylibs.tfw.sms.SmsHelper;
import com.lazylibs.tfw.room.RoomHelper;
import com.lazylibs.utils.Lazier;
import com.lazylibs.utils.Logger;
import com.lazylibs.utils.cache.Cache;
import com.lazylibs.utils.cache.sp.ISpCache;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Lazier.onCreate(this);
        Logger.d("App.onCreate()");
        Cache.onCreate(new ISpCache.Factory() {
            @Override
            public Context requireContext() {
                return App.this;
            }
        });
        RoomHelper.onCreate(this);
        SmsHelper.startServer(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Cache.onTerminate();
        Lazier.onTerminate();
        RoomHelper.onTerminate(this);
        SmsHelper.stopServer(this);
    }
}
