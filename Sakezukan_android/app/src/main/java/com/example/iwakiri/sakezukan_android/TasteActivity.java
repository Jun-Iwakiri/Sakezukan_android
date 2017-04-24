package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TasteActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    Boolean hasFound;
    Boolean hasTasted;
    String str;
    EditText editText;

    public static final String EXTRA_USER_RECORDS_ID = "EXTRA_USER_RECORDS_ID";
    public static final String EXTRA_NO_DATA = "EXTRA_NO_DATA";

    private long sakeId;
    private String[] projection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_BRAND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED
    };
    private String selection = null;
    private String[] selectionArgs = null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste);

        Button homeButton = (Button) findViewById(R.id.button16);
        Button guideButton = (Button) findViewById(R.id.button15);
        Button tasteButton = (Button) findViewById(R.id.button14);
        Button findButton = (Button) findViewById(R.id.button13);
        Button helpButton = (Button) findViewById(R.id.button12);
        Button searchButton = (Button) findViewById(R.id.button69);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edittext4);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button16:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button15:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button14:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button13:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button12:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button69:
                searchData();
        }
    }

    private void searchData() {
        //FindActivityに重複表現有り
        str = editText.getText().toString().trim();
        if (!str.isEmpty()) {
            selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " LIKE '%" + str + "%'";
            cursor = getContentResolver().query(
                    UnifiedDataContentProvider.CONTENT_URI_SAKE,
                    projection,
                    selection,
                    selectionArgs,
                    null
            );
            cursor.moveToFirst();

            if (cursor != null && cursor.getCount() > 0) {
                int hasFoundInt = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND));
                int hasTastedInt = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED));

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
                        Intent intent = new Intent(this, TasteNewDataActivity.class);
                        intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                        startActivity(intent);
                    }
                } else {
                    //初発見初飲酒
                    Intent intent = new Intent(this, TasteNewDataActivity.class);
                    intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                    startActivity(intent);
                }
            } else {
                //非該当
                Intent intent = new Intent(this, TasteNoDataActivity.class);
                intent.putExtra(EXTRA_NO_DATA, str);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "不当な文字列", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //FindActivity,GuideActivityに重複表現有り
        return new CursorLoader(
                this,
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                projection,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
