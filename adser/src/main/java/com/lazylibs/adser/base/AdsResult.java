package com.lazylibs.adser.base;

import androidx.annotation.Keep;

@Keep
public class AdsResult {
    public AdsResult(boolean isLoaded, boolean isAdser) {
        this.isLoaded = isLoaded;
        this.isAdser = isAdser;
    }

    public boolean isLoaded = false;
    public boolean isAdser = false;
}
