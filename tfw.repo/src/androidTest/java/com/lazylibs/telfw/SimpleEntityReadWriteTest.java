package com.lazylibs.telfw;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.lazylibs.tfw.room.AppDatabase;
import com.lazylibs.tfw.room.SmsContent;
import com.lazylibs.tfw.room.SmsDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private SmsDao smsDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        smsDao = db.smsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.clearAllTables();
        db.close();
    }

    @Test
    public void testWriteUserAndReadInList() throws Exception {
        SmsContent smsContent = new SmsContent("17520408408","测试短信");
        List<Long> ids = smsDao.insertAll(Collections.singletonList(smsContent));
        smsContent.id = Math.toIntExact(ids.get(0));
        List<SmsContent> smsList = smsDao.getAll();
        SmsContent finds = smsDao.findById(smsContent.id);
        assertThat(smsList.get(0), equalTo(smsContent));
        assertThat(finds, equalTo(smsContent));
        assertThat(smsList.get(0), equalTo(finds));
    }
}
