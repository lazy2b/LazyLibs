package com.lazylibs.utils.cache;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ICache<CORE> {
    default String get(String key) {
        return getString(key);
    }

    default String getString(@NonNull final String key) {
        return getString(key, "");
    }

    String getString(@NonNull final String key, @NonNull final String defaultValue);

    void put(@NonNull final String key, @NonNull final String value);

    void remove(@NonNull final String key);

    CORE core();

    void onTerminate();

    interface ICacheFactory<C> {
        ICache<C> create(@Nullable String name);

        Context requireContext();
    }
}
