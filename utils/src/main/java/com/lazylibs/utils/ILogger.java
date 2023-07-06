package com.lazylibs.utils;

public interface ILogger {
    void e(String tag, String msg);

    void i(String tag, String msg);

    void d(String tag, String msg);

    void p(String tag, String msg);

    void w(String tag, String msg);

    default void e(String msg) {
        e("", msg);
    }

    default void i(String msg) {
        i("", msg);
    }

    default void d(String msg) {
        d("", msg);
    }

    default void p(String msg) {
        p("", msg);
    }

    default void w(String msg) {
        w("", msg);
    }

}