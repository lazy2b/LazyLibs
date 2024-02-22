package com.lazylibs.tfw.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SmsContent.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SmsDao smsDao();
}
