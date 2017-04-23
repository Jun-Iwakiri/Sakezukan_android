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

public class FindNewDataActivity extends AppCompatActivity implements View.OnClickListener {

    private long sakeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_new_data);

        Button homeButton = (Button) findViewById(R.id.button32);
        Button guideButton = (Button) findViewById(R.id.button33);
        Button tasteButton = (Button) findViewById(R.id.button34);
        Button findButton = (Button) findViewById(R.id.button35);
        Button helpButton = (Button) findViewById(R.id.button36);
        Button goTasteButton = (Button) findViewById(R.id.button28);
        TextView textView = (TextView) findViewById(R.id.textView);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        goTasteButton.setOnClickListener(this);

        Intent intent = getIntent();
        sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
        Uri uri = ContentUris.withAppendedId(
                UnifiedDataContentProvider.CONTENT_URI_SAKE,
                sakeId
        );
        String[] projection = {
                UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_BRAND
        };
        Cursor cursor = getContentResolver().query(
                uri,
                projection,
                UnifiedDataColumns.DataColumns._ID + "=?",
                new String[]{Long.toString(sakeId)},
                null
        );
        cursor.moveToFirst();
        textView.setText(cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND)));
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
                Intent goTasteNewDataIntent = new Intent(getApplicationContext(), TasteNewDataActivity.class);
                goTasteNewDataIntent.putExtra("message", 2);
                startActivity(goTasteNewDataIntent);
                break;
        }
    }
}
