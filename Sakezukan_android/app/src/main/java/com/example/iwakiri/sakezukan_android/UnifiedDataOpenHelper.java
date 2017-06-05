package com.example.iwakiri.sakezukan_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iwakiri on 2017/04/14.
 */

public class UnifiedDataOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "android_sakezukan";
    //    public static final int DB_VERSION = 7;
    public static final int DB_VERSION = 7;

    public static final String CREATE_TABLE_USER_INFO =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_INFO + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_NAME + " text" +
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
                    "," + UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + " integer)";

    public static final String CREATE_TABLE_USER_SAKE =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_SAKE + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BRAND + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT + " real" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_CATEGORY + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + " integer)";

    public static final String CREATE_TABLE_USER_RECORD =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_RECORD + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE + " date" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE + " date" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_REVIEW + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID + " integer)";

    public static final String CREATE_TABLE_INFORMATION =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_INFORMATION + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY + " text)";

    public static final String CREATE_TABLE_HELP_CATEGORY =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_BODY + " text)";

    public static final String CREATE_TABLE_HELP_CONTENT =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_ID + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_TITLE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY + " text)";

    public static final String DROP_TABLE_USER_INFO =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_INFO;

    public static final String DROP_TABLE_SAKE =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_SAKE;

    public static final String DROP_TABLE_USER_SAKE =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;

    public static final String DROP_TABLE_USER_RECORD =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_USER_RECORD;

    public static final String DROP_TABLE_INFORMATION =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_INFORMATION;

    public static final String DROP_TABLE_HELP_CATEGORY =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;

    public static final String DROP_TABLE_HELP_CONTENT =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT;

    public static final String INIT_TABLE_USER_INFO =
            "insert into user_info (user_name,license_name,license_number) values" +
                    "('ユーザーネーム','日本酒資格名','1234567')";

    public static final String INIT_TABLE_SAKE =
            "insert into sake (brand,brewery_name,brewery_address,lower_alcohol_content,upper_alcohol_content,category,master_sake_id) values" +
                    "('あいうえお','田中酒造','東京','12.3','13.4','純米大吟醸','1')," +
                    "('あいうえ','田中酒造','大阪','14.5','15.6','純米酒','2')," +
                    "('さしすせそ','田中酒造','北海道','10.1','11.2','大吟醸','3')," +
                    "('たちつてと','山本酒造','沖縄','9.8','10.9','大吟醸','4')";

    public static final String INIT_TABLE_USER_SAKE =
            "insert into user_sake (_id,brand,brewery_name,brewery_address,lower_alcohol_content,upper_alcohol_content,category,master_sake_id) values" +
                    "('5','なにぬねの','田中酒造','東京','12.3','13.4','純米大吟醸','0')," +
                    "('6','あ','佐藤酒造','大阪','14.5','15.6','純米酒','0')," +
                    "('7','まみむめも','山本酒造','北海道','10.1','11.2','大吟醸','0')," +
                    "('8','あいう','高橋酒造','沖縄','9.8','10.9','大吟醸','0')";

    public static final String INIT_TABLE_USER_RECORD =
            "insert into user_record (found_date,tasted_date,total_grade,flavor_grade,taste_grade,user_record_image,review,master_sake_id,user_sake_id) values" +
                    "('2017-03-22',null,null,null,null,null,null,'1','0')," +
                    "('2016-08-26','2016-10-12','null','2','0',null,'さしすせその試飲記録','3','0')," +
                    "('2014-03-21','2016-04-17','4','3','4',null,'たちつてとの試飲記録','4','0')," +
                    "('2017-04-19',null,null,null,null,null,null,'0','5')," +
                    "('2017-04-28',null,null,null,null,null,null,'0','6')," +
                    "('2017-05-04',null,null,null,null,null,null,'0','7')," +
                    "('2017-05-11','2017-05-13','2','3','4',null,'あいうの試飲記録','0','8')";

    public static final String INIT_TABLE_INFORMATION =
            "insert into information (information_title,information_body) values" +
                    "('インフォメーションタイトル1','インフォメーションテキスト1')," +
                    "('インフォメーションタイトル2','インフォメーションテキスト2')";

    public static final String INIT_TABLE_HELP_CATEGORY =
            "insert into help_category (help_category_body) values" +
                    "('ヘルプカテゴリ1')," +
                    "('ヘルプカテゴリ2')," +
                    "('ヘルプカテゴリ3')";

    public static final String INIT_TABLE_HELP_CONTENT =
            "insert into help_content (help_category_id,help_title,help_body) values" +
                    "('3','ヘルプタイトル1','ヘルプテキスト1')," +
                    "('1','ヘルプタイトル2','ヘルプテキスト2')," +
                    "('2','ヘルプタイトル3','ヘルプテキスト3')," +
                    "('1','ヘルプタイトル4','ヘルプテキスト4')," +
                    "('3','ヘルプタイトル5','ヘルプテキスト5')," +
                    "('2','ヘルプタイトル6','ヘルプテキスト6')";

    public UnifiedDataOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_INFO);
        db.execSQL(CREATE_TABLE_SAKE);
        db.execSQL(CREATE_TABLE_USER_SAKE);
        db.execSQL(CREATE_TABLE_USER_RECORD);
        db.execSQL(CREATE_TABLE_INFORMATION);
        db.execSQL(CREATE_TABLE_HELP_CATEGORY);
        db.execSQL(CREATE_TABLE_HELP_CONTENT);
//        db.execSQL(INIT_TABLE_USER_INFO);
//        db.execSQL(INIT_TABLE_SAKE);
//        db.execSQL(INIT_TABLE_USER_SAKE);
//        db.execSQL(INIT_TABLE_USER_RECORD);
//        db.execSQL(INIT_TABLE_INFORMATION);
//        db.execSQL(INIT_TABLE_HELP_CATEGORY);
//        db.execSQL(INIT_TABLE_HELP_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL(DROP_TABLE_USER_INFO);
            db.execSQL(DROP_TABLE_SAKE);
            db.execSQL(DROP_TABLE_USER_SAKE);
            db.execSQL(DROP_TABLE_USER_RECORD);
            db.execSQL(DROP_TABLE_INFORMATION);
            db.execSQL(DROP_TABLE_HELP_CATEGORY);
            db.execSQL(DROP_TABLE_HELP_CONTENT);

            onCreate(db);
        }
    }

}