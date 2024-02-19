package com.lazylibs.sms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SmsContent {
    @PrimaryKey
    public int smid;
    public SmsContent() {
    }

    public SmsContent(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }
    @ColumnInfo(name = "is_saved")
    public boolean isSaved = false;
    @ColumnInfo(name = "sender")
    public String sender;
    @ColumnInfo(name = "body")
    public String body;
}
