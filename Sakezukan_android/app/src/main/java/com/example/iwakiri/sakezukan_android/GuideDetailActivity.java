package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuideDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;

    private long sakeId;
    private long masterSakeId;
    private long userRecordId;
    boolean isUserSake;
    Uri uri;
    Uri userRecordUri;
    Cursor userRecordCursor;
    String selectionColumn;
    String[] projection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
            UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID,
            UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID
    };

    Button goTasteButton;
    TextView textView35;
    TextView textView34;
    TextView textView33;
    TextView textView32;
    TextView textView31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        Button homeButton = (Button) findViewById(R.id.button82);
        Button guideButton = (Button) findViewById(R.id.button81);
        Button tasteButton = (Button) findViewById(R.id.button80);
        Button findButton = (Button) findViewById(R.id.button79);
        Button helpButton = (Button) findViewById(R.id.button78);
        goTasteButton = (Button) findViewById(R.id.button83);
        Button goRegisterdButton = (Button) findViewById(R.id.button9);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        goTasteButton.setOnClickListener(this);
        goRegisterdButton.setOnClickListener(this);
        textView35 = (TextView) findViewById(R.id.brand_text);
        textView34 = (TextView) findViewById(R.id.brewery_name_text);
        textView33 = (TextView) findViewById(R.id.brewery_address_text);
        textView32 = (TextView) findViewById(R.id.alcohol_content_text);
        textView31 = (TextView) findViewById(R.id.category_text);
        Intent intent = getIntent();
        sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
        masterSakeId = intent.getLongExtra(GuideActivity.EXTRA_MASTER_SAKE_ID, 0L);

        userRecordUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                sakeId
        );
        queryDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button82:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button81:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button80:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button79:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button78:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button83:
                Intent goTasteIntent = new Intent(getApplicationContext(), TasteRegistrationActivity.class);
                goTasteIntent.putExtra(FindActivity.EXTRA_ID, sakeId);
                goTasteIntent.putExtra(FindActivity.EXTRA_IS_USER_SAKE, isUserSake);
                startActivity(goTasteIntent);
                break;
            case R.id.button9:

                //該当日本酒データIDを持っているユーザ記録を検索
                if (isUserSake) {
                    selectionColumn = UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID;
                } else {
                    selectionColumn = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID;
                }
                userRecordCursor = getContentResolver().query(
                        userRecordUri,
                        projection,
                        selectionColumn + "=?",
                        new String[]{Long.toString(sakeId)},
                        null
                );
                userRecordCursor.moveToFirst();
                userRecordId = userRecordCursor.getLong(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));


                Intent goRegisterdIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
                goRegisterdIntent.putExtra(FindActivity.EXTRA_ID, sakeId);
                goRegisterdIntent.putExtra(GuideActivity.EXTRA_MASTER_SAKE_ID, masterSakeId);
                goRegisterdIntent.putExtra(TasteRegistrationActivity.EXTRA_USER_SAKE_ID, sakeId);
                goRegisterdIntent.putExtra(TasteActivity.EXTRA_USER_RECORDS_ID, userRecordId);
                goRegisterdIntent.putExtra(FindActivity.EXTRA_IS_USER_SAKE, isUserSake);
                goRegisterdIntent.putExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, true);
                startActivityForResult(goRegisterdIntent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                sakeId = data.getLongExtra(FindActivity.EXTRA_ID, 0L);
                masterSakeId = data.getLongExtra(GuideActivity.EXTRA_MASTER_SAKE_ID, 0L);
                queryDetail();
            }
        }
    }

    private void queryDetail() {
        if (masterSakeId > 0) {
            uri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
            isUserSake = false;
        } else {
            uri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
            isUserSake = true;
        }
        Uri detailUri = ContentUris.withAppendedId(
                uri,
                sakeId
        );
        String[] detailProjection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_BRAND,
                UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME,
                UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS,
                UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT,
                UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT,
                UnifiedDataColumns.DataColumns.COLUMN_CATEGORY,
                UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
                UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED
        };
        Cursor cursor = getContentResolver().query(
                detailUri,
                detailProjection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(sakeId)},
                null
        );
        cursor.moveToFirst();

        textView35.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)));
        textView34.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME)));
        textView33.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS)));
        textView32.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT)));
        textView31.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY)));

        if (isUserSake) {
            selectionColumn = UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID;
        } else {
            selectionColumn = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID;
        }

        userRecordCursor = getContentResolver().query(
                userRecordUri,
                projection,
                selectionColumn + "=?",
                new String[]{Long.toString(sakeId)},
                null
        );
        userRecordCursor.moveToFirst();
        String tastedDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED));
        if (tastedDate != null) {
            goTasteButton.setVisibility(View.GONE);
        } else {
            goTasteButton.setVisibility(View.VISIBLE);
        }
    }
}
