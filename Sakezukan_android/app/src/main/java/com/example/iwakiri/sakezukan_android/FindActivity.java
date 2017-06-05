package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_MASTER_SAKE_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_NO_DATA;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_USER_RECORD_ID;

public class FindActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    InputMethodManager inputMethodManager;
    LinearLayout layout;
    EditText editText;
    ListView resultList;
    String str;
    String brand;
    String breweryName;
    String category;
    int queryOrder;

    final ArrayList<UnifiedData> unifiedDatas = new ArrayList<>();
    UnifiedData unifiedDataItem;
    private SimpleSakeAdapter customAdapter;
    Uri sakeUri;
    Uri userRecordUri;
    Cursor sakeCursor;
    Cursor userRecordCursor;
    boolean isLoadFinished = false;
    private long sakeId;
    private long masterSakeId;
    private long userRecordId;
    private long perfectMatchId;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Button homeButton = (Button) findViewById(R.id.button21);
        Button guideButton = (Button) findViewById(R.id.button20);
        Button tasteButton = (Button) findViewById(R.id.button19);
        Button findButton = (Button) findViewById(R.id.button18);
        Button helpButton = (Button) findViewById(R.id.button17);
        Button searchButton = (Button) findViewById(R.id.button);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        resultList = (ListView) findViewById(R.id.find_result_listview);
        View header = getLayoutInflater().inflate(R.layout.result_list_header, null);
        resultList.addHeaderView(header);
        editText = (EditText) findViewById(R.id.edittext);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layout = (LinearLayout) findViewById(R.id.findLinearLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button21:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button20:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button19:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button18:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button17:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button:
                searchData();
                break;
        }
    }

    private void searchData() {
        str = editText.getText().toString().trim();
        queryOrder = 1;
        if (!str.isEmpty()) {
            if (isLoadFinished) {
                customAdapter.clear();
            }
            querySake();
            if (!isLoadFinished) {
                customAdapter = new SimpleSakeAdapter(this, R.layout.simple_sake_list_item, unifiedDatas);
                if (!customAdapter.isEmpty()) {
                    resultList.setAdapter(customAdapter);
                    resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                UnifiedData clickedData = (UnifiedData) resultList.getItemAtPosition(position);
                                sakeId = clickedData.getSakeId();
                                masterSakeId = clickedData.getMasterSakeId();
                                identifyResult();
                            }
                        }
                    });
                    getSupportLoaderManager().initLoader(0, null, this);
                } else {
                    //非該当
                    Intent intent = new Intent(this, FindNoDataActivity.class);
                    intent.putExtra(EXTRA_NO_DATA, str);
                    startActivity(intent);
                }
            } else {
                customAdapter.notifyDataSetChanged();
                if (customAdapter.isEmpty()) {
                    //非該当
                    Intent intent = new Intent(this, FindNoDataActivity.class);
                    intent.putExtra(EXTRA_NO_DATA, str);
                    startActivity(intent);
                }
            }
        } else {
            Toast.makeText(this, "不当な文字列", Toast.LENGTH_SHORT).show();
        }
    }

    private void querySake() {
        boolean queryNext = true;
        boolean addNext;
        while (queryNext) {
            switch (queryOrder) {
                case 1:
                    //マスター完全一致
                    selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " = '" + str + "'";
                    sakeUri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
                    break;
                case 2:
                    //ローカル完全一致
                    selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " = '" + str + "'";
                    sakeUri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
                    break;
                case 3:
                    //マスター部分一致
                    selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " LIKE '%" + str + "%'";
                    sakeUri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
                    break;
                case 4:
                    //ローカル部分一致
                    selection = UnifiedDataColumns.DataColumns.COLUMN_BRAND + " LIKE '%" + str + "%'";
                    sakeUri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
                    break;
                default:
                    queryNext = false;
                    break;
            }
            if (queryOrder <= 4) {
                sakeCursor = getContentResolver().query(
                        sakeUri,
                        sakeProjection,
                        selection,
                        selectionArgs,
                        null
                );
                addNext = sakeCursor.moveToFirst();
                addSakeItem(addNext);
                queryOrder += 1;
            } else {
                queryNext = false;
            }
        }
    }

    private void addSakeItem(boolean next) {
        boolean addNext = next;
        while (addNext) {
            sakeId = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
            brand = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
            breweryName = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME));
            category = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY));
            masterSakeId = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID));
            if (queryOrder == 1 || queryOrder == 2) {
                //部分一致検索時に完全一致該当データを除外する為idを取得
                perfectMatchId = sakeId;

                unifiedDataItem = new UnifiedData();
                unifiedDataItem.setSake(sakeId, brand, breweryName, null, 0, 0, category, masterSakeId);
                unifiedDatas.add(unifiedDataItem);
            } else if (sakeId != perfectMatchId) {
                //部分一致検索時に完全一致検索時該当していたデータ以外をセット
                unifiedDataItem = new UnifiedData();
                unifiedDataItem.setSake(sakeId, brand, breweryName, null, 0, 0, category, masterSakeId);
                unifiedDatas.add(unifiedDataItem);
            }
            addNext = sakeCursor.moveToNext();
        }
    }

    private void identifyResult() {
        long id;
        //マスターidカラムの値による問合せ先識別
        if (masterSakeId > 0) {
            id = masterSakeId;
            selection = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID + "=?";
        } else {
            id = sakeId;
            selection = UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID + "=?";
        }
        selectionArgs = new String[]{Long.toString(id)};
        userRecordUri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                id
        );
        //日本酒idが格納されている試飲記録を問合せ
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
            //発見日と試飲日の有無で遷移先を識別
            if (foundDate != null) {
                if (tastedDate != null) {
                    //発見済試飲済
                    Intent intent = new Intent(this, FindFoundDataActivity.class);
                    intent.putExtra(EXTRA_ID, id);
                    intent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                    intent.putExtra(EXTRA_USER_RECORD_ID, userRecordId);
                    startActivity(intent);
                } else {
                    //発見済初飲酒
                    Intent intent = new Intent(this, FindFoundDataActivity.class);
                    intent.putExtra(EXTRA_ID, id);
                    intent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
                    intent.putExtra(EXTRA_USER_RECORD_ID, userRecordId);
                    startActivity(intent);
                }
            }
        } else {
            //初発見
            Intent intent = new Intent(this, FindRegistrationActivity.class);
            intent.putExtra(EXTRA_ID, id);
            startActivity(intent);
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
        isLoadFinished = true;
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputMethodManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        layout.requestFocus();
        return true;

    }
}
