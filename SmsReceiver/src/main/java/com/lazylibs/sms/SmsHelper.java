package com.lazylibs.sms;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class SmsHelper implements ServiceConnection {
    private Context mContext;
    private Handler mHandler;
    private SmsService mSmsService;
    private boolean mIsInitialization = false;
    SmsReceivedHandler mSmsReceivedHandler;

    public static SmsHelper create(@NonNull Activity context, SmsReceivedHandler smsReceivedHandler) {
        return new SmsHelper(context, smsReceivedHandler).bindService();
    }

    public static void destroy(SmsHelper helper) {
        if (helper != null) {
            if (helper.unbindService()) {
                helper = null;
            }
        }
    }

    public static void stopServer(App app) {
        app.stopService(new Intent(app, SmsService.class));
    }

    public static void startServer(App app) {
        app.startService(new Intent(app, SmsService.class));
    }

    private SmsHelper(@NonNull Activity context, SmsReceivedHandler smsReceivedHandler) {
        this.mContext = context;
        this.mSmsReceivedHandler = smsReceivedHandler;
    }

    private SmsHelper bindService() {
        if (mSmsService == null && mContext != null) {
            mContext.bindService(new Intent(mContext, SmsService.class), this, Context.BIND_AUTO_CREATE);
        }
        return this;
    }

    private boolean unbindService() {
        if (!mIsInitialization) {
            return false;
        }
        if (mSmsService != null) {
            mContext.unbindService(this);
            mIsInitialization = false;
        }
        return true;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        mSmsService = ((SmsService.SmsBinder) binder).getService();
        mIsInitialization = true;
        mSmsService.startSmsObserver(mSmsReceivedHandler);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        if (mHandler != null) {
            mHandler.removeCallbacks(null);
            mHandler = null;
        }
        mSmsService = null;
        mContext = null;
    }
}
