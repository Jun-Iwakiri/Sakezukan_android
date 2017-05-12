package com.example.iwakiri.sakezukan_android;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class UnifiedDataContentProvider extends ContentProvider {

    public static final String AUTHORITY =
            "com.example.iwakiri.sakezukan_android.UnifiedDataContentProvider";

    public static final Uri CONTENT_URI_USER_DATA =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_DATA);
    public static final Uri CONTENT_URI_SAKE =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_SAKE);
    public static final Uri CONTENT_URI_USER_SAKE =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_SAKE);
    public static final Uri CONTENT_URI_USER_RECORDS =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS);
    public static final Uri CONTENT_URI_INFORMATIONS =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS);
    public static final Uri CONTENT_URI_HELP_CATEGORY =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY);
    public static final Uri CONTENT_URI_HELP_CONTENTS =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENTS);

    String tableName;

    private static final int USER_DATA = 1;
    private static final int USER_DATA_ITEM = 2;
    private static final int SAKE = 3;
    private static final int SAKE_ITEM = 4;
    private static final int USER_SAKE = 5;
    private static final int USER_SAKE_ITEM = 6;
    private static final int USER_RECORDS = 7;
    private static final int USER_RECORDS_ITEM = 8;
    private static final int INFORMATIONS = 9;
    private static final int INFOMATIONS_ITEM = 10;
    private static final int HELP_CATEGORY = 11;
    private static final int HELP_CATEGORY_ITEM = 12;
    private static final int HELP_CONTENTS = 13;
    private static final int HELP_CONTENTS_ITEM = 14;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_DATA, USER_DATA);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_DATA + "/#", USER_DATA_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_SAKE, SAKE);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_SAKE + "/#", SAKE_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_SAKE, USER_SAKE);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_SAKE + "/#", USER_SAKE_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS, USER_RECORDS);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS + "/#", USER_RECORDS_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS, INFORMATIONS);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS + "/#", INFOMATIONS_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY, HELP_CATEGORY);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY + "/#", HELP_CATEGORY_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENTS, HELP_CONTENTS);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENTS + "/#", HELP_CONTENTS_ITEM);
    }

    SQLiteDatabase db;

    private UnifiedDataOpenHelper unifiedDataOpenHelper;

    public UnifiedDataContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        if (uriMatcher.match(uri) == USER_RECORDS_ITEM || uriMatcher.match(uri) == USER_RECORDS) {
//            tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;
//        } else if (uriMatcher.match(uri) == USER_DATA_ITEM || uriMatcher.match(uri) == USER_DATA) {
//            tableName = UnifiedDataColumns.DataColumns.TABLE_USER_DATA;
//        } else {
//            throw new IllegalArgumentException("invalid URI:" + uri);
//        }
        switch (uriMatcher.match(uri)) {
            case USER_RECORDS_ITEM:
            case USER_RECORDS:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;
                break;
            case USER_DATA_ITEM:
            case USER_DATA:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_DATA;
                break;
            default:
                throw new IllegalArgumentException("invalid URI:" + uri);
        }

        SQLiteDatabase db = unifiedDataOpenHelper.getWritableDatabase();
        int deletedCount = db.delete(
                tableName,
                selection,
                selectionArgs
        );
        getContext().getContentResolver().notifyChange(uri, null);
        return deletedCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri contentUri;
        switch (uriMatcher.match(uri)) {
            case USER_DATA:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_DATA;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_USER_DATA;
                break;
            case USER_SAKE:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
                break;
            case USER_RECORDS:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS;
                break;
            default:
                throw new IllegalArgumentException("Invalid URI:" + uri);
        }
        SQLiteDatabase db = unifiedDataOpenHelper.getWritableDatabase();
        long newId = db.insert(
                tableName,
                null,
                values
        );
        Uri newUri = ContentUris.withAppendedId(
                contentUri,
                newId
        );
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        unifiedDataOpenHelper = new UnifiedDataOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case USER_DATA:
            case USER_DATA_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_DATA;
                break;
            case SAKE:
            case SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                break;
            case USER_SAKE:
            case USER_SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                break;
            case USER_RECORDS:
            case USER_RECORDS_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;
                break;
            case INFORMATIONS:
            case INFOMATIONS_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_INFORMATIONS;
                break;
            case HELP_CATEGORY:
            case HELP_CATEGORY_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;
                break;
            case HELP_CONTENTS:
            case HELP_CONTENTS_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENTS;
                break;
            default:
                throw new IllegalArgumentException("Invalid URI:" + uri);
        }
        db = unifiedDataOpenHelper.getReadableDatabase();
        Cursor c = db.query(
                tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updatedCount;
        switch (uriMatcher.match(uri)) {
            case USER_DATA_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_DATA;
                break;
            case SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                break;
            case USER_SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                break;
            case USER_RECORDS_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;
                break;
            default:
                throw new IllegalArgumentException("invalid URI:" + uri);
        }
        SQLiteDatabase db = unifiedDataOpenHelper.getWritableDatabase();
        updatedCount = db.update(
                tableName,
                values,
                selection,
                selectionArgs
        );
        getContext().getContentResolver().notifyChange(uri, null);
        return updatedCount;
    }
}
