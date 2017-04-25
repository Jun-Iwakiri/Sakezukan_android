package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuideDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private long sakeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        Button homeButton = (Button) findViewById(R.id.button82);
        Button guideButton = (Button) findViewById(R.id.button81);
        Button tasteButton = (Button) findViewById(R.id.button80);
        Button findButton = (Button) findViewById(R.id.button79);
        Button helpButton = (Button) findViewById(R.id.button78);
        Button goTasteButton = (Button) findViewById(R.id.button83);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        goTasteButton.setOnClickListener(this);
        TextView textView36 = (TextView) findViewById(R.id.textView36);
        TextView textView35 = (TextView) findViewById(R.id.textView35);
        TextView textView34 = (TextView) findViewById(R.id.textView34);
        TextView textView33 = (TextView) findViewById(R.id.textView33);
        TextView textView32 = (TextView) findViewById(R.id.textView32);
        TextView textView20 = (TextView) findViewById(R.id.textView20);
        TextView textView31 = (TextView) findViewById(R.id.textView31);
        TextView textView30 = (TextView) findViewById(R.id.textView30);
        TextView textView6 = (TextView) findViewById(R.id.textView6);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
        if (sakeId != 0) {
            Uri uri = ContentUris.withAppendedId(
                    UnifiedDataContentProvider.CONTENT_URI_SAKE,
                    sakeId
            );
            String[] projection = {
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
            Cursor cursor = getContentResolver().query(
                    uri,
                    projection,
                    UnifiedDataColumns.DataColumns._ID + "=?",
                    new String[]{Long.toString(sakeId)},
                    null
            );
            cursor.moveToFirst();
            textView36.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID)));
            textView35.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)));
            textView34.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME)));
            textView33.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS)));
            textView32.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT)));
            textView20.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT)));
            textView31.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY)));
            textView30.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND)));
            textView6.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED)));

            //findActivityに重複表現有り
            boolean hasTasted = false;
            int hasTastedInt = cursor.getInt(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED));
            switch (hasTastedInt) {
                case 0:
                    hasTasted = false;
                    break;
                case 1:
                    hasTasted = true;
                    break;
            }
            if (hasTasted) {
                goTasteButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button82:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button81:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button80:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button79:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button78:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button83:
                Intent goTasteIntent = new Intent(getApplicationContext(), TasteRegistrationActivity.class);
                goTasteIntent.putExtra(FindActivity.EXTRA_ID, sakeId);
                startActivity(goTasteIntent);
                break;
        }
    }
}
