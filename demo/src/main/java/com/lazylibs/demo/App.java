package com.lazylibs.demo;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.lazylibs.adsenter.Enter;
import com.lazylibs.adsenter.Patos;
import com.lazylibs.adser.adjust.AdjustChannel;
import com.lazylibs.adser.adjust.IAdjustConfig;
import com.lazylibs.adser.base.AdsChannel;
import com.lazylibs.http.SimpleOkHttp;
import com.lazylibs.weber.LazyWebActivity;

public class App extends Adapts {

    @Nullable
    @Override
    protected AdsChannel adsChannel() {
        return new AdjustChannel(new IAdjustConfig.Simple("bn2v0eblh3wg", "BRL", ""));
    }

    interface ISettings{
        String getPatos();
        String getAdsToken();
        String settingsUrl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Enter.onCreate(new Enter.ISkipper() {
            @Override
            public void adsed(Activity activity, boolean isAdser) {
                if (isAdser) {
                    LazyWebActivity.start(activity.getApplicationContext(), "https://bing.com");
                } else {
                    activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                }
                activity.finish();
            }

            @Override
            public void afterEnter(Activity activity, boolean agreePatos) {
                startActivity(new Intent(activity, Patos.class));
                activity.finish();
            }

            @Override
            public String getPatos() {
                return "必须同意，不同意不给玩哦";//"必须同意，不同意不给玩哦";//"https://bing.com";
            }
        });
        SimpleOkHttp simpleOkHttp = new SimpleOkHttp() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                try {
//                    U u = JSON.parseObject(data, U.class);
//                    if (u != null) {
//                        Us.icu(u);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        simpleOkHttp.get("du");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Enter.onTerminate();
    }
}
