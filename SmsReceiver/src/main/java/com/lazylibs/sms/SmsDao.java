package com.lazylibs.sms;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface SmsDao {
    @Query("SELECT * FROM sms")
    List<SmsContent> getAll();

    @Query("SELECT * FROM sms WHERE smid IN (:smids)")
    List<SmsContent> loadAllByIds(int[] smids);

//    @Query("SELECT * FROM sms WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    SmsContent findByName(String first, String last);

    @Insert
    void insertAll(SmsContent... sms);

    @Delete
    void delete(SmsContent sms);
}


