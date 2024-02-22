package com.lazylibs.tfw.room;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface SmsDao {
    @Query("SELECT * FROM sms_content")
    List<SmsContent> getAll();

    @Query("SELECT * FROM sms_content WHERE id IN (:ids)")
    List<SmsContent> loadAllByIds(int[] ids);

//    @Query("SELECT * FROM sms WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    SmsContent findByName(String first, String last);

    @Insert
    Long insert(SmsContent sms);

    @Insert
    List<Long> insertAll(List<SmsContent> sms);

    @Delete
    int delete(SmsContent sms);

    @Query("select * from sms_content where id = :id")
    @Nullable
    SmsContent findById(int id);
}


