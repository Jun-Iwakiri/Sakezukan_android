package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TasteRegisteredDataActivity extends AppCompatActivity {

    private long userRecordsId;
    private long userSakeId;
    Boolean isRequestedNewMaster;
    Boolean hasTasted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_registered_data);

        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        TextView textView37 = (TextView) findViewById(R.id.textView37);
        TextView textView38 = (TextView) findViewById(R.id.textView38);
        TextView textView39 = (TextView) findViewById(R.id.textView39);
        TextView textView40 = (TextView) findViewById(R.id.textView40);
        TextView textView41 = (TextView) findViewById(R.id.textView41);
        TextView textView42 = (TextView) findViewById(R.id.textView42);
        TextView textView45 = (TextView) findViewById(R.id.textView45);
        TextView textView46 = (TextView) findViewById(R.id.textView46);
        TextView textView47 = (TextView) findViewById(R.id.textView47);
        TextView textView48 = (TextView) findViewById(R.id.textView48);

        Intent intent = getIntent();
        userRecordsId = intent.getLongExtra(TasteActivity.EXTRA_USER_RECORDS_ID, 0L);
        userSakeId = intent.getLongExtra(TasteRegistrationActivity.EXTRA_USER_SAKE_ID, 0L);
        isRequestedNewMaster = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_REQUEST, false);
        hasTasted = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, true);

        linearLayout2.setVisibility(View.GONE);
        if (isRequestedNewMaster) {
            linearLayout2.setVisibility(View.VISIBLE);
            if (!hasTasted) {
                linearLayout3.setVisibility(View.GONE);
            }
        }

        Uri insertedUserSakeUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                userSakeId
        );
        String[] insertedUserSakeProjection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_BRAND,
                UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME,
                UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS,
                UnifiedDataColumns.DataColumns.COLUMN_CATEGORY
        };
        Cursor insertedUserSakeCursor = getContentResolver().query(
                insertedUserSakeUri,
                insertedUserSakeProjection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(userSakeId)},
                null
        );
        insertedUserSakeCursor.moveToFirst();

        textView48.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)));
        textView47.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME)));
        textView46.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS)));
        textView45.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY)));

        Uri insertedUserRecordUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                userRecordsId
        );
        String[] insertedUserRecordProjection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
                UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_REVIEW
        };
        Cursor insertedUserRecordCursor = getContentResolver().query(
                insertedUserRecordUri,
                insertedUserRecordProjection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(userRecordsId)},
                null
        );
        insertedUserRecordCursor.moveToFirst();

        textView37.setText(insertedUserRecordCursor.getString(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND)));
        textView38.setText(insertedUserRecordCursor.getString(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED)));
        textView39.setText(insertedUserRecordCursor.getString(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE)));
        textView40.setText(insertedUserRecordCursor.getString(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE)));
        textView41.setText(insertedUserRecordCursor.getString(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE)));
        textView42.setText(insertedUserRecordCursor.getString(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW)));

        Button button = (Button) findViewById(R.id.button71);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
