package com.lazylibs.weber;

import android.webkit.WebView;

public interface IWebHandler extends IActivityGetter {
    default void doProgressed(String url) {
    }

    default void onProgressChanged(WebView view, int newProgress) {
    }

    default void onRealPageFinished(String url, boolean isReceivedError) {
    }
}
