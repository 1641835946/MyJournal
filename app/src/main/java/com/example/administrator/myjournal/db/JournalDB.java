package com.example.administrator.myjournal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;

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

    public void saveHint(Hint hint) {
        if (hint != null) {
            ContentValues values = new ContentValues();
            values.put("tag", hint.getTag());
            values.put("definition", hint.getDefinition());
            db.insert("Definition", null, values);
        }
    }

    public void saveNote(Note note) {
        if (note != null) {
            ContentValues values = new ContentValues();
            values.put("time", note.getTime());
            values.put("tag", note.getTag());
            values.put("content", note.getContent());
            db.insert("Journal", null, values);
        }
    }

    public Hint loadHint(String tag) {
        Hint hint = new Hint();
        Cursor cursor = db.query("Definition", null, "tag = ?", new String[] {tag}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                hint.setDefinition(cursor.getString(cursor.getColumnIndex("definition")));
                hint.setTag(cursor.getString(cursor.getColumnIndex("tag")));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return hint;//null first
    }

    public Note loadNote(long time) {
        Cursor cursor = db.query("Journal", null, "time = ?", new String[] {String.valueOf(time)}, null, null, null);
        Note note = new Note();
        if (cursor.moveToFirst()) {
            do{
                note.setTime(time);
                note.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return note;//null
    }

    public List<Note> loadNote() {
        List<Note> list = new ArrayList<Note>();
        Cursor cursor = db.query("Journal", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                note.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                list.add(note);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<Hint> loadHint() {
        List<Hint> list = new ArrayList<Hint>();
        Cursor cursor = db.query("Definition", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Hint hint = new Hint();
                hint.setDefinition(cursor.getString(cursor.getColumnIndex("definition")));
                hint.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                list.add(hint);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;//null first
    }

}
