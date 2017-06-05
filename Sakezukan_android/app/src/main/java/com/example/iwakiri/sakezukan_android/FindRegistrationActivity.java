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
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_MASTER_SAKE_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_USER_RECORD_ID;

public class FindRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private SimpleSakeAdapter customAdapter;
    final ArrayList<UnifiedData> unifiedDatas = new ArrayList<>();
    UnifiedData unifiedDataItem;
    String category;
    ContentValues userRecordValues;
    String breweryName;
    String foundDate;
    Uri userRecordUri;
    Uri insertedUri;
    Cursor sakeCursor;
    Cursor userRecordCursor;
    private Uri sakeUri;
    private long sakeId;
    private long foundId;
    private long masterSakeId;
    private long userRecordId;
    private long foundUserRecordId;
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
    private String brand;

    TextView textView11;

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
        textView11 = (TextView) findViewById(R.id.textView11);
        ListView simpleSakeList = (ListView) findViewById(R.id.simple_sake_listview);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        goTasteButton.setOnClickListener(this);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(EXTRA_ID, 0L);

        sakeUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
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

        brand = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
        breweryName = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));

        textView.setText(brand + "を見つけました");
        selection = UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME + "=?";
        selectionArgs = new String[]{breweryName};
        sakeCursor = getContentResolver().query(
                sakeUri,
                sakeProjection,
                selection,
                selectionArgs,
                null
        );
        boolean next = sakeCursor.moveToFirst();

        foundId = sakeId;
        masterSakeId = sakeId;
        while (next) {
            sakeId = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
            userRecordUri = ContentUris.withAppendedId(
                    UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                    sakeId
            );
            selection = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + "=?";
            selectionArgs = new String[]{Long.toString(sakeId)};
            userRecordCursor = getContentResolver().query(
                    userRecordUri,
                    userRecordProjection,
                    selection,
                    selectionArgs,
                    null
            );
            userRecordCursor.moveToFirst();
            if (userRecordCursor == null || userRecordCursor.getCount() == 0) {
                addListItem();
            }
            next = sakeCursor.moveToNext();
        }

        customAdapter = new SimpleSakeAdapter(this, R.layout.simple_sake_list_item, unifiedDatas);
        simpleSakeList.setAdapter(customAdapter);
        simpleSakeList.setEnabled(false);
    }

    private void addListItem() {
        unifiedDataItem = new UnifiedData();
        brand = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
        breweryName = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));
        category = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY));

        //ユーザ記録テーブルにレコード挿入、発見日と日本酒IDを格納
        userRecordValues = new ContentValues();
        foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE, foundDate);
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, sakeId);
        insertedUri = getContentResolver().insert(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORD, userRecordValues
        );
        userRecordId = ContentUris.parseId(insertedUri);

        if (sakeId != foundId) {
            //直接発見した以外の銘柄である場合
            textView11.setVisibility(View.VISIBLE);
            textView11.setText(breweryName + "の別の銘柄を発見しました");
            //リストビュー用データセット
            unifiedDataItem.setSake(sakeId, brand, breweryName, null, 0, 0, category, 0);
            unifiedDatas.add(unifiedDataItem);
        } else {
            //直接発見した銘柄である場合
            foundUserRecordId = userRecordId;
            textView11.setVisibility(View.INVISIBLE);
        }
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
                goTasteNewDataIntent.putExtra(EXTRA_ID, foundId);
                goTasteNewDataIntent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                goTasteNewDataIntent.putExtra(EXTRA_USER_RECORD_ID, foundUserRecordId);
                startActivity(goTasteNewDataIntent);
                break;
        }
    }
}
