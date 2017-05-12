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
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED + " integer" +
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
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + " integer)";

    public static final String CREATE_TABLE_USER_RECORDS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND + " date" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED + " date" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_REVIEW + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID + " integer)";

    public static final String CREATE_TABLE_INFORMATIONS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE + " text" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY + " text)";

    public static final String CREATE_TABLE_HELP_CATEGORY =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY + " text)";

    public static final String CREATE_TABLE_HELP_CONTENTS =
            "create table " + UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENTS + " (" +
                    UnifiedDataColumns.DataColumns._ID + " integer primary key autoincrement" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_ID + " integer" +
                    "," + UnifiedDataColumns.DataColumns.COLUMN_HELP_TITLE + " text" +
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

    public static final String DROP_TABLE_HELP_CATEGORY =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;

    public static final String DROP_TABLE_HELP_CONTENTS =
            "drop table if exists " + UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENTS;

    public static final String INIT_TABLE_USER_DATA =
            "insert into user_data (user_name,license_name,license_number) values" +
                    "('ユーザーネーム','日本酒資格名','1234567')";

    public static final String INIT_TABLE_SAKE =
            "insert into sake (brand,brewery_name,brewery_address,lower_alcohol_content,upper_alcohol_content,category,has_found,has_tasted,master_sake_id) values" +
                    "('あいうえお','田中酒造','東京','12.3','13.4','純米大吟醸','0','0','1')," +
                    "('かきくけこ','佐藤酒造','大阪','14.5','15.6','純米酒','1','0','2')," +
                    "('さしすせそ','山本酒造','北海道','10.1','11.2','大吟醸','1','1','3')," +
                    "('たちつてと','高橋酒造','沖縄','9.8','10.9','大吟醸','1','1','4')";

    public static final String INIT_TABLE_USER_SAKE =
            "insert into user_sake (brand,brewery_name,brewery_address,lower_alcohol_content,upper_alcohol_content,category,has_found,has_tasted,master_sake_id) values" +
                    "('なにぬねの','田中酒造','東京','12.3','13.4','純米大吟醸','1','0','0')," +
                    "('はひふへほ','佐藤酒造','大阪','14.5','15.6','純米酒','1','0','0')," +
                    "('まみむめも','山本酒造','北海道','10.1','11.2','大吟醸','1','0','0')," +
                    "('らりるれろ','高橋酒造','沖縄','9.8','10.9','大吟醸','1','0','0')";

    public static final String INIT_TABLE_USER_RECORDS =
            "insert into user_records (date_found,date_tasted,total_grade,flavor_grade,taste_grade,user_records_image,review,master_sake_id,user_sake_id) values" +
                    "('2017-03-22',null,null,null,null,null,null,'1','0')," +
                    "('2016-08-26','2016-10-12','3','2','3',null,'さしすせその試飲記録','3','0')," +
                    "('2014-03-21','2016-04-17','4','3','4',null,'たちつてとの試飲記録','4','0')," +
                    "('2017-04-19',null,null,null,null,null,null,'0','1')," +
                    "('2017-04-28',null,null,null,null,null,null,'0','2')," +
                    "('2017-05-04',null,null,null,null,null,null,'0','3')," +
                    "('2017-05-11',null,null,null,null,null,null,'0','4')";

    public static final String INIT_TABLE_INFORMATIONS =
            "insert into informations (information_title,information_body) values" +
                    "('インフォメーションタイトル1','インフォメーションテキスト1')," +
                    "('インフォメーションタイトル2','インフォメーションテキスト2')";

    public static final String INIT_TABLE_HELP_CATEGORY =
            "insert into help_category (help_category) values" +
                    "('ヘルプカテゴリ1')," +
                    "('ヘルプカテゴリ2')," +
                    "('ヘルプカテゴリ3')";

    public static final String INIT_TABLE_HELP_CONTENTS =
            "insert into help_contents (help_category_id,help_title,help_body) values" +
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
        db.execSQL(CREATE_TABLE_USER_DATA);
        db.execSQL(CREATE_TABLE_SAKE);
        db.execSQL(CREATE_TABLE_USER_SAKE);
        db.execSQL(CREATE_TABLE_USER_RECORDS);
        db.execSQL(CREATE_TABLE_INFORMATIONS);
        db.execSQL(CREATE_TABLE_HELP_CATEGORY);
        db.execSQL(CREATE_TABLE_HELP_CONTENTS);
        db.execSQL(INIT_TABLE_USER_DATA);
        db.execSQL(INIT_TABLE_SAKE);
        db.execSQL(INIT_TABLE_USER_SAKE);
        db.execSQL(INIT_TABLE_USER_RECORDS);
        db.execSQL(INIT_TABLE_INFORMATIONS);
        db.execSQL(INIT_TABLE_HELP_CATEGORY);
        db.execSQL(INIT_TABLE_HELP_CONTENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER_DATA);
        db.execSQL(DROP_TABLE_SAKE);
        db.execSQL(DROP_TABLE_USER_SAKE);
        db.execSQL(DROP_TABLE_USER_RECORDS);
        db.execSQL(DROP_TABLE_INFORMATIONS);
        db.execSQL(DROP_TABLE_HELP_CATEGORY);
        db.execSQL(DROP_TABLE_HELP_CONTENTS);
    }

}