package com.lazylibs.utils.cache.sp;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.lazylibs.utils.cache.ICache;

import java.util.HashMap;
import java.util.Map;

public class ISpc {
    public static ICache<SharedPreferences> defaults() {
        return create("defaults");
    }

    public static ICache<SharedPreferences> settings() {
        return create("settings");
    }

    private static Map<String, ICache<SharedPreferences>> MAPS = new HashMap<>();
    private static ICache.ICacheFactory<SharedPreferences> _factory;

    public static ICache<SharedPreferences> create(@NonNull String id) {
        if (MAPS == null) {
            MAPS = new HashMap<>();
        }
        id = toId(id);
        if (!MAPS.containsKey(id)) {
            MAPS.put(id, _factory.create(id));
        }
        return MAPS.get(id);
    }

    private static String toId(String id) {
        return String.format("_%s", id);
    }

    public static void onCreate(ICache.ICacheFactory<SharedPreferences> factory) {
        _factory = factory;
    }

    public static void onTerminate() {
        if (MAPS != null) {
            for (ICache<SharedPreferences> iCache : MAPS.values()) {
                if (iCache != null) {
                    iCache.onTerminate();
                }
            }
            MAPS.clear();
            MAPS = null;

        }
        _factory = null;
    }
}