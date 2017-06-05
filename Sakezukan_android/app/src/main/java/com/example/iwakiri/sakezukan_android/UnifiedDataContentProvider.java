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

    public static final Uri CONTENT_URI_USER_INFO =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_INFO);
    public static final Uri CONTENT_URI_SAKE =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_SAKE);
    public static final Uri CONTENT_URI_USER_SAKE =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_SAKE);
    public static final Uri CONTENT_URI_USER_RECORD =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_RECORD);
    public static final Uri CONTENT_URI_INFORMATION =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_INFORMATION);
    public static final Uri CONTENT_URI_HELP_CATEGORY =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY);
    public static final Uri CONTENT_URI_HELP_CONTENT =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT);

    String tableName;

    private static final int USER_INFO = 1;
    private static final int USER_INFO_ITEM = 2;
    private static final int SAKE = 3;
    private static final int SAKE_ITEM = 4;
    private static final int USER_SAKE = 5;
    private static final int USER_SAKE_ITEM = 6;
    private static final int USER_RECORD = 7;
    private static final int USER_RECORD_ITEM = 8;
    private static final int INFORMATION = 9;
    private static final int INFOMATION_ITEM = 10;
    private static final int HELP_CATEGORY = 11;
    private static final int HELP_CATEGORY_ITEM = 12;
    private static final int HELP_CONTENT = 13;
    private static final int HELP_CONTENT_ITEM = 14;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_INFO, USER_INFO);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_INFO + "/#", USER_INFO_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_SAKE, SAKE);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_SAKE + "/#", SAKE_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_SAKE, USER_SAKE);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_SAKE + "/#", USER_SAKE_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_RECORD, USER_RECORD);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_RECORD + "/#", USER_RECORD_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_INFORMATION, INFORMATION);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_INFORMATION + "/#", INFOMATION_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY, HELP_CATEGORY);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY + "/#", HELP_CATEGORY_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT, HELP_CONTENT);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT + "/#", HELP_CONTENT_ITEM);
    }

    SQLiteDatabase db;

    private UnifiedDataOpenHelper unifiedDataOpenHelper;

    public UnifiedDataContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case USER_INFO_ITEM:
            case USER_INFO:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_INFO;
                break;
            case SAKE_ITEM:
            case SAKE:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                break;
            case USER_SAKE_ITEM:
            case USER_SAKE:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                break;
            case USER_RECORD_ITEM:
            case USER_RECORD:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORD;
                break;
            case INFOMATION_ITEM:
            case INFORMATION:
                tableName = UnifiedDataColumns.DataColumns.TABLE_INFORMATION;
                break;
            case HELP_CATEGORY_ITEM:
            case HELP_CATEGORY:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;
                break;
            case HELP_CONTENT_ITEM:
            case HELP_CONTENT:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT;
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
            case USER_INFO:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_INFO;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_USER_INFO;
                break;
            case SAKE:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
                break;
            case USER_SAKE:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
                break;
            case USER_RECORD:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORD;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_USER_RECORD;
                break;
            case INFORMATION:
                tableName = UnifiedDataColumns.DataColumns.TABLE_INFORMATION;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_INFORMATION;
                break;
            case HELP_CATEGORY:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_HELP_CATEGORY;
                break;
            case HELP_CONTENT:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT;
                contentUri = UnifiedDataContentProvider.CONTENT_URI_HELP_CONTENT;
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
            case USER_INFO:
            case USER_INFO_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_INFO;
                break;
            case SAKE:
            case SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                break;
            case USER_SAKE:
            case USER_SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                break;
            case USER_RECORD:
            case USER_RECORD_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORD;
                break;
            case INFORMATION:
            case INFOMATION_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_INFORMATION;
                break;
            case HELP_CATEGORY:
            case HELP_CATEGORY_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;
                break;
            case HELP_CONTENT:
            case HELP_CONTENT_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CONTENT;
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
            case USER_INFO_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_INFO;
                break;
            case SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                break;
            case USER_SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_SAKE;
                break;
            case USER_RECORD_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORD;
                break;
            case INFOMATION_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_INFORMATION;
                break;
            case HELP_CATEGORY_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;
                break;
            case HELP_CONTENT_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_HELP_CATEGORY;
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
