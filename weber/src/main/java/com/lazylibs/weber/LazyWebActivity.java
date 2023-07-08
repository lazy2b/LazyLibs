package com.lazylibs.weber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;

import java.io.Serializable;
import java.util.HashMap;

public class LazyWebActivity extends FragmentActivity {
    LazyWebFragment web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weber_lazy_borswer);
        initWebView(getIntent().getStringExtra(WEB_URL), getIntent().getSerializableExtra(WEB_BRIDGE));
    }

    protected void initWebView(String stringExtra, Serializable jsBridge) {
        HashMap<String, Object> objectHashMap = getJsBridge();
        if (jsBridge instanceof HashMap) {
            objectHashMap = (HashMap<String, Object>) jsBridge;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_web, web = LazyWebFragment.newInstance(stringExtra, objectHashMap)).commit();
    }

    protected HashMap<String, Object> getJsBridge() {
        return new HashMap<>();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (web.canGoBack(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Activity activity, String url, boolean finish) {
        start(activity, url, finish, new HashMap<>());
    }

    public static void start(Context context, String url) {
        start(context, url, new HashMap<>());
    }

    public static void start(Context context, String url, HashMap<String, Object> jsInterface) {
        Intent intent = new Intent(context, LazyWebActivity.class);
        intent.putExtra(WEB_URL, url);
        intent.putExtra(WEB_BRIDGE, jsInterface);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public static void start(Activity activity, String url, boolean finish, HashMap<String, Object> jsInterface) {
        Intent intent = new Intent(activity, LazyWebActivity.class);
        intent.putExtra(WEB_URL, url);
        intent.putExtra(WEB_BRIDGE, jsInterface);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.getApplicationContext().startActivity(intent);
        if (finish) activity.finish();
    }

    public static final String WEB_URL = "WEB.URL";
    public static final String WEB_BRIDGE = "WEB.BRIDGE";

}
