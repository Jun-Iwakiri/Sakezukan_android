package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private CustomAdapter customAdapter;

    public static final String EXTRA_MASTER_SAKE_ID = "EXTRA_MASTER_SAKE_ID";

    private String[] sakeProjection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_BRAND,
            UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME,
            UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS,
            UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT,
            UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT,
            UnifiedDataColumns.DataColumns.COLUMN_CATEGORY,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
            UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED,
            UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID
    };
    private String[] userRecordProjection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND,
            UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
            UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
            UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID,
            UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID
    };

    private String selection = null;
    private String[] selectionArgs = null;
    private String sortOrder = null;

    final ArrayList<UnifiedData> unifiedData = new ArrayList<>();
    UnifiedData unifiedDataItem;

    Cursor cursor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.guide_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id:
                customAdapter.clear();
                sortOrder = UnifiedDataColumns.DataColumns._ID + " DESC";
                queryListItem();
                return true;
            case R.id.date_found:
                customAdapter.clear();
                sortOrder = UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND;
                queryListItem();
                return true;
            case R.id.total_grade:
                customAdapter.clear();
                sortOrder = UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE;
                queryListItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Button homeButton = (Button) findViewById(R.id.button87);
        Button guideButton = (Button) findViewById(R.id.button86);
        Button tasteButton = (Button) findViewById(R.id.button85);
        Button findButton = (Button) findViewById(R.id.button84);
        Button helpButton = (Button) findViewById(R.id.button83);
        final ListView sakeList = (ListView) findViewById(R.id.sake_listview);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        queryListItem();

        customAdapter = new CustomAdapter(this, R.layout.sake_list_item, unifiedData);
        sakeList.setAdapter(customAdapter);

        sakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UnifiedData clickedData = (UnifiedData) sakeList.getItemAtPosition(position);
                long sakeId = clickedData.getSakeId();
                long masterSakeId = clickedData.getMasterSakeId();
                Intent intent = new Intent(getApplicationContext(), GuideDetailActivity.class);
                intent.putExtra(FindActivity.EXTRA_ID, sakeId);
                intent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void queryListItem() {

        Cursor allUserRecordCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                userRecordProjection,
                selection,
                selectionArgs,
                sortOrder
        );
        boolean next = allUserRecordCursor.moveToFirst();
        while (next) {
            Uri sakeTableUri;
            long masterSakeId = allUserRecordCursor.getLong(allUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID));
            long userSakeId = allUserRecordCursor.getLong(allUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID));
            long id;
            if (masterSakeId > 0) {
                id = masterSakeId;
                sakeTableUri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
            } else {
                id = userSakeId;
                sakeTableUri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
            }
            String sakeSelection = UnifiedDataColumns.DataColumns._ID + "=?";
            String[] sakeSelectionArgs = new String[]{Long.toString(id)};
            Uri sakeUri = ContentUris.withAppendedId(
                    sakeTableUri,
                    id
            );
            cursor = getContentResolver().query(
                    sakeUri,
                    sakeProjection,
                    sakeSelection,
                    sakeSelectionArgs,
                    null
            );
            cursor.moveToFirst();

            String foundDate = allUserRecordCursor.getString(allUserRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND));
            if (foundDate != null) {
                long sakeId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                String brand = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
                String breweryName = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));
                String breweryAddress = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS));
                float lowerAlcoholContent = cursor.getFloat(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT));
                float upperAlcoholContent = cursor.getFloat(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT));
                String category = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY));
                masterSakeId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID));

                unifiedDataItem = new UnifiedData();
                unifiedDataItem.setSake(sakeId, brand, breweryName, breweryAddress, lowerAlcoholContent, upperAlcoholContent, category, masterSakeId);
                unifiedData.add(unifiedDataItem);
            }
            next = allUserRecordCursor.moveToNext();
        }
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
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                sakeProjection,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
