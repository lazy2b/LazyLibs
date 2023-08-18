package com.lazylibs.adsenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.lifecycle.Observer;

import com.lazylibs.adser.Adser;
import com.lazylibs.adser.base.AdsResult;
import com.lazylibs.utils.Logger;

public class Enter extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        FrameLayout space = new FrameLayout(this);
        space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(space);
        space.postDelayed(() -> skipper().afterEnter(this, isAgreePatos(this)), 1000);
    }

    public interface ISkipper {
        /***
         * 入口|闪屏页 倒计时结束后触发方法
         * @param activity
         * @param agreePatos 是否已经同意了隐私协议和服务条款
         */
        default void afterEnter(Activity activity, boolean agreePatos) {
            Logger.d("Enter.ISkipper.afterEnter " + agreePatos);
            if (agreePatos) {
                skipPatos(activity);
            } else {
                showPatos(activity);
            }
        }

        /**
         * 跳转或显示 隐私协议和服务条款
         * @param activity
         */
        default void showPatos(Activity activity) {
        }

        /**
         * 跳过 隐私协议和服务条款 (点击同意服务条款或者同意后下次进入自动跳过)，一般在这里先统一处理广告归因结果
         * @param activity
         */
        default void skipPatos(Activity activity) {
            Logger.d("Enter.ISkipper.skipPatos");
            Adser.CORE.get().observeForever(new Observer<AdsResult>() {
                @Override
                public void onChanged(AdsResult adsResult) {
                    Logger.d("Enter.ISkipper.skipPatos.onChanged 1 " + adsResult);
                    if (adsResult != null && adsResult.isLoaded) {
                        Logger.d("Enter.ISkipper.skipPatos.onChanged 2 " + adsResult.isAdser);
//                        quitFullScreen(activity.getWindow());
                        skipper().adsed(activity, adsResult.isAdser);
                        Adser.CORE.get().removeObserver(this);
                    }
                }
            });
        }

        /***
         * 广告归因结果后跳转
         * @param activity
         * @param isAdser 是否广告用户
         */
        default void adsed(Activity activity, boolean isAdser) {
        }

        /**
         * 获取服务条款和隐私协议连接
         * @return
         */
        default String getPatos() {
            return "";
        }
    }

    public static final String SP_KEY_IS_AGREE_PATOS = "isAgreePatos";
    private static ISkipper _skipper = null;

    public static ISkipper skipper() {
        if (_skipper == null) {
            throw new RuntimeException("Application.onCreate() -> Enter.Skipper.onCreate()!");
        }
        return _skipper;
    }

    public static void onCreate(ISkipper skipper) {
        _skipper = skipper;
    }

    public static void onTerminate() {
        _skipper = null;
    }

    public static boolean isEmpty(Context context) {
        return context == null || _skipper == null;
    }

    protected static boolean isAgreePatos(Context context) {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean(SP_KEY_IS_AGREE_PATOS, false);
    }

    protected static void setAgreePatos(Context context) {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit().putBoolean(SP_KEY_IS_AGREE_PATOS, true).apply();
    }
}