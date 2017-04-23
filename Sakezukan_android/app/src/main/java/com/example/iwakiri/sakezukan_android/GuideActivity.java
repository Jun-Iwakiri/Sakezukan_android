package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "GuideActivity";
    private SimpleCursorAdapter adapter;

    private String[] projection = {
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

    private String selection = null;
    private String[] selectionArgs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Button homeButton = (Button) findViewById(R.id.button87);
        Button guideButton = (Button) findViewById(R.id.button86);
        Button tasteButton = (Button) findViewById(R.id.button85);
        Button findButton = (Button) findViewById(R.id.button84);
        Button helpButton = (Button) findViewById(R.id.button83);
//        Button noTastedButton = (Button) findViewById(R.id.button76);
//        Button tastedButton = (Button) findViewById(R.id.button77);
        ListView sakeList = (ListView) findViewById(R.id.sake_listview);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
//        noTastedButton.setOnClickListener(this);
//        tastedButton.setOnClickListener(this);

        Cursor cursor = getContentResolver().query(UnifiedDataContentProvider.CONTENT_URI_SAKE, projection, selection, selectionArgs, null);
        String[] from = {UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_BRAND,
                UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME,
                UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS,
                UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT,
                UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT,
                UnifiedDataColumns.DataColumns.COLUMN_CATEGORY,
                UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
                UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED
        };
        int[] to = {
                R.id.textView29,
                R.id.textView28,
                R.id.textView27,
                R.id.textView26,
                R.id.textView25,
                R.id.textView24,
                R.id.textView23,
                R.id.textView22,
                R.id.textView21
        };
        adapter = new SimpleCursorAdapter(this, R.layout.sake_list_item, cursor, from, to, 0);
        sakeList.setAdapter(adapter);
        sakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GuideDetailActivity.class);
                intent.putExtra(FindActivity.EXTRA_ID, id);
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button87:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button86:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button85:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button84:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button83:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
//            case R.id.button76:
//                Intent notTastedIntent = new Intent(getApplicationContext(), GuideDetailActivity.class);
//                notTastedIntent.putExtra("tasted", false);
//                startActivity(notTastedIntent);
//                break;
//            case R.id.button77:
//                Intent tastedIntent = new Intent(getApplicationContext(), GuideDetailActivity.class);
//                tastedIntent.putExtra("tasted", true);
//                startActivity(tastedIntent);
//                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
        adapter.swapCursor(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
