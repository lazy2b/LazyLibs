package com.lazylibs.sms;

public class SmsContent {
    public SmsContent() {
    }

    public SmsContent(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    public String sender;
    public String body;
}
