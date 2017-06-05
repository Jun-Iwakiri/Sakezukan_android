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

import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_ADD;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_BROWSE;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_MASTER_SAKE_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_USER_RECORD_ID;

public class GuideDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;

    private long sakeId;
    private long masterSakeId;
    private long userRecordId;
    Uri uri;
    Uri sakeUri;
    Uri userRecordUri;
    Cursor sakeCursor;
    Cursor userRecordCursor;
    private String[] sakeProjection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_BRAND,
            UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME,
            UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS,
            UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT,
            UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT,
            UnifiedDataColumns.DataColumns.COLUMN_CATEGORY,
            UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID
    };
    private String[] userRecordProjection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE,
            UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE,
            UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
            UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE,
            UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE,
            UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE,
            UnifiedDataColumns.DataColumns.COLUMN_REVIEW,
            UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID,
            UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID
    };
    private String selection = null;
    private String[] selectionArgs = null;

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
        sakeId = intent.getLongExtra(EXTRA_ID, 0L);
        masterSakeId = intent.getLongExtra(EXTRA_MASTER_SAKE_ID, 0L);

        displaySakeData();
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
                boolean isrequestedAdd = true;
                Intent goTasteIntent = new Intent(getApplicationContext(), TasteRegistrationActivity.class);
                goTasteIntent.putExtra(EXTRA_ID, sakeId);
                goTasteIntent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                goTasteIntent.putExtra(EXTRA_USER_RECORD_ID, userRecordId);
                goTasteIntent.putExtra(EXTRA_IS_REQUESTED_ADD, isrequestedAdd);
                startActivityForResult(goTasteIntent, REQUEST_CODE);
                break;
            case R.id.button9:
                boolean isRequestedBrowse = true;
                Intent goRegisterdIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
                goRegisterdIntent.putExtra(EXTRA_ID, sakeId);
                goRegisterdIntent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                goRegisterdIntent.putExtra(EXTRA_USER_RECORD_ID, userRecordId);
                goRegisterdIntent.putExtra(EXTRA_IS_REQUESTED_BROWSE, isRequestedBrowse);
                startActivityForResult(goRegisterdIntent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                sakeId = data.getLongExtra(EXTRA_ID, 0L);
                masterSakeId = data.getLongExtra(EXTRA_MASTER_SAKE_ID, 0L);
                displaySakeData();
            }
        }
    }

    private void displaySakeData() {
        if (masterSakeId > 0) {
            uri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
        } else {
            uri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
        }
        sakeUri = ContentUris.withAppendedId(
                uri,
                sakeId
        );
        selection = UnifiedDataColumns.DataColumns._ID + "=?";
        selectionArgs = new String[]{Long.toString(sakeId)};
        sakeCursor = getContentResolver().query(
                sakeUri,
                sakeProjection,
                selection,
                selectionArgs,
                null
        );
        sakeCursor.moveToFirst();

        String brand = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
        String breweryName = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));
        String breweryAddress = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS));
        String lowerAlcoholContent = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT));
        String category = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY));
        if (brand != null) {
            textView35.setText(brand);
        }
        if (breweryName != null) {
            textView34.setText(breweryName);
        }
        if (breweryAddress != null) {
            textView33.setText(breweryAddress);
        }
        if (lowerAlcoholContent != null) {
            textView32.setText(lowerAlcoholContent);
        }
        if (category != null) {
            textView31.setText(category);
        }

        if (uri == UnifiedDataContentProvider.CONTENT_URI_USER_SAKE) {
            selection = UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID + "=?";
        } else {
            selection = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + "=?";
        }
        userRecordUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                sakeId
        );

        userRecordCursor = getContentResolver().query(
                userRecordUri,
                userRecordProjection,
                selection,
                new String[]{Long.toString(sakeId)},
                null
        );
        userRecordCursor.moveToFirst();
        userRecordId = userRecordCursor.getLong(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
        String tastedDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE));
        if (tastedDate != null) {
            goTasteButton.setVisibility(View.GONE);
        } else {
            goTasteButton.setVisibility(View.VISIBLE);
        }
    }
}
