package com.lazylibs.demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import com.lazylibs.demo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    public class SmsGetObserver extends ContentObserver {

        public SmsGetObserver(Context context) {
            super(new Handler(Looper.getMainLooper()));
            this.context = context;
        }

        Context context;

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            if (uri == null) {
                return;
            }
            if (uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms")) {
                return;
            }
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, "date DESC");
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    String sender = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    txtView.append(sender + content + "\n");
                    Log.e("SMS", "NewMsg:" + sender + content);
                    Toast.makeText(context.getApplicationContext(), sender + content, Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        }
    }


    SmsGetObserver observer;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityMainBinding.inflate(getLayoutInflater()).getRoot());
        txtView = findViewById(R.id.txtView);
        if (Build.VERSION.SDK_INT >= 23) {
            int hasReadSmsPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            if (hasReadSmsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE}, 10086);
                return;
            }
        }
        Uri uri = Uri.parse("content://sms");
        observer = new SmsGetObserver(this);
        getContentResolver().registerContentObserver(uri, true, observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}