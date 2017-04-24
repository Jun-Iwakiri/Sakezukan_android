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

    public static final Uri CONTENT_URI_SAKE =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_SAKE);
    public static final Uri CONTENT_URI_USER_RECORDS =
            Uri.parse("content://" + AUTHORITY + "/" + UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS);

    private static final int SAKE = 1;
    private static final int SAKE_ITEM = 2;
    private static final int USER_RECORDS = 3;
    private static final int USER_RECORDS_ITEM = 4;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_SAKE, SAKE);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_SAKE + "/#", SAKE_ITEM);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS, USER_RECORDS);
        uriMatcher.addURI(AUTHORITY, UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS + "/#", USER_RECORDS_ITEM);
    }

    SQLiteDatabase db;

    private UnifiedDataOpenHelper unifiedDataOpenHelper;

    public UnifiedDataContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != USER_RECORDS) {
            throw new IllegalArgumentException("Infalid URI:" + uri);
        }
        SQLiteDatabase db = unifiedDataOpenHelper.getWritableDatabase();
        long newId = db.insert(
                UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS,
                null,
                values
        );
        Uri newUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
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
        String tableName;
        switch (uriMatcher.match(uri)) {
            case SAKE:
            case SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
                break;
            case USER_RECORDS:
            case USER_RECORDS_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_USER_RECORDS;
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
        String tableName;
        switch (uriMatcher.match(uri)) {
            case SAKE_ITEM:
                tableName = UnifiedDataColumns.DataColumns.TABLE_SAKE;
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
