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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_MASTER_SAKE_ID;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private GuideListSakeAdapter guideListSakeAdapter;

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

    Cursor cursor;
    Cursor sakeCursor;
    Cursor userSakeCursor;
    Cursor userRecordCursor;
    private long sakeId;
    private long masterSakeId;
    private long userRecordId;
    boolean isfilterdFoundDate = false;
    boolean isfilterdTasteDate = false;

    final ArrayList<UnifiedData> unifiedDatas = new ArrayList<>();
    UnifiedData unifiedDataItem;

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
                guideListSakeAdapter.clear();
                isfilterdFoundDate = false;
                isfilterdTasteDate = false;
                queryListItem();
                guideListSakeAdapter.notifyDataSetChanged();
                return true;
            case R.id.only_found:
                guideListSakeAdapter.clear();
                isfilterdFoundDate = true;
                isfilterdTasteDate = false;
                queryListItem();
                guideListSakeAdapter.notifyDataSetChanged();
                return true;
            case R.id.only_tasted:
                guideListSakeAdapter.clear();
                isfilterdTasteDate = true;
                isfilterdFoundDate = false;
                queryListItem();
                guideListSakeAdapter.notifyDataSetChanged();
                return true;
            case R.id.stats:
                Intent intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
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
        TextView emptyText = (TextView) findViewById(R.id.textView9);
        final ListView sakeList = (ListView) findViewById(R.id.sake_listview);
        sakeList.setEmptyView(emptyText);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        queryListItem();
        guideListSakeAdapter = new GuideListSakeAdapter(this, R.layout.sake_list_item, unifiedDatas);
        sakeList.setAdapter(guideListSakeAdapter);

        sakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UnifiedData clickedData = (UnifiedData) sakeList.getItemAtPosition(position);
                sakeId = clickedData.getSakeId();
                masterSakeId = clickedData.getMasterSakeId();
                userRecordId = clickedData.getUserRecordId();
                if (userRecordId > 0) {
                    Intent intent = new Intent(getApplicationContext(), GuideDetailActivity.class);
                    intent.putExtra(EXTRA_ID, sakeId);
                    intent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "未発見の銘柄", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void queryListItem() {

        sakeCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                null,
                null,
                null,
                null
        );
        userSakeCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                null,
                null,
                null,
                null
        );
        cursor = sakeCursor;
        boolean next = cursor.moveToFirst();
        while (next) {
            addItemData(next);
            if (cursor == sakeCursor) {
                cursor = userSakeCursor;
                next = cursor.moveToFirst();
            } else {
                next = cursor.moveToNext();
            }
        }
    }

    private void addItemData(boolean next) {
        boolean itemAddNext = next;
        while (itemAddNext) {
            unifiedDataItem = new UnifiedData();

            sakeId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
            String brand = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
            String breweryName = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));
            String breweryAddress = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS));
            float lowerAlcoholContent = cursor.getFloat(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT));
            float upperAlcoholContent = cursor.getFloat(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT));
            String category = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY));
            masterSakeId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID));

            //該当idを格納している試飲記録問合せ
            Uri userRecordUri = ContentUris.withAppendedId(
                    UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                    sakeId
            );
            if (cursor == sakeCursor) {
                selection = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + "=?";
            } else {
                selection = UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID + "=?";
            }
            selectionArgs = new String[]{Long.toString(sakeId)};
            userRecordCursor = getContentResolver().query(
                    userRecordUri,
                    userRecordProjection,
                    selection,
                    selectionArgs,
                    null
            );
            userRecordCursor.moveToFirst();
            if (userRecordCursor != null && userRecordCursor.getCount() > 0) {
                userRecordId = userRecordCursor.getLong(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                String foundDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE));
                String tastedDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE));
                String totalGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE));
                String flavorGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE));
                String tasteGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE));
                String review = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW));

                if (isfilterdTasteDate) {
                    if (tastedDate != null) {
                        unifiedDataItem.setUserRecord(userRecordId, foundDate, tastedDate, totalGrade, flavorGrade, tasteGrade, review);
                        unifiedDataItem.setSake(sakeId, brand, breweryName, breweryAddress, lowerAlcoholContent, upperAlcoholContent, category, masterSakeId);
                        unifiedDatas.add(unifiedDataItem);
                    }
                } else {
                    unifiedDataItem.setUserRecord(userRecordId, foundDate, tastedDate, totalGrade, flavorGrade, tasteGrade, review);
                    unifiedDataItem.setSake(sakeId, brand, breweryName, breweryAddress, lowerAlcoholContent, upperAlcoholContent, category, masterSakeId);
                    unifiedDatas.add(unifiedDataItem);
                }
            } else {
                if (!isfilterdFoundDate) {
                    if (!isfilterdTasteDate) {
                        unifiedDataItem.setSake(sakeId, brand, breweryName, breweryAddress, lowerAlcoholContent, upperAlcoholContent, category, masterSakeId);
                        unifiedDatas.add(unifiedDataItem);
                    }
                }
            }
            itemAddNext = cursor.moveToNext();
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
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                userRecordProjection,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        guideListSakeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
