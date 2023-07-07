package com.lazylibs.utils.cache.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lazylibs.utils.cache.ICache;

public class ISpCache implements ICache<SharedPreferences> {


    private SharedPreferences sharedPreferences;

    private ISpCache(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void put(@NonNull String key, @NonNull String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public void remove(@NonNull final String key) {
        sharedPreferences.edit().remove(key).commit();
    }

    @Override
    public SharedPreferences core() {
        return sharedPreferences;
    }

    @Override
    public void onTerminate() {
        sharedPreferences.edit().commit();
        sharedPreferences = null;
    }

    public static abstract class Factory implements ICacheFactory<SharedPreferences> {
        @Override
        public ICache<SharedPreferences> create(@Nullable String name) {
            return new ISpCache(requireContext(), TextUtils.isEmpty(name) ? "" : name);
        }
    }

}
