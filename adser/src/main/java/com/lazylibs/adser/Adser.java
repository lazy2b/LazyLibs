package com.lazylibs.adser;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lazylibs.adser.base.IAdsChannel;
import com.lazylibs.adser.base.IAdsConfig;
import com.lazylibs.adser.base.IAdsEvent;
import com.lazylibs.adser.base.AdsResult;
import com.lazylibs.adser.base.IAdsResultUpdater;

public enum Adser implements IAdsResultUpdater {
    CORE;

    public LiveData<AdsResult> get() {
        return _liveData;
    }

    @Override
    public void update(@Nullable AdsResult result) {
        _liveData.postValue(result);
    }

    private final MutableLiveData<AdsResult> _liveData = new LazyLiveData<>(this::fetchAdser);

    private LiveData<AdsResult> fetchAdser() {
        logD("Adser.fetchAdser " + channel);
        if (channel != null) {
            return new MutableLiveData<>(channel.adsResult());
        }
        return new MutableLiveData<>(new AdsResult(false, false));
    }

    public boolean isAdser() {
        AdsResult adsResult = get().getValue();
        return adsResult != null && adsResult.isLoaded && adsResult.isAdser;
    }

    private IAdsChannel channel;

    public static boolean isInitialized() {
        return CORE.channel != null;
    }

    public static <C extends IAdsConfig, E extends IAdsEvent> void onCreate(Application app, IAdsChannel<C, E> adsChannel) {
        logD("Adser.onCreate " + adsChannel);
        if (!isInitialized() && adsChannel != null) {
            CORE.channel = adsChannel;
            CORE.channel.onCreate(app, CORE);
        }
    }

    public static void onTerminate(Application app) {
        logD("Adser.onTerminate ");
        if (CORE.channel != null) {
            CORE.channel.onTerminate(app);
            CORE.channel = null;
        }
    }

    public static <E extends IAdsEvent> void trackEvent(E events) {
        if (CORE.channel != null) {
            CORE.channel.trackEvents(events);
        }
    }

    public static void logD(String msg) {
        Log.d("Lzlbs", msg);
    }

}
