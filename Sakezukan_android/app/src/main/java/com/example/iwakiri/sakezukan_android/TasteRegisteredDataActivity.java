package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TasteRegisteredDataActivity extends AppCompatActivity {

    private long userRecordsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_registered_data);

        TextView textView37 = (TextView) findViewById(R.id.textView37);
        TextView textView38 = (TextView) findViewById(R.id.textView38);
        TextView textView39 = (TextView) findViewById(R.id.textView39);
        TextView textView40 = (TextView) findViewById(R.id.textView40);
        TextView textView41 = (TextView) findViewById(R.id.textView41);
        TextView textView42 = (TextView) findViewById(R.id.textView42);

        Intent intent = getIntent();
        userRecordsId = intent.getLongExtra(TasteActivity.EXTRA_USER_RECORDS_ID, 0L);
        Uri uri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                userRecordsId
        );
        String[] projection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
                UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_REVIEW
        };
        Cursor cursor = getContentResolver().query(
                uri,
                projection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(userRecordsId)},
                null
        );
        cursor.moveToFirst();

        textView37.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND)));
        textView38.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED)));
        textView39.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE)));
        textView40.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE)));
        textView41.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE)));
        textView42.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW)));

        Button button = (Button) findViewById(R.id.button71);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
