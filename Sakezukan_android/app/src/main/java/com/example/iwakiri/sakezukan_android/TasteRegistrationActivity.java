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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TasteRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LAST_INSERT_ROWID = "ROWID = last_insert_rowid()";
    public static final String EXTRA_USER_SAKE_ID = "EXTRA_USER_SAKE_ID";

    private long sakeId;
    private long userSakeId;
    private long userRecordId;
    Boolean hasFound;
    Boolean hasTasted;
    Boolean isRequestedNewMaster;
    Cursor cursor;
    String foundDate;
    String tastedDate;
    Integer totalGrade;
    Integer flavorGrade;
    Integer tasteGrade;
    String review;
    ContentValues userRecordsValues;
    ContentValues sakeValues;

    EditText totalGradeEdit;
    EditText flavorGradeEdit;
    EditText tasteGradeEdit;
    EditText reviewEdit;
    EditText brandEdit;
    EditText breweryNameEdit;
    EditText breweryAddressEdit;
    EditText categoryEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_registration);

        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
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
        brandEdit = (EditText) findViewById(R.id.brand_edit);
        breweryNameEdit = (EditText) findViewById(R.id.brewery_name_edit);
        breweryAddressEdit = (EditText) findViewById(R.id.brewery_address_edit);
        categoryEdit = (EditText) findViewById(R.id.category_edit);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        registrationButon.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.textView15);

        linearLayout3.setVisibility(View.GONE);
        Intent intent = getIntent();
        isRequestedNewMaster = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_REQUEST, false);
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
                    UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED,
                    UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID
            };
            cursor = getContentResolver().query(
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

            String foundStr = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
            if (hasFound) {
                if (hasTasted) {
                    textView.setText(foundStr + "の試飲記録を追加登録");
                } else {
                    textView.setText(foundStr + "の以前発見した情報の表示");
                }
            } else {
                textView.setText(foundStr + "の発見情報の表示");
            }
        } else {
            linearLayout3.setVisibility(View.VISIBLE);
            textView.setText("新規登録申請");
            hasTasted = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, false);
            if (!hasTasted) {
                linearLayout4.setVisibility(View.GONE);
            }
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
                if (!isRequestedNewMaster) {

                    totalGrade = Integer.parseInt(totalGradeEdit.getText().toString().trim());
                    flavorGrade = Integer.parseInt(flavorGradeEdit.getText().toString().trim());
                    tasteGrade = Integer.parseInt(tasteGradeEdit.getText().toString().trim());
                    review = reviewEdit.getText().toString().trim();

                    //試飲日生成
                    userRecordsValues = new ContentValues();
                    tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                    //試飲登録セット
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED, tastedDate);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
                    //条件処理必要
                    int setTrueInt = 1;
                    sakeValues = new ContentValues();
                    if (!hasFound) {
                        //ユーザ情報挿入時
                        //発見日の生成
                        foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND, foundDate);

                        //挿入
                        getContentResolver().insert(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
                        );

                        //挿入したレコードIDを取得する
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
                        Cursor insertedCursor = getContentResolver().query(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                                projection,
                                selection,
                                null,
                                null
                        );
                        insertedCursor.moveToFirst();
                        userRecordId = insertedCursor.getLong(insertedCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                        //ユーザ記録レコードIDの追加
                        sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID, userRecordId);
                        //発見フラグの更新
                        int hasFoundInt = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND));
                        if (hasFoundInt != setTrueInt) {
                            hasFoundInt = setTrueInt;
                            sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND, hasFoundInt);
                        }
                    } else {
                        //ユーザ情報更新時
                        //更新するレコードID取得
                        userRecordId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID));
                        //更新
                        Uri uri = ContentUris.withAppendedId(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                                userRecordId
                        );
                        getContentResolver().update(
                                uri,
                                userRecordsValues,
                                UnifiedDataColumns.DataColumns._ID + "=?",
                                new String[]{Long.toString(userRecordId)}
                        );
                    }

                    //試飲フラグの更新
                    int hasTastedInt = setTrueInt;
                    sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED, hasTastedInt);
                    //更新
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
                } else {
                    //日本酒データ登録時
                    //発見日生成
                    userRecordsValues = new ContentValues();
                    foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND, foundDate);
                    //試飲時追加生成
                    if (hasTasted) {
                        tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                        totalGrade = Integer.parseInt(totalGradeEdit.getText().toString().trim());
                        flavorGrade = Integer.parseInt(flavorGradeEdit.getText().toString().trim());
                        tasteGrade = Integer.parseInt(tasteGradeEdit.getText().toString().trim());
                        review = reviewEdit.getText().toString().trim();
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED, tastedDate);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
                    }
                    //ユーザ記録挿入
                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
                    );
                    //以下マスターデータ存在時と重複表現有り
                    //挿入したレコードIDを取得する
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
                    Cursor insertedUserRecordCursor = getContentResolver().query(
                            UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                            projection,
                            selection,
                            null,
                            null
                    );
                    insertedUserRecordCursor.moveToFirst();
                    userRecordId = insertedUserRecordCursor.getLong(insertedUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));

                    //ローカル日本酒データ生成用入力文字列取得
                    String brand = brandEdit.getText().toString().trim();
                    String breweryName = breweryNameEdit.getText().toString().trim();
                    String breweryAddress = breweryAddressEdit.getText().toString().trim();
                    String category = categoryEdit.getText().toString().trim();
                    //マスターID初期化
                    Integer masterSakeId = 0;

                    //データセット
                    ContentValues userSakeValues = new ContentValues();
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BRAND, brand);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME, breweryName);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS, breweryAddress);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY, category);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID, userRecordId);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);

                    //挿入
                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_USER_SAKE, userSakeValues
                    );

                    //挿入したレコードIDを取得する
                    String[] userSakeProjection = {UnifiedDataColumns.DataColumns._ID};
                    String userSakeSelection = LAST_INSERT_ROWID;
                    Cursor insertedCursor = getContentResolver().query(
                            UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                            userSakeProjection,
                            userSakeSelection,
                            null,
                            null
                    );
                    insertedCursor.moveToFirst();
                    userSakeId = insertedCursor.getLong(insertedCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                }
                Intent registrationIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
                registrationIntent.putExtra(EXTRA_USER_SAKE_ID, userSakeId);
                registrationIntent.putExtra(TasteActivity.EXTRA_USER_RECORDS_ID, userRecordId);
                registrationIntent.putExtra(TasteNoDataActivity.EXTRA_REQUEST, isRequestedNewMaster);
                registrationIntent.putExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, hasTasted);
                startActivity(registrationIntent);
                finish();
                break;
        }
    }
}
