package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final String KEY1 = "TITLE";
    private final String KEY2 = "SUMMARY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button homeButton = (Button) findViewById(R.id.button26);
        Button guideButton = (Button) findViewById(R.id.button25);
        Button tasteButton = (Button) findViewById(R.id.button24);
        Button findButton = (Button) findViewById(R.id.button23);
        Button helpButton = (Button) findViewById(R.id.button22);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        //親の要素を格納
        List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
        Cursor helpCategoryCursor = getContentResolver().query(UnifiedDataContentProvider.CONTENT_URI_HELP_CATEGORY, null, null, null, null);
        boolean helpCategoryNext = helpCategoryCursor.moveToFirst();
        int helpCategoryId = 0;
        while (helpCategoryNext) {
            helpCategoryId = helpCategoryCursor.getInt(helpCategoryCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
            String helpCategory = helpCategoryCursor.getString(helpCategoryCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY));
            Map<String, String> parentData = new HashMap<String, String>();
            parentData.put(KEY1, helpCategory);
            parentList.add(parentData);
            helpCategoryNext = helpCategoryCursor.moveToNext();
        }

        //子の要素を格納
        List<List<Map<String, String>>> allChildList = new ArrayList<List<Map<String, String>>>();
        Cursor helpContentsCursor = getContentResolver().query(UnifiedDataContentProvider.CONTENT_URI_HELP_CONTENTS, null, null, null, null);
        boolean helpContentNext = helpContentsCursor.moveToFirst();
        helpCategoryCursor = getContentResolver().query(UnifiedDataContentProvider.CONTENT_URI_HELP_CATEGORY, null, null, null, null);
        helpCategoryNext = helpCategoryCursor.moveToFirst();
        while (helpCategoryNext) {
            List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
            helpCategoryId = helpCategoryCursor.getInt(helpCategoryCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
            while (helpContentNext) {
                int cursorHelpCategoryId = helpContentsCursor.getInt(helpContentsCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_ID));
                String helpTitle = helpContentsCursor.getString(helpContentsCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_TITLE));
                String helpBody = helpContentsCursor.getString(helpContentsCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY));
                //カテゴリ識別処理
                if (cursorHelpCategoryId == helpCategoryId) {
                    Map<String, String> childData = new HashMap<String, String>();
                    childData.put(KEY1, helpTitle);
                    childData.put(KEY2, helpBody);
                    childList.add(childData);
                }
                helpContentNext = helpContentsCursor.moveToNext();
            }
            allChildList.add(childList);
            helpContentNext = helpContentsCursor.moveToFirst();
            helpCategoryNext = helpCategoryCursor.moveToNext();
        }

        SimpleExpandableListAdapter adapter =
                new SimpleExpandableListAdapter(
                        this,
                        parentList,
                        android.R.layout.simple_expandable_list_item_1,
                        new String[]{KEY1},
                        new int[]{android.R.id.text1, android.R.id.text2},
                        allChildList,
                        android.R.layout.simple_expandable_list_item_2,
                        new String[]{KEY1, KEY2},
                        new int[]{android.R.id.text1, android.R.id.text2}
                );
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.expandableListView);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button26:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button25:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button24:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button23:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button22:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
