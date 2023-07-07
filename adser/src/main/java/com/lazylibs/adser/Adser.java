package com.lazylibs.adser;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lazylibs.adser.base.AdsChannel;
import com.lazylibs.adser.base.AdsConfig;
import com.lazylibs.adser.base.AdsEvent;
import com.lazylibs.adser.base.AdsResult;

public enum Adser {
    CORE;

    public LiveData<AdsResult> get() {
        return _liveData;
    }

    public void update(@Nullable AdsResult result) {
        _liveData.postValue(result);
    }

    private final MutableLiveData<AdsResult> _liveData = new LazyLiveData<>(this::fetchAdser);

    private LiveData<AdsResult> fetchAdser() {
        if(channel!=null){
            return new MutableLiveData<>(channel.adsResult());
        }
        return new MutableLiveData<>(null);
    }

    public boolean isAdser() {
        AdsResult adsResult = get().getValue();
        return adsResult != null && adsResult.isLoaded && adsResult.isAdser;
    }

    private AdsChannel channel;

    public static <C extends AdsConfig, E extends AdsEvent> void onCreate(Application app, AdsChannel<C, E> adsChannel) {
        if (adsChannel != null) {
            CORE.channel = adsChannel;
            CORE.channel.onCreate(app);
        }
    }

    public static void onTerminate(Application app) {
        if (CORE.channel != null) {
            CORE.channel.onTerminate(app);
            CORE.channel = null;
        }
    }

    public static <E extends AdsEvent> void trackEvent(E events) {
        if (CORE.channel != null) {
            CORE.channel.trackEvents(events);
        }
    }

    public static void logD(String msg) {
        Log.d("Lazy.Adser", msg);
    }

}
