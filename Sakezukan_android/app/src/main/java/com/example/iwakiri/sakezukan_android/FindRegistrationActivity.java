package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FindRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private long sakeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_registration);

        Button homeButton = (Button) findViewById(R.id.button32);
        Button guideButton = (Button) findViewById(R.id.button33);
        Button tasteButton = (Button) findViewById(R.id.button34);
        Button findButton = (Button) findViewById(R.id.button35);
        Button helpButton = (Button) findViewById(R.id.button36);
        Button goTasteButton = (Button) findViewById(R.id.button28);
        TextView textView = (TextView) findViewById(R.id.textView);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        goTasteButton.setOnClickListener(this);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
        Uri uri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                sakeId
        );
        String[] projection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_BRAND
        };
        Cursor cursor = getContentResolver().query(
                uri,
                projection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(sakeId)},
                null
        );
        cursor.moveToFirst();

        String searchedBrand = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
        textView.setText(searchedBrand + "を見つけました");

        //ユーザ記録テーブルにレコード挿入、発見日と日本酒IDを格納
        ContentValues userRecordsValues = new ContentValues();
        String foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND, foundDate);
        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, sakeId);
        long userRecordId = 0;
        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID, userRecordId);
        getContentResolver().insert(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
        );

        //挿入したユーザ記録取得
        String[] insertedProjection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND
        };
        String selection = TasteRegistrationActivity.LAST_INSERT_ROWID;
        Cursor insertedCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                insertedProjection,
                selection,
                null,
                null
        );
        insertedCursor.moveToFirst();

        //日本酒データ更新
        int setTrueInt = 1;
        Integer hasFoundInt = setTrueInt;
        ContentValues values = new ContentValues();
        values.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND, hasFoundInt);
        Uri updatedUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                sakeId
        );
        getContentResolver().update(
                updatedUri,
                values,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(sakeId)}
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button32:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button33:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button34:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button35:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button36:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button28:
                Intent goTasteNewDataIntent = new Intent(getApplicationContext(), TasteRegistrationActivity.class);
                goTasteNewDataIntent.putExtra(FindActivity.EXTRA_ID, sakeId);
                startActivity(goTasteNewDataIntent);
                break;
        }
    }
}
