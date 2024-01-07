package com.lazylibs.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SmsReceiver extends BroadcastReceiver {

    public static void register(){
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        SmsReceiver receiver = new SmsReceiver();

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for(int i=0;i<pdus.length;i++){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                Date date = new Date(messages[i].getTimestampMillis());
                String dateFormatted = formatter.format(date);
                Log.e("SMS","SmsReceiver: "+ messages[i].getMessageBody());
            }
        }
    }
}
























