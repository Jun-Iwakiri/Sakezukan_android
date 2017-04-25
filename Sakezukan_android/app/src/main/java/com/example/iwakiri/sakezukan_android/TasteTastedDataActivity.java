package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TasteTastedDataActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean hasFound;
    Boolean hasTasted;
    EditText editText;
    String str;
    private long sakeId;
    private long userRecordsId;

    private String[] projection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_BRAND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED,
            UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID
    };
    private String selection = null;
    private String[] selectionArgs = null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_tasted_data);

        Button homeButton = (Button) findViewById(R.id.button68);
        Button guideButton = (Button) findViewById(R.id.button67);
        Button tasteButton = (Button) findViewById(R.id.button66);
        Button findButton = (Button) findViewById(R.id.button65);
        Button helpButton = (Button) findViewById(R.id.button64);
        Button searchButton = (Button) findViewById(R.id.button74);
        Button addRegistrationButton = (Button) findViewById(R.id.button75);
        TextView textView18 = (TextView) findViewById(R.id.textView18);
        TextView textView14 = (TextView) findViewById(R.id.textView14);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        addRegistrationButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edittext6);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
        Uri uri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                sakeId
        );
        cursor = getContentResolver().query(
                uri,
                projection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(sakeId)},
                null
        );
        cursor.moveToFirst();
        String displayTastedBrand = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
        textView18.setText(displayTastedBrand + "は以前飲んだことのある銘柄");

        userRecordsId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_ID));

        Uri userRecordsUri = ContentUris.withAppendedId(
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
        Cursor userRecordsCursor = getContentResolver().query(
                userRecordsUri,
                projection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(userRecordsId)},
                null
        );
        userRecordsCursor.moveToFirst();
        String displayDateTasted = userRecordsCursor.getString(userRecordsCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED));
        textView14.setText("前回試飲日:" + displayDateTasted);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button68:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button67:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button66:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button65:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button64:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button74:
                searchData();
                break;
            case R.id.button75:
                //試飲記録追加登録
                Intent intent = new Intent(getApplicationContext(), TasteRegistrationActivity.class);
                intent.putExtra(FindActivity.EXTRA_ID, cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                startActivity(intent);
                break;
        }
    }

    private void searchData() {
        str = editText.getText().toString().trim();
        if (!str.isEmpty()) {
            //マスターデータから完全一致検索
            selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " = '" + str + "'";
            cursor = getContentResolver().query(
                    UnifiedDataContentProvider.CONTENT_URI_SAKE,
                    projection,
                    selection,
                    selectionArgs,
                    null
            );
            cursor.moveToFirst();
            //マスターデータから部分一致検索
            if (cursor == null || cursor.getCount() == 0) {
                selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " LIKE '%" + str + "%'";
                cursor = getContentResolver().query(
                        UnifiedDataContentProvider.CONTENT_URI_SAKE,
                        projection,
                        selection,
                        selectionArgs,
                        null
                );
                cursor.moveToFirst();
            }
            //ユーザ作成データから完全一致検索
            if (cursor == null || cursor.getCount() == 0) {
                selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " = '" + str + "'";
                cursor = getContentResolver().query(
                        UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                        projection,
                        selection,
                        selectionArgs,
                        null
                );
                cursor.moveToFirst();
            }
            //ユーザ作成データから部分一致検索
            if (cursor == null || cursor.getCount() == 0) {
                selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " LIKE '%" + str + "%'";
                cursor = getContentResolver().query(
                        UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                        projection,
                        selection,
                        selectionArgs,
                        null
                );
                cursor.moveToFirst();
            }

            if (cursor != null && cursor.getCount() > 0) {
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
                sakeId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                if (hasFound) {
                    if (hasTasted) {
                        //発見済試飲済
                        Intent intent = new Intent(this, TasteTastedDataActivity.class);
                        intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                        startActivity(intent);
                    } else {
                        //発見済初飲酒
                        Intent intent = new Intent(this, TasteRegistrationActivity.class);
                        intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                        startActivity(intent);
                    }
                } else {
                    //初発見
                    Intent intent = new Intent(this, TasteRegistrationActivity.class);
                    intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                    startActivity(intent);
                }
            } else {
                //非該当
                Intent intent = new Intent(this, TasteNoDataActivity.class);
                intent.putExtra(TasteActivity.EXTRA_NO_DATA, str);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "不当な文字列", Toast.LENGTH_SHORT).show();
        }
    }
}
