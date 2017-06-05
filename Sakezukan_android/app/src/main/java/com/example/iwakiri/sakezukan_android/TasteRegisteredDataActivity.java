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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_BROWSE;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_EDIT;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_MASTER_SAKE_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_NEW_MASTER;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_USER_RECORD_ID;

public class TasteRegisteredDataActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;

    Uri sakeUri;
    Uri userRecordUri;
    Cursor sakeCursor;
    Cursor userRecordCursor;
    private long sakeId;
    private long userRecordId;
    private long masterSakeId;
    Boolean isRequestedNewMaster;
    Boolean isRequestedBrowse;
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

    LinearLayout linearLayout2;
    LinearLayout linearLayout3;

    TextView textView37;
    TextView textView38;
    TextView textView39;
    TextView textView40;
    TextView textView41;
    TextView textView42;
    TextView textView45;
    TextView textView46;
    TextView textView47;
    TextView textView48;
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
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        textView37 = (TextView) findViewById(R.id.textView37);
        textView38 = (TextView) findViewById(R.id.textView38);
        textView39 = (TextView) findViewById(R.id.textView39);
        textView40 = (TextView) findViewById(R.id.textView40);
        textView41 = (TextView) findViewById(R.id.textView41);
        textView42 = (TextView) findViewById(R.id.textView42);
        TextView textView20 = (TextView) findViewById(R.id.textView20);
        textView45 = (TextView) findViewById(R.id.textView45);
        textView46 = (TextView) findViewById(R.id.textView46);
        textView47 = (TextView) findViewById(R.id.textView47);
        textView48 = (TextView) findViewById(R.id.textView48);
        imageView = (ImageView) findViewById(R.id.imageView5);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(EXTRA_ID, 0L);
        masterSakeId = intent.getLongExtra(EXTRA_MASTER_SAKE_ID, 0L);
        userRecordId = intent.getLongExtra(EXTRA_USER_RECORD_ID, 0L);
        isRequestedNewMaster = intent.getBooleanExtra(EXTRA_IS_REQUESTED_NEW_MASTER, false);
        isRequestedBrowse = intent.getBooleanExtra(EXTRA_IS_REQUESTED_BROWSE, false);

        deleteButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
        if (isRequestedBrowse) {
            textView20.setText("試飲記録閲覧");
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
        }

        linearLayout2.setVisibility(View.GONE);

        userRecordUri = UnifiedDataContentProvider.CONTENT_URI_USER_RECORD;
        selection = UnifiedDataColumns.DataColumns._ID + "=?";
        selectionArgs = new String[]{Long.toString(userRecordId)};
        //受け取った試飲記録idからレコード問い合わせ
        userRecordCursor = getContentResolver().query(
                userRecordUri,
                userRecordProjection,
                selection,
                selectionArgs,
                null
        );
        userRecordCursor.moveToFirst();
        if (isRequestedNewMaster) {
            displaySakeData();
        }

        displayUserRecord();

    }

    private void displaySakeData() {
        linearLayout2.setVisibility(View.VISIBLE);

        String tastedDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE));
        if (tastedDate == null) {
            linearLayout3.setVisibility(View.GONE);
        }
        sakeUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
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

        masterSakeId = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));

        String brand = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
        String breweryName = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));
        String breweryAddress = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS));
        String category = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY));
        if (brand != null) {
            textView48.setText(brand);
        }
        if (breweryName != null) {
            textView47.setText(breweryName);
        }
        if (breweryAddress != null) {
            textView46.setText(breweryAddress);
        }
        if (category != null) {
            textView45.setText(category);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button11:
                //発見日以外をnullにする更新
                ContentValues userRecordValues = new ContentValues();

                //試飲登録セット

                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE);
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE);
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE);
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE);
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE);
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_REVIEW);

                userRecordUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                        userRecordId
                );
                selection = UnifiedDataColumns.DataColumns._ID + "=?";
                selectionArgs = new String[]{Long.toString(userRecordId)};
                getContentResolver().update(
                        userRecordUri,
                        userRecordValues,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(userRecordId)}
                );

                Intent intent = new Intent();
                Log.v("userRecordId", String.valueOf(userRecordId));

                intent.putExtra(EXTRA_ID, sakeId);
                intent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.button10:
                Intent editIntent = new Intent(this, TasteRegistrationActivity.class);
                boolean isRequestedEdit = true;
                editIntent.putExtra(EXTRA_ID, sakeId);
                editIntent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                editIntent.putExtra(EXTRA_USER_RECORD_ID, userRecordId);
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
                userRecordId = data.getLongExtra(EXTRA_USER_RECORD_ID, 0L);
                userRecordUri = UnifiedDataContentProvider.CONTENT_URI_USER_RECORD;
                selection = UnifiedDataColumns.DataColumns._ID + "=?";
                selectionArgs = new String[]{Long.toString(userRecordId)};
                userRecordCursor = getContentResolver().query(
                        userRecordUri,
                        userRecordProjection,
                        selection,
                        selectionArgs,
                        null
                );
                userRecordCursor.moveToFirst();
                displayUserRecord();
            }
        }
    }

    private void displayUserRecord() {

        String foundDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE));
        String tastedDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE));
        String totalGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE));
        String flavorGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE));
        String tasteGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE));
        String review = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW));
        String imagePath = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE));
        textView37.setText(foundDate);
        if (!"null".equals(tastedDate)) {
            try {
                parseDate(tastedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            textView38.setText(tastedDate);
        }
        if (totalGrade != null) {
            textView39.setText(totalGrade);
        }
        if (flavorGrade != null) {
            textView40.setText(flavorGrade);
        }
        if (tasteGrade != null) {
            textView41.setText(tasteGrade);
        }
        if (review != null) {
            textView42.setText(review);
        }
        setImage(imagePath);
    }

    static String getTimeStr(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return sdf.format(d);
    }

    static String parseDate(String datestr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(datestr);
        return getTimeStr(d);
    }

    private void setImage(String path) {
        if (path != null) {
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;

            int size = 1;
            if (Math.max(imageWidth, imageHeight) > 640) {
                size = Math.max(imageWidth, imageHeight) / 640;
            }
            options.inSampleSize = size;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, options);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                ContentValues userRecordValues = new ContentValues();
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE);

                Uri updateUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
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
