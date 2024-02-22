package com.lazylibs.tfw.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = SmsContent.TAB_NAME)
public class SmsContent {
    public static final String TAB_NAME = "sms_content";
    public static final String COLUMN_ID = "id";
    @PrimaryKey(autoGenerate = true)
    public int id;
    public SmsContent() {
    }

    @ColumnInfo(name = "is_saved")
    public boolean isSaved = false;
    public String sender;
    public String body;
    @Ignore // 忽略字段示例
    public String ignoreField;

    @Ignore
    public SmsContent(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsContent that = (SmsContent) o;
        return id == that.id && isSaved == that.isSaved && Objects.equals(sender, that.sender) && Objects.equals(body, that.body) && Objects.equals(ignoreField, that.ignoreField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isSaved, sender, body, ignoreField);
    }
}

/**
 * 忽略字段示例-继承
 */
class BaseIgnore{
    public String ignoreField;
}
@Entity(ignoredColumns = "picture")
class Ignores extends BaseIgnore{
}
