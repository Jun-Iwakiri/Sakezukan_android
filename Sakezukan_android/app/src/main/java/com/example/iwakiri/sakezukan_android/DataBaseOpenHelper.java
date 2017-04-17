package com.example.iwakiri.sakezukan_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iwakiri on 2017/04/14.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "android_sakezukan";
    public static final int DB_VERSION = 1;
    public static final String CREATE_TABLE_USER_DATA =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER + " integer";

    public static final String CREATE_TABLE_SAKE =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_SAKE + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BRAND + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALKOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALKOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_CATEGORY + " text";

    public static final String CREATE_TABLE_USER_RECORDS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND + " datetime" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED + " datetime" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_REVIEW + " text";

    public static final String CREATE_TABLE_INFORMATIONS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY + " text";

    public static final String CREATE_TABLE_HELP =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_HELP + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY + " text";

    public static final String DROP_TABLE_USER_DATA =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA;

    public static final String DROP_TABLE_SAKE =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA;

    public static final String DROP_TABLE_USER_RECORDS =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA;

    public static final String DROP_TABLE_INFORMATIONS =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA;

    public static final String DROP_TABLE_HELP =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA;

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_DATA);
        db.execSQL(CREATE_TABLE_SAKE);
        db.execSQL(CREATE_TABLE_USER_RECORDS);
        db.execSQL(CREATE_TABLE_INFORMATIONS);
        db.execSQL(CREATE_TABLE_HELP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER_DATA);
        db.execSQL(DROP_TABLE_SAKE);
        db.execSQL(DROP_TABLE_USER_RECORDS);
        db.execSQL(DROP_TABLE_INFORMATIONS);
        db.execSQL(DROP_TABLE_HELP);
    }

}