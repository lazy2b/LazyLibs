package com.lazylibs.adser.adjust;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.LogLevel;
import com.lazylibs.adser.Adser;
import com.lazylibs.adser.base.IAdsChannel;
import com.lazylibs.adser.base.IAdsEvent;
import com.lazylibs.adser.base.AdsResult;
import com.lazylibs.adser.base.IAdsResultUpdater;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AdjustChannel implements IAdsChannel<IAdjustConfig, AdjustEvents> {

    private final IAdjustConfig configs;
    private final IAdsEvent.Converter<IAdjustConfig, AdjustEvents, AdjustEvent> converter;
    private IAdsResultUpdater adsResultUpdater;

    public AdjustChannel(IAdjustConfig configs) {
        this(configs, new AdjustEvents.Converter());
    }

    public AdjustChannel(IAdjustConfig configs, IAdsEvent.Converter<IAdjustConfig, AdjustEvents, AdjustEvent> converter) {
        this.configs = configs;
        this.converter = converter == null ? new AdjustEvents.Converter() : converter;
    }

    @Override
    public void trackEvents(AdjustEvents event) {
        assert configs != null;
        AdjustEvent adjustEvent = converter.convert(configs, event);
        if (adjustEvent != null) {
            Adjust.trackEvent(adjustEvent);
        }
    }

    @Override
    public void onCreate(Application app, IAdsResultUpdater adsResultUpdater) {
        this.adsResultUpdater = adsResultUpdater;
        assert app != null;
        assert configs != null;
        boolean debug = (app.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        AdjustConfig config = new AdjustConfig(app, configs.getAppToken(), debug ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION);
        config.setOnEventTrackingSucceededListener(adjustEventSuccess -> {
            Adser.logD("EventTrackingSucceeded:" + adjustEventSuccess.toString());
        });
        config.setOnEventTrackingFailedListener(adjustEventFailure -> {
            Adser.logD("EventTrackingFailed:" + adjustEventFailure.toString());
        });
        config.setOnSessionTrackingSucceededListener(adjustSessionSuccess -> {
            Adser.logD("SessionTrackingSucceeded:" + adjustSessionSuccess.toString());
        });
        config.setOnSessionTrackingFailedListener(adjustSessionFailure -> {
            Adser.logD("SessionTrackingFailed:" + adjustSessionFailure.toString());
        });
        config.setOnDeeplinkResponseListener(uri -> true);
        config.setOnAttributionChangedListener(attribution -> {
            Adser.logD("AdjustChannel.onAttributionChanged " + attribution);
            if (this.adsResultUpdater != null && attribution != null) {
                this.adsResultUpdater.update(new AdsResult(true, isAdser(attribution)));
            }
        });
        config.setLogLevel(debug ? LogLevel.VERBOSE : LogLevel.ERROR);
        Adjust.onCreate(config);
        register(app);
    }

    @Override
    public void onTerminate(Application app) {
        unregister(app);
    }

    @Override
    public AdsResult adsResult() {
        AdjustAttribution attribution = Adjust.getAttribution();
        Adser.logD("AdjustChannel.adsResult " + attribution);
        if (attribution != null) {
            return new AdsResult(true, isAdser(attribution));
        }
        return null;
    }

    @Override
    public IAdjustConfig getConfig(Context context) {
        return configs;
    }

    private boolean isFbInstaller(AdjustAttribution attribution) {
        String fbInstallReferrer = attribution.fbInstallReferrer;
        if (fbInstallReferrer != null && (fbInstallReferrer.trim().startsWith("{") || fbInstallReferrer.trim().startsWith("["))) {
            try {
                JSONObject fbObj = new JSONObject(attribution.fbInstallReferrer);
                Adser.logD(fbObj.toString());
                return true;
            } catch (JSONException e) {
                Adser.logD("not fb : " + e.getMessage());
            }
        }
        return false;
    }

    private boolean isAdser(@Nullable AdjustAttribution attribution) {
        if (attribution != null) {
            String networkInfo = attribution.network;
            return isFbInstaller(attribution) || (!(Objects.equals(networkInfo, "Organic") || Objects.equals(networkInfo, "Google%20Organic%20Search") || Objects.equals(networkInfo, "Google+Organic+Search")));
        }
        return false;
    }

    private Application.ActivityLifecycleCallbacks lifecycleCallbacks;

    private void register(Application app) {
        app.registerActivityLifecycleCallbacks(lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Adjust.onResume();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Adjust.onPause();
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void unregister(Application app) {
        if (lifecycleCallbacks != null) {
            app.unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
            lifecycleCallbacks = null;
        }
    }

}