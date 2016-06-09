package com.example.administrator.myjournal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class JournalDB {
    public static final String DB_NAME = "my_journal";

    public static final int VERSION = 1;

    private static JournalDB journalDB;

    private SQLiteDatabase db;

    private JournalDB(Context context) {
        MyJournalOpenHelper dbHelper = new MyJournalOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static JournalDB getInstance(Context context) {
        if (journalDB == null) {
            journalDB = new JournalDB(context);
        }
        return journalDB;
    }

    public void saveHint(Hint hint) {//same
        if (hint != null) {
            ContentValues values = new ContentValues();
            values.put("tag", hint.getTag());
            values.put("definition", hint.getDefinition());
            db.insert("Definition", null, values);
        }
    }

    public void saveTime(long time) {
        ContentValues values = new ContentValues();
        values.put("day", time);
        if (!hasTime(time)) {
            db.insert("DayTable", null, values);
        }
    }

    public boolean hasTime(long time) {
        boolean flags = false;
        Cursor cursor = db.query("DayTable", null, "day = ?", new String[]{String.valueOf(time)} , null, null, null);
        if (cursor.moveToFirst()) {
            flags = true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return flags;
    }
    public List<Long> loadTime() {
        List<Long> timeStr = new ArrayList<Long>();
        Cursor cursor = db.query("DayTable", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                long timeLong = Long.parseLong(cursor.getString(cursor.getColumnIndex("day")));
                timeStr.add(timeLong);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return timeStr.isEmpty()?null:timeStr;
    }

    public void saveNote(Note note, boolean insert) {//修改
        if (insert) {
            //插入
            ContentValues values = new ContentValues();
            values.put("time", note.getTime());
            values.put("tag", note.getTag());
            values.put("content", note.getDefinition());
            db.insert("Journal", null, values);
        } else {
            ContentValues values = new ContentValues();
            values.put("content", note.getDefinition());
            db.update("Journal", values, "time = ? and tag = ?", new String[]{String.valueOf(note.getTime()), note.getTag()});
        }
    }

    public boolean hasDay(long time) {
    //sql，不存在怎么处理？考虑：开机（24h更新一次）就将日期添加到表中，这样需要修改updata和save
        boolean exist = false;
        Cursor cursor = db.query("DayTable", null, "day = ?", new String[] {String.valueOf(time)}, null, null, null);
        if (cursor.moveToFirst()) {
            exist = true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?true:false;
    }
//////////////////////////////////////
    public Hint loadHint(String tag) {
        boolean exist = false;
        Hint hint = new Hint();
        Cursor cursor = db.query("Definition", null, "tag = ?", new String[] {tag}, null, null, null);
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                hint.setDefinition(cursor.getString(cursor.getColumnIndex("definition")));
                hint.setTag(cursor.getString(cursor.getColumnIndex("tag")));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?hint:null;
    }

    public List<Note> loadNote(long time) {
        boolean exist = false;
        Cursor cursor = db.query("Journal", null, "time = ?", new String[] {String.valueOf(time)}, null, null, null);
        List<Note> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            exist = true;
            do{
                Note note = new Note();
                note.setTime(time);
                note.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                note.setDefinition(cursor.getString(cursor.getColumnIndex("content")));
                list.add(note);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?list:null;
    }

    public Note loadNote(long time, String tag) {
        //多个约束条件怎么写;
        boolean exist = false;
        Note note = new Note();
        Cursor cursor = db.query("Journal", null, "time = ? and tag = ?", new String[] {String.valueOf(time), tag}, null, null, null);
        if (cursor.moveToFirst()) {
            exist = true;
            do{
                LogUtil.e("journaldb", "执行了");
                note.setTime(time);
                note.setTag(tag);
                note.setDefinition(cursor.getString(cursor.getColumnIndex("content")));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?note:null;//set要保证只有不存在的才为空
    }

    public List<Note> loadNote() {
        boolean exist = false;
        List<Note> list = new ArrayList<Note>();
        Cursor cursor = db.query("Journal", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                Note note = new Note();
                note.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                note.setDefinition(cursor.getString(cursor.getColumnIndex("content")));
                note.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                list.add(note);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?list:null;
    }

    public List<String> loadHint() {
        boolean exist = false;
        List<String> list = new ArrayList<String>();
        Cursor cursor = db.query("Definition", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                String str;
                str = (cursor.getString(cursor.getColumnIndex("tag")));
                list.add(str);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?list:null;
    }

    public void deleteNote(long time, String tagName) {
        db.delete("Journal", "time = ? and tag = ?", new String[] {String.valueOf(time),tagName});
    }

    public void deleteTag(String tagName) {
        //删除标签及信息;
        db.delete("Journal", "tag = ?", new String[]{tagName});
        db.delete("Definition", "tag = ?", new String[]{tagName});
    }
    public List<Note> loadNote(String tag) {
        boolean exist = false;
        List<Note> list = new ArrayList<Note>();
        Cursor cursor = db.query("Journal", null, "tag = ?", new String[] {tag}, null, null, null);
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                Note note = new Note();
                note.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                note.setDefinition(cursor.getString(cursor.getColumnIndex("content")));
                list.add(note);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return exist?list:null;
    }
}
