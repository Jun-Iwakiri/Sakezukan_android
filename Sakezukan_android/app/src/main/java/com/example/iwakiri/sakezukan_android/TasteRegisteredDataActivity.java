package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TasteRegisteredDataActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_IS_REQUESTED_EDIT = "EXTRA_IS_REQUESTED_EDIT";
    private static final int REQUEST_CODE = 1;

    private long sakeId;
    private long userRecordId;
    private long userSakeId;
    private long masterSakeId;
    Boolean isRequestedNewMaster;
    Boolean hasTasted;
    Boolean isUserSake;

    TextView textView37;
    TextView textView38;
    TextView textView39;
    TextView textView40;
    TextView textView41;
    TextView textView42;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_registered_data);

        Button finishButton = (Button) findViewById(R.id.button71);
        Button deleteButton = (Button) findViewById(R.id.button11);
        Button editButton = (Button) findViewById(R.id.button10);
        deleteButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        textView37 = (TextView) findViewById(R.id.textView37);
        textView38 = (TextView) findViewById(R.id.textView38);
        textView39 = (TextView) findViewById(R.id.textView39);
        textView40 = (TextView) findViewById(R.id.textView40);
        textView41 = (TextView) findViewById(R.id.textView41);
        textView42 = (TextView) findViewById(R.id.textView42);
        TextView textView45 = (TextView) findViewById(R.id.textView45);
        TextView textView46 = (TextView) findViewById(R.id.textView46);
        TextView textView47 = (TextView) findViewById(R.id.textView47);
        TextView textView48 = (TextView) findViewById(R.id.textView48);
        imageView = (ImageView) findViewById(R.id.imageView5);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
        masterSakeId = intent.getLongExtra(GuideActivity.EXTRA_MASTER_SAKE_ID, 0L);
        userRecordId = intent.getLongExtra(TasteActivity.EXTRA_USER_RECORDS_ID, 0L);
        userSakeId = intent.getLongExtra(TasteRegistrationActivity.EXTRA_USER_SAKE_ID, 0L);
        isRequestedNewMaster = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_REQUEST, false);
        hasTasted = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, true);
        isUserSake = intent.getBooleanExtra(FindActivity.EXTRA_IS_USER_SAKE, false);

        linearLayout2.setVisibility(View.GONE);
        if (isRequestedNewMaster) {
            linearLayout2.setVisibility(View.VISIBLE);
            if (!hasTasted) {
                linearLayout3.setVisibility(View.GONE);
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

            masterSakeId = insertedUserSakeCursor.getLong(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));

            textView48.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)));
            textView47.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME)));
            textView46.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS)));
            textView45.setText(insertedUserSakeCursor.getString(insertedUserSakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY)));
        }

        queryEditRecord();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button11:
                Intent intent = new Intent();
                Uri uri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                        userRecordId
                );
                getContentResolver().delete(
                        uri,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(userRecordId)}
                );

                intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                intent.putExtra(GuideActivity.EXTRA_MASTER_SAKE_ID, masterSakeId);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.button10:
                Intent editIntent = new Intent(this, TasteRegistrationActivity.class);
                boolean isRequestedEdit = true;
                editIntent.putExtra(FindActivity.EXTRA_IS_USER_SAKE, isUserSake);
                editIntent.putExtra(FindActivity.EXTRA_ID, sakeId);
                editIntent.putExtra(TasteActivity.EXTRA_USER_RECORDS_ID, userRecordId);
                editIntent.putExtra(EXTRA_IS_REQUESTED_EDIT, isRequestedEdit);
                setResult(RESULT_OK, editIntent);
                startActivityForResult(editIntent, REQUEST_CODE);
                break;
            case R.id.button71:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                userRecordId = data.getLongExtra(TasteActivity.EXTRA_USER_RECORDS_ID, 0L);
                queryEditRecord();
            }
        }
    }

    private void queryEditRecord() {
        Uri userRecordUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                userRecordId
        );
        String[] userRecordProjection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND,
                UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
                UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE,
                UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE,
                UnifiedDataColumns.DataColumns.COLUMN_REVIEW
        };
        Cursor userRecordCursor = getContentResolver().query(
                userRecordUri,
                userRecordProjection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(userRecordId)},
                null
        );
        userRecordCursor.moveToFirst();

        textView37.setText(userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND)));
        textView38.setText(userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED)));
        textView39.setText(userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE)));
        textView40.setText(userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE)));
        textView41.setText(userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE)));
        textView42.setText(userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW)));

        String imagePath = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE));

        if (imagePath != null) {
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);

            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;

            int size = 1;
            if (Math.max(imageWidth, imageHeight) > 640) {
                size = Math.max(imageWidth, imageHeight) / 640;
            }
            options.inSampleSize = size;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                ContentValues userRecordValues = new ContentValues();
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE, (byte[]) null);

                Uri updateUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                        userRecordId
                );
                getContentResolver().update(
                        updateUri,
                        userRecordValues,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(userRecordId)}
                );
            }
        }
    }
}
