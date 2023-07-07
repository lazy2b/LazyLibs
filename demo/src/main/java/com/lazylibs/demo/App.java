package com.lazylibs.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazylibs.adsenter.Enter;
import com.lazylibs.adsenter.Patos;
import com.lazylibs.adser.Adser;
import com.lazylibs.adser.adjust.AdjustChannel;
import com.lazylibs.adser.adjust.IAdjustConfig;
import com.lazylibs.adser.base.AdsChannel;
import com.lazylibs.http.SimpleOkHttp;
import com.lazylibs.utils.Lazier;
import com.lazylibs.utils.Xc;
import com.lazylibs.utils.cache.Cache;
import com.lazylibs.utils.cache.ICache;
import com.lazylibs.utils.cache.sp.ISpCache;
import com.lazylibs.utils.cache.sp.ISpc;
import com.lazylibs.weber.LazyWebActivity;

public class App extends Application {


    /***
     * 配置读取地址
     */
    private String u = "";
    /***
     * 配置解析结果
     */
    private As as = null;

    void initAs(String ass) {
        if (ass != null) {
            try {
                as = JSON.parseObject(ass, As.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Cache.onCreate(new ISpCache.Factory() {
            @Override
            public Context requireContext() {
                return App.this;
            }
        });
        initAs(Cache.get("sts", ""));
        Enter.onCreate(new Enter.ISkipper() {
            @Override
            public void adsed(Activity activity, boolean ads) {
                if (as != null) {
                    if (ads) {
                        LazyWebActivity.start(activity.getApplicationContext(), as.zb());
                    } else {
                        if (!TextUtils.isEmpty(as.za())) {
                            LazyWebActivity.start(activity.getApplicationContext(), as.za());
                        } else {
                            activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                        }
                    }
                    activity.finish();
                }
            }

            @Override
            public void showPatos(Activity activity) {
                startActivity(new Intent(activity, Patos.class));
                activity.finish();
            }

            @Override
            public String getPatos() {
                return as != null ? as.zp() : "PATOS!";//"必须同意，不同意不给玩哦";//"必须同意，不同意不给玩哦";//"https://bing.com";
            }
        });
        SimpleOkHttp simpleOkHttp = new SimpleOkHttp() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                super.onFailure(exception);
                if (as != null) {
                    Adser.onCreate(App.this, new AdjustChannel(as.c.toSimple()));
                }
            }

            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                try {
                    Cache.put("sts", data);
                    initAs(data);
                    Adser.onCreate(App.this, new AdjustChannel(as.c.toSimple()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        simpleOkHttp.get("https://i.v2o.top/sts/ads.json");//Lazier.uRaw(u, Xc.ck));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Adser.onTerminate(this);
        Enter.onTerminate();
    }
}
