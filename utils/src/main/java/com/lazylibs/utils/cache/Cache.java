package com.lazylibs.utils.cache;

import android.content.SharedPreferences;

import com.lazylibs.utils.cache.sp.ISpc;


public class Cache {
    public static String cacheKey(String key) {
        return String.format("_%s", key);
    }

    public static void put(String key, String str) {
        ISpc.defaults().put(cacheKey(key), str);
    }

    public static String get(String key) {
        return get(key, "");
    }

    public static String get(String key, String defaultValue) {
        return ISpc.defaults().getString(cacheKey(key), defaultValue);
    }

    public static void remove(String key) {
        ISpc.defaults().remove(cacheKey(key));
    }

    public static void onCreate(ICache.ICacheFactory<SharedPreferences> factory) {
        ISpc.onCreate(factory);
    }

    public static void onTerminate() {
        ISpc.onTerminate();
    }
}
