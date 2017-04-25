package com.example.iwakiri.sakezukan_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iwakiri on 2017/04/14.
 */

public class UnifiedDataOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "android_sakezukan";
    public static final int DB_VERSION = 1;
    public static final String CREATE_TABLE_USER_DATA =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER + " integer)";

    public static final String CREATE_TABLE_SAKE =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_SAKE + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BRAND + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_CATEGORY + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID + " integer)";

    public static final String CREATE_TABLE_USER_SAKE =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_SAKE + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BRAND + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_CATEGORY + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + " integer)";

    public static final String CREATE_TABLE_USER_RECORDS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND + " date" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED + " date" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_REVIEW + " text)";

    public static final String CREATE_TABLE_INFORMATIONS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY + " text)";

    public static final String CREATE_TABLE_HELP =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_HELP + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY + " text)";

    public static final String DROP_TABLE_USER_DATA =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_DATA;

    public static final String DROP_TABLE_SAKE =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_SAKE;

    public static final String DROP_TABLE_USER_SAKE =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;

    public static final String DROP_TABLE_USER_RECORDS =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;

    public static final String DROP_TABLE_INFORMATIONS =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS;

    public static final String DROP_TABLE_HELP =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_HELP;

    public static final String INIT_TABLE_USER_DATA =
            "insert into user_data (license_name,license_number) values" +
                    "('日本酒資格名','1234567')";

    public static final String INIT_TABLE_SAKE =
            "insert into sake (brand,brewery_name,brewery_address,lower_alcohol_content,upper_alcohol_content,category,has_found,has_tasted,user_records_id) values" +
                    "('あいうえお','田中酒造','東京','12.3','13.4','純米大吟醸','0','0',null)," +
                    "('かきくけこ','佐藤酒造','大阪','14.5','15.6','純米酒','1','0',1)," +
                    "('さしすせそ','山本酒造','北海道','10.1','11.2','大吟醸','1','1','2')," +
                    "('たちつてと','高橋酒造','沖縄','9.8','10.9','大吟醸','1','1','3')";

    public static final String INIT_TABLE_USER_RECORDS =
            "insert into user_records (date_found,date_tasted,total_grade,flavor_grade,taste_grade,review) values" +
                    "('2017-03-22',null,null,null,null,null)," +
                    "('2016-08-26','2016-10-12','3','2','3','さしすせその試飲記録')," +
                    "('2014-03-21','2016-04-17','4','3','4','たちつてとの試飲記録')";

    public static final String INIT_TABLE_INFORMATIONS =
            "insert into informations (information_body) values" +
                    "('インフォメーションテキスト1')," +
                    "('インフォメーションテキスト2')";

    public static final String INIT_TABLE_HELP =
            "insert into help (help_body) values" +
                    "('ヘルプテキスト1')," +
                    "('ヘルプテキスト2')";

    public UnifiedDataOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_DATA);
        db.execSQL(CREATE_TABLE_SAKE);
        db.execSQL(CREATE_TABLE_USER_SAKE);
        db.execSQL(CREATE_TABLE_USER_RECORDS);
        db.execSQL(CREATE_TABLE_INFORMATIONS);
        db.execSQL(CREATE_TABLE_HELP);
        db.execSQL(INIT_TABLE_USER_DATA);
        db.execSQL(INIT_TABLE_SAKE);
        db.execSQL(INIT_TABLE_USER_RECORDS);
        db.execSQL(INIT_TABLE_INFORMATIONS);
        db.execSQL(INIT_TABLE_HELP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER_DATA);
        db.execSQL(DROP_TABLE_SAKE);
        db.execSQL(DROP_TABLE_USER_SAKE);
        db.execSQL(DROP_TABLE_USER_RECORDS);
        db.execSQL(DROP_TABLE_INFORMATIONS);
        db.execSQL(DROP_TABLE_HELP);
    }

}