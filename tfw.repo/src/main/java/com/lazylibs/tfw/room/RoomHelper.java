package com.lazylibs.tfw.room;

import android.app.Application;

import androidx.room.Room;

public class RoomHelper {

    private static AppDatabase db;

    public static void onCreate(Application app) {
        if (db == null) {
            db = Room.databaseBuilder(app, AppDatabase.class, "msg_forward_db").build();
        }
    }

    public static SmsDao smsDao() {
        return db != null ? db.smsDao() : null;
    }

    public static void onTerminate(Application app) {
        if (db != null) {
            db.close();
            db = null;
        }
    }

}
