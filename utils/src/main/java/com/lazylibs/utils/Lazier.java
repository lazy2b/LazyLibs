package com.lazylibs.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public final class Lazier {
    private static Application application = null;
    public static boolean DEBUGGABLE = false;

    public static void onCreate(Application app) {
        onCreate(app, null);
    }

    public static void onCreate(Application app, ILogger iLogger) {
        application = app;
        DEBUGGABLE = app != null && ((app.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        if (iLogger == null) {
            Logger.onCreate(new Logger.Simple("Lzlbs", DEBUGGABLE));
        }
        NetworkMonitor.startMonitor(application);
    }

    public static boolean isEmptyOrNull(@Nullable String raw) {
        return raw == null || raw.length() == 0;
    }

    public static String removeZWSP(String raw) {
        return isEmptyOrNull(raw) ? "" : raw.replaceAll("\\s", "").replace("​", "");
    }

    public static Application globalContext() {
        if (application == null) {
            throw new RuntimeException("application 尚未初始化！");
        }
        return application;
    }

    public static void onTerminate() {
        NetworkMonitor.onTerminate(application);
        application = null;
    }

    public static boolean isMain() {
        return isMain(application);
    }

    /**
     * 判断是否是主进程
     *
     * @param application 当前的Application
     * @return 是否是主进程
     */
    private static boolean isMain(Application application) {
        if (application == null) {
            return false;
        }
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        String packageName = application.getPackageName();
        return processName.equals(packageName);
    }

    public static int dp(Context context, float dpValue) {
        // 获取设备屏幕的密度比例因子
        float scale = context.getResources().getDisplayMetrics().density;
        // 将dp值转换为像素值
        return (int) (dpValue * scale + 0.5f);
    }

    public static void quitFullScreen(Window window) {
        final WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setAttributes(attrs);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
