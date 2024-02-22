package com.lazylibs.tfw.sms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;


import com.lazylibs.tfw.forward.R;
import com.lazylibs.tfw.room.SmsContent;

import java.lang.ref.WeakReference;

public class SmsService extends Service {
    private static final int NOTIFICATION_ID = 100;
    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder;

    public SmsService() {
    }

    SmsGetObserver observer;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNotificationManager != null) mNotificationManager.cancelAll();
        if (observer != null) {
            getContentResolver().unregisterContentObserver(observer);
            observer = null;
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    void buildNotification() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "持续监听中...";
        // The PendingIntent to launch our activity if the user selects this notification
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), 0);
        if (Build.VERSION.SDK_INT >= 26) {
            //ChannelId为"1",ChannelName为"Channel1"
            NotificationChannel channel = new NotificationChannel("1", "持续监听中...", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(false);
            channel.enableLights(false);
//            channel.setSound();
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setVibrationPattern(null);
            channel.setSound(null, null);
//            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            mNotificationManager.createNotificationChannel(channel);
            mNotificationBuilder = new Notification.Builder(this, channel.getId());
        } else {
            mNotificationBuilder = new Notification.Builder(this);
            mNotificationBuilder.setVibrate(null);
            mNotificationBuilder.setSound(null);
        }
        mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);  // the status icon
        mNotificationBuilder.setTicker(text);  // the status text
        mNotificationBuilder.setWhen(System.currentTimeMillis());  // the time stamp
        mNotificationBuilder.setContentText(text);  // the contents of the entry
//        mNotificationBuilder.setContentIntent(contentIntent);  // The intent to send when the entry is clicked
        mNotificationBuilder.setContentTitle("...1"); // the label of the entry
        mNotificationBuilder.setOngoing(true);
        mNotificationBuilder.setAutoCancel(true);
    }

//    void updateNotification(int progress) {
//        if (mNotificationBuilder != null) {
//            mNotificationBuilder.setContentTitle(getString(R.string.updater_download_progress, progress) + "%"); // the label of the entry
//            mNotificationBuilder.setProgress(100, progress, false);
//            mNotificationBuilder.setVibrate(null);
//            mNotificationBuilder.setSound(null);
//            mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.getNotification());
//        }
//    }

    void cancelNotification() {
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            startSmsObserver(smsReceivedHandler);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();
        startForeground(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    SmsReceivedHandler smsReceivedHandler;

    public void startSmsObserver(SmsReceivedHandler smsReceivedHandler) {
        this.smsReceivedHandler = smsReceivedHandler;
        if (observer == null) {
            getContentResolver().registerContentObserver(Uri.parse("content://sms/inbox"), true, observer = new SmsGetObserver(null));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new SmsBinder(this);
    }

    static class SmsBinder extends Binder {
        WeakReference<SmsService> weakReferenceService;

        public SmsBinder(SmsService service) {
            weakReferenceService = new WeakReference<>(service);
        }

        SmsService getService() {
            return weakReferenceService.get();
        }
    }

    class SmsGetObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public SmsGetObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            if (uri == null) {
                return;
            }
            if (uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms")) {
                return;
            }
            Cursor cursor = getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, "date DESC");
            if (cursor != null) {
                try {
                    if (cursor.moveToNext()) {
                        String sender = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                        String content = cursor.getString(cursor.getColumnIndexOrThrow("body"));
//                    txtView.append(sender + content + "\n");
//                        Log.e("SMS", "NewMsg:" + sender + content);

                        System.out.println("NewMsg:" + sender + content);
//                        Looper.prepare();
//                        Toast.makeText(getApplicationContext(), sender + content, Toast.LENGTH_LONG).show();
//                        Looper.loop();
                        if (smsReceivedHandler != null)
                            smsReceivedHandler.smsReceived(new SmsContent(sender, content));
//                        forward(sender, content);
                    }
                } finally {
                    cursor.close();
                }
            }
        }

        @SuppressLint("UnlocalizedSms")
        private void forward(String sender, String content) {
//            if (!content.contains("=>") && sender.equals("14789201176") && content.contains("【中国移动】") && content.contains("验证码")) {
//                // when message sent and when delivered, or set to null.
//                PendingIntent sentIntent = null, deliveryIntent = null;
//                System.out.println("ForwardMsg:" + sender + content);
//                // Use SmsManager.
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("17520408408", "15920505602", sender + "=>" + content, sentIntent, deliveryIntent);
//            }
        }
    }
}
