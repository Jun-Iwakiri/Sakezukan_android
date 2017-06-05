package com.example.iwakiri.sakezukan_android;

import android.provider.BaseColumns;

/**
 * Created by iwakiri on 2017/04/17.
 */

public class UnifiedDataColumns {
    public UnifiedDataColumns() {
    }

    public static abstract class DataColumns implements BaseColumns {
        public static final String TABLE_USER_INFO = "user_info";
        public static final String TABLE_SAKE = "sake";
        public static final String TABLE_USER_SAKE = "user_sake";
        public static final String TABLE_USER_RECORD = "user_record";
        public static final String TABLE_INFORMATION = "information";
        public static final String TABLE_HELP_CATEGORY = "help_category";
        public static final String TABLE_HELP_CONTENT = "help_content";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_LICENSE_NAME = "license_name";
        public static final String COLUMN_LICENSE_NUMBER = "license_number";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_BREWERY_NAME = "brewery_name";
        public static final String COLUMN_BREWERY_ADDRESS = "brewery_address";
        public static final String COLUMN_LOWER_ALCOHOL_CONTENT = "lower_alcohol_content";
        public static final String COLUMN_UPPER_ALCOHOL_CONTENT = "upper_alcohol_content";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_MASTER_SAKE_ID = "master_sake_id";
        public static final String COLUMN_FOUND_DATE = "found_date";
        public static final String COLUMN_TASTED_DATE = "tasted_date";
        public static final String COLUMN_TOTAL_GRADE = "total_grade";
        public static final String COLUMN_FLAVOR_GRADE = "flavor_grade";
        public static final String COLUMN_TASTE_GRADE = "taste_grade";
        public static final String COLUMN_USER_RECORD_IMAGE = "user_record_image";
        public static final String COLUMN_REVIEW = "review";
        public static final String COLUMN_USER_SAKE_ID = "user_sake_id";
        public static final String COLUMN_INFORMATION_TITLE = "information_title";
        public static final String COLUMN_INFORMATION_BODY = "information_body";
        public static final String COLUMN_HELP_CATEGORY_BODY = "help_category_body";
        public static final String COLUMN_HELP_CATEGORY_ID = "help_category_id";
        public static final String COLUMN_HELP_TITLE = "help_title";
        public static final String COLUMN_HELP_BODY = "help_body";
    }
}

