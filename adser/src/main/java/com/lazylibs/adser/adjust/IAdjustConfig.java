package com.lazylibs.adser.adjust;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.lazylibs.adser.base.IAdsConfig;

import java.util.HashMap;
import java.util.Map;

public interface IAdjustConfig extends IAdsConfig {
    String getAppToken();

    String getCurrency();

    Map<String, String> getEvents();

    static HashMap<String, String> toEvents(@Nullable String events) {
        try {
            if (!TextUtils.isEmpty(events)) {
                events = events.trim();
//                if (events.startsWith("{")) {
//                    return JSON.parseObject(events, HashMap.class);
//                } else {
                events = events.replace("\n", " ").trim().replace("  ", " ").replace("   ", " ");
                String[] arr = events.split(" ");
                HashMap<String, String> map = new HashMap<>();
                if (arr.length % 2 == 0) {
                    for (int i = 0; i < arr.length; i += 2) {
                        map.put(arr[i], arr[i + 1]);
                    }
                }
                return map;
//                }
            }
        } catch (Exception ignore) {
        }
        return new HashMap<>();
    }

    class Simple implements IAdjustConfig {
        public Simple() {
        }

        public Simple(String appToken) {
            this.appToken = appToken;
        }

        public Simple(String appToken, String currency, @Nullable String events) {
            this(appToken, currency, toEvents(events));
        }

        public Simple(String appToken, String currency, @Nullable Map<String, String> events) {
            this.appToken = appToken;
            this.currency = currency;
            this.events = events == null ? new HashMap<>() : events;
        }

        public String appToken = "";
        public String currency = "";
        public Map<String, String> events = new HashMap<>();

        @Override
        public String getAppToken() {
            return appToken;
        }

        @Override
        public String getCurrency() {
            return currency;
        }

        @Override
        public Map<String, String> getEvents() {
            return events;
        }
    }
}
