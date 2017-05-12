package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;

    private SimpleCursorAdapter adapter;

    private String[] informationProjection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE,
            UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY
    };

    private String[] userInfoProjection = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_USER_NAME,
            UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME,
            UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER
    };

    private String selection = null;
    private String[] selectionArgs = null;

    TextView userNameText;
    TextView licenseNameText;
    TextView licenseNumberText;
    TextView noUserInfoText;
    LinearLayout userInfoLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button homeButton = (Button) findViewById(R.id.button6);
        Button guideButton = (Button) findViewById(R.id.button5);
        Button tasteButton = (Button) findViewById(R.id.button4);
        Button findButton = (Button) findViewById(R.id.button3);
        Button helpButton = (Button) findViewById(R.id.button2);
        ImageButton userInfoEditButton = (ImageButton) findViewById(R.id.imageButton);
        userNameText = (TextView) findViewById(R.id.textView22);
        licenseNameText = (TextView) findViewById(R.id.textView21);
        licenseNumberText = (TextView) findViewById(R.id.textView10);
        noUserInfoText = (TextView) findViewById(R.id.textView16);
        userInfoLinear = (LinearLayout) findViewById(R.id.userInfoLinear);
        ListView informationList = (ListView) findViewById(R.id.information_listview);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        userInfoEditButton.setOnClickListener(this);

        queryUserInfo();

        Cursor informationCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_INFORMATIONS,
                informationProjection,
                selection,
                selectionArgs,
                null
        );
        informationCursor.moveToFirst();
        String[] from = {UnifiedDataColumns.DataColumns._ID,
                UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE,
                UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY
        };
        int[] to = {
                R.id.textView51,
                R.id.textView50,
                R.id.textView5
        };
        adapter = new SimpleCursorAdapter(this, R.layout.information_list_item, informationCursor, from, to, 0);
        informationList.setAdapter(adapter);
        //重複
        informationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button6:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button5:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button4:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button3:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button2:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.imageButton:
                Intent homeUserInfoIntent = new Intent(getApplicationContext(), HomeUserInfoActivity.class);
                startActivityForResult(homeUserInfoIntent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                queryUserInfo();
            }
        }
    }

    private void queryUserInfo() {
        Cursor userInfoCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_USER_DATA,
                userInfoProjection,
                selection,
                selectionArgs,
                null
        );
        userInfoCursor.moveToFirst();
        userInfoLinear.setVisibility(View.VISIBLE);
        noUserInfoText.setVisibility(View.VISIBLE);
        if (userInfoCursor != null && userInfoCursor.getCount() > 0) {
            noUserInfoText.setVisibility(View.GONE);
            String userName = userInfoCursor.getString(userInfoCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_NAME));
            String licenseName = userInfoCursor.getString(userInfoCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME));
            int licenseNumber = userInfoCursor.getInt(userInfoCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER));

            userNameText.setText(userName);
            licenseNameText.setText(licenseName);
            licenseNumberText.setText(Integer.toString(licenseNumber));
        } else {
            userInfoLinear.setVisibility(View.GONE);
        }
    }
}
