package com.lazylibs.tfw.sms;


import com.lazylibs.tfw.room.SmsContent;

public interface SmsReceivedHandler {
    void smsReceived(SmsContent smsContent);
}
