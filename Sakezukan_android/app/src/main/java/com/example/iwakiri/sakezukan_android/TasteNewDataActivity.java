package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TasteNewDataActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LAST_INSERT_ROWID = "ROWID = last_insert_rowid()";

    private long sakeId;
    Boolean hasFound;
    Boolean hasTasted;

    EditText totalGradeEdit;
    EditText flavorGradeEdit;
    EditText tasteGradeEdit;
    EditText reviewEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_new_data);

        Button homeButton = (Button) findViewById(R.id.button58);
        Button guideButton = (Button) findViewById(R.id.button57);
        Button tasteButton = (Button) findViewById(R.id.button56);
        Button findButton = (Button) findViewById(R.id.button55);
        Button helpButton = (Button) findViewById(R.id.button54);
        Button registrationButon = (Button) findViewById(R.id.button70);
        totalGradeEdit = (EditText) findViewById(R.id.total_grade_edit);
        flavorGradeEdit = (EditText) findViewById(R.id.flavor_grade_edit);
        tasteGradeEdit = (EditText) findViewById(R.id.taste_grade_edit);
        reviewEdit = (EditText) findViewById(R.id.review_edit);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        registrationButon.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.textView15);
        Intent intent = getIntent();
        Boolean isRequestedNewMaster = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_REQUEST, false);
        String searchedStr = intent.getStringExtra(TasteActivity.EXTRA_NO_DATA);
        if (!isRequestedNewMaster) {
            sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
            Uri uri = ContentUris.withAppendedId(
                    UnifiedDataContentProvider.CONTENT_URI_SAKE,
                    sakeId
            );
            String[] projection = {
                    UnifiedDataColumns.DataColumns._ID,
                    UnifiedDataColumns.DataColumns.COLUMN_BRAND,
                    UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
                    UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED
            };
            Cursor cursor = getContentResolver().query(
                    uri,
                    projection,
                    UnifiedDataColumns.DataColumns._ID + "=?",
                    new String[]{Long.toString(sakeId)},
                    null
            );
            cursor.moveToFirst();

            int hasFoundInt = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND));
            int hasTastedInt = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED));

            //integerで格納された値をbooleanに変換
            switch (hasFoundInt) {
                case 0:
                    hasFound = false;
                    break;
                case 1:
                    hasFound = true;
                    break;
            }
            switch (hasTastedInt) {
                case 0:
                    hasTasted = false;
                    break;
                case 1:
                    hasTasted = true;
                    break;
            }

            if (hasFound) {
                if (hasTasted) {
                    textView.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)) + "の試飲記録を追加登録");
                } else {
                    textView.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)) + "の以前発見した情報の表示");
                }
            } else {
                textView.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)) + "の発見情報の表示");
            }
        } else {
            textView.setText(searchedStr + "を新規登録申請して登録");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button58:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button57:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button56:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button55:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button54:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button70:
                Integer totalGrade = Integer.parseInt(totalGradeEdit.getText().toString().trim());
                Integer flavorGrade = Integer.parseInt(flavorGradeEdit.getText().toString().trim());
                Integer tasteGrade = Integer.parseInt(tasteGradeEdit.getText().toString().trim());
                String review = reviewEdit.getText().toString().trim();
                //条件処理必要
                String foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                String tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());

                ContentValues userRecordsValues = new ContentValues();
                userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND, foundDate);
                userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED, tastedDate);
                userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
                userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
                userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
                userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);

                getContentResolver().insert(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
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
                String selection = LAST_INSERT_ROWID;
                Cursor cursor = getContentResolver().query(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                        projection,
                        selection,
                        null,
                        null
                );
                cursor.moveToFirst();

                //日本酒テーブルに試飲記録を紐付ける処理
                long userRecordId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                //条件分岐必要
                int hasFound = 1;
                int hasTasted = 1;
                Log.v("userRecordId", Long.toString(userRecordId));
                ContentValues sakeValues = new ContentValues();
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID, userRecordId);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND, hasFound);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED, hasTasted);

                Uri uri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_SAKE,
                        sakeId
                );
                getContentResolver().update(
                        uri,
                        sakeValues,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(sakeId)}
                );

                Intent registrationIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
                registrationIntent.putExtra(TasteActivity.EXTRA_USER_RECORDS_ID, cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID)));
                startActivity(registrationIntent);
                finish();
                break;
        }
    }
}
