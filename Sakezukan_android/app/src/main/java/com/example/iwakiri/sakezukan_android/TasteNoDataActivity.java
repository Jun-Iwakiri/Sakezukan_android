package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TasteNoDataActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean hasFound;
    Boolean hasTasted;
    Boolean isUserSake;
    String str;
    String displayNoDataBrand;
    String searchedStr;
    EditText editText;

    public static final String EXTRA_REQUEST = "EXTRA_REQUEST";
    public static final String EXTRA_HAS_TASTED = "EXTRA_HAS_TASTED";

    private long sakeId;
    private String[] projection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_BRAND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED,
            UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID
    };
    private String selection = null;
    private String[] selectionArgs = null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_no_data);

        Button homeButton = (Button) findViewById(R.id.button63);
        Button guideButton = (Button) findViewById(R.id.button62);
        Button tasteButton = (Button) findViewById(R.id.button61);
        Button findButton = (Button) findViewById(R.id.button60);
        Button helpButton = (Button) findViewById(R.id.button59);
        Button searchButton = (Button) findViewById(R.id.button72);
        Button goNewDataButton = (Button) findViewById(R.id.button73);
        TextView textView18 = (TextView) findViewById(R.id.textView18);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        goNewDataButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edittext5);

        Intent intent = getIntent();
        searchedStr = intent.getStringExtra(TasteActivity.EXTRA_NO_DATA);
        displayNoDataBrand = getString(R.string.no_data_text, searchedStr);
        textView18.setText(displayNoDataBrand);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button63:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button62:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button61:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button60:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button59:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button72:
                searchData();
                break;
            case R.id.button73:
                //新規登録申請して登録
                Intent intent = new Intent(getApplicationContext(), TasteRegistrationActivity.class);
                boolean isRequestedNewMaster = true;
                boolean hasTasted = true;
                intent.putExtra(EXTRA_REQUEST, isRequestedNewMaster);
                intent.putExtra(EXTRA_HAS_TASTED, hasTasted);
                startActivity(intent);
                finish();
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
                int masterSakeId = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID));

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
                if (masterSakeId > 0) {
                    isUserSake = false;
                } else {
                    isUserSake = true;
                }

                sakeId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                if (hasFound) {
                    if (hasTasted) {
                        //発見済試飲済
                        Intent intent = new Intent(this, TasteTastedDataActivity.class);
                        intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                        intent.putExtra(FindActivity.EXTRA_IS_USER_SAKE, isUserSake);
                        startActivity(intent);
                    } else {
                        //発見済初飲酒
                        Intent intent = new Intent(this, TasteRegistrationActivity.class);
                        intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                        intent.putExtra(FindActivity.EXTRA_IS_USER_SAKE, isUserSake);
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
