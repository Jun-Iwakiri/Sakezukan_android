package com.example.iwakiri.sakezukan_android;

import android.provider.BaseColumns;

/**
 * Created by iwakiri on 2017/04/17.
 */

public class UnifiedDataColumns {
    public UnifiedDataColumns() {
    }

    public static abstract class DataColumns implements BaseColumns {
        public static final String TABLE_USER_DATA = "user_data";
        public static final String TABLE_SAKE = "sake";
        public static final String TABLE_USER_RECORDS = "user_records";
        public static final String TABLE_INFORMATIONS = "informations";
        public static final String TABLE_HELP = "help";
        public static final String COLUMN_LICENSE_NAME = "license_name";
        public static final String COLUMN_LICENSE_NUMBER = "license_number;";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_BREWERY_NAME = "brewery_name";
        public static final String COLUMN_BREWERY_ADDRESS = "brewery_address";
        public static final String COLUMN_LOWER_ALKOHOL_CONTENT = "lower_alcolol_content";
        public static final String COLUMN_UPPER_ALKOHOL_CONTENT = "upper_alkohol_content";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_HAS_FOUND = "has_found";
        public static final String COLUMN_HAS_TASTED = "has_tasted";
        public static final String COLUMN_DATE_FOUND = "date_found";
        public static final String COLUMN_DATE_TASTED = "date_tasted";
        public static final String COLUMN_TOTAL_GRADE = "total_grade";
        public static final String COLUMN_FLAVOR_GRADE = "flavor_grade";
        public static final String COLUMN_TASTE_GRADE = "taste_grade";
        public static final String COLUMN_REVIEW = "review";
        public static final String COLUMN_HELP_BODY = "help_body";
        public static final String COLUMNE_INFORMATION_BODY = "information_body";
    }
}
