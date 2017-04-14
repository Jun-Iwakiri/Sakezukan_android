package com.example.iwakiri.sakezukan_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iwakiri on 2017/04/14.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "android_sakezukan.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "userinfo";

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "brand TEXT NOT NULL );");

        db.execSQL("INSERT INTO " + TABLE_NAME + " (brand), VALUES ('日本酒１');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (brand), VALUES ('日本酒２');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + " ;");
    }

}
