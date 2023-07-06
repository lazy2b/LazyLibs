package com.lazylibs.utils;

import android.text.TextUtils;
import android.util.Log;

public final class Logger {
    static class Simple implements ILogger {
        private final String tag;
        private final boolean debuggable;

        public Simple(String defaultTag, boolean debuggable) {
            this.tag = TextUtils.isEmpty(defaultTag) ? "" : defaultTag;
            this.debuggable = debuggable;
        }

        @Override
        public void e(String tag, String msg) {
            Log.e(tag, msg);
        }

        @Override
        public void i(String tag, String msg) {
            Log.i(tag, msg);
        }

        @Override
        public void d(String tag, String msg) {
            if (debuggable) Log.d(tag, msg);
        }

        @Override
        public void p(String tag, String msg) {
            System.out.println(((tag == null || tag.length() == 0) ? msg : String.format("%s:%s", tag, msg)));
        }

        @Override
        public void w(String tag, String msg) {
            Log.w(tag, msg);
        }

        @Override
        public void e(String msg) {
            e(tag, msg);
        }

        @Override
        public void i(String msg) {
            i(tag, msg);
        }

        @Override
        public void d(String msg) {
            d(tag, msg);
        }

        @Override
        public void p(String msg) {
            p(tag, msg);
        }

        @Override
        public void w(String msg) {
            w(msg);
        }
    }

    private static ILogger _logger = new Simple("Simple", false);

    public static void onCreate(ILogger logger) {
        _logger = logger;
    }

    public static void e(String tag, String msg) {
        _logger.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        _logger.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        _logger.d(tag, msg);
    }

    public static void p(String tag, String msg) {
        _logger.p(tag, msg);
    }

    public static void w(String tag, String msg) {
        _logger.w(tag, msg);
    }

    public static void e(String msg) {
        _logger.e(msg);
    }

    public static void i(String msg) {
        _logger.i(msg);
    }

    public static void d(String msg) {
        _logger.d(msg);
    }

    public static void p(String msg) {
        _logger.p(msg);
    }

    public static void w(String msg) {
        _logger.w(msg);
    }
}
