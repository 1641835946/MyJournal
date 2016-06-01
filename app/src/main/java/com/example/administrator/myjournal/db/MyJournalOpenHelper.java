package com.example.administrator.myjournal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MyJournalOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_JOURNAL = "create table Journal ("
            + "id integer primary key autoincrement, "
            + "time text, "
            + "tag text, "
            + "content text)";
    public static final String CREATE_DEFINITION = "create table Definition (" +
            "id integer primary key autoincrement," +
            "tag text, " +
            "definition text)";

    public MyJournalOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_JOURNAL);
        db.execSQL(CREATE_DEFINITION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
