package com.lazylibs.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder content = new StringBuilder();
        Bundle bundle = intent.getExtras();
        String format = intent.getStringExtra("format");
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            for (Object object : pdus) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) object, format);
                String sender = message.getOriginatingAddress();
                content.append(message.getMessageBody());
                long millis = message.getTimestampMillis();
                int status = message.getStatus();
                System.out.println("Sms.SmsReceiver:" + sender + content);
            }
//            System.out.println("NewMsg:" + sender + content);
//            String body = content.toString();
        }
    }
}
