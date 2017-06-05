package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    InputMethodManager inputMethodManager;
    LinearLayout layout;
    private long userInfoId;
    String userName;
    String licenseName;
    String licenseNumber;
    Cursor cursor;

    EditText userNameEdit;
    EditText licenseNameEdit;
    EditText licenseNumberEdit;

    private String[] projeciton = {
            UnifiedDataColumns.DataColumns._ID,
            UnifiedDataColumns.DataColumns.COLUMN_USER_NAME,
            UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME,
            UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER
    };

    Uri uri = ContentUris.withAppendedId(
            UnifiedDataContentProvider.CONTENT_URI_USER_INFO,
            userInfoId
    );

    private String selection = null;
    private String[] selectionArgs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_info);

        Button homeButton = (Button) findViewById(R.id.button51);
        Button guideButton = (Button) findViewById(R.id.button50);
        Button tasteButton = (Button) findViewById(R.id.button49);
        Button findButton = (Button) findViewById(R.id.button48);
        Button helpButton = (Button) findViewById(R.id.button47);
        Button registrationButton = (Button) findViewById(R.id.button52);
        Button cancelButton = (Button) findViewById(R.id.button53);
        Button deleteButton = (Button) findViewById(R.id.button27);
        userNameEdit = (EditText) findViewById(R.id.userNameEdit);
        licenseNameEdit = (EditText) findViewById(R.id.licenseNameEdit);
        licenseNumberEdit = (EditText) findViewById(R.id.licenseNumberEdit);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        registrationButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layout = (LinearLayout) findViewById(R.id.homeUserInfoLinearLayout);

        getIntent();

        cursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_USER_INFO,
                projeciton,
                selection,
                selectionArgs,
                null
        );
        cursor.moveToFirst();

        deleteButton.setVisibility(View.GONE);
        if (cursor != null && cursor.getCount() > 0) {
            userInfoId = cursor.getLong(cursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));

            deleteButton.setVisibility(View.VISIBLE);
            userName = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_NAME));
            licenseName = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME));
            licenseNumber = cursor.getString(cursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER));
            if (userName != null) {
                userNameEdit.setText(userName);
            }
            if (licenseName != null) {
                licenseNameEdit.setText(licenseName);
            }
            if (licenseNumber != null) {
                licenseNumberEdit.setText(String.valueOf(licenseNumber));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button51:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button50:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button49:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button48:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button47:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button52:
                registerUserInfo();
                break;
            case R.id.button27:
                getContentResolver().delete(
                        UnifiedDataContentProvider.CONTENT_URI_USER_INFO,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(userInfoId)}
                );
                Intent deleteIntent = new Intent();
                setResult(RESULT_OK, deleteIntent);
                finish();
                break;
            case R.id.button53:
                finish();
        }
    }

    private void registerUserInfo() {
        userName = userNameEdit.getText().toString().trim();
        licenseName = licenseNameEdit.getText().toString().trim();
        licenseNumber = licenseNumberEdit.getText().toString().trim();
        if (!userName.isEmpty() || !licenseName.isEmpty() || !licenseNumber.isEmpty()) {
            ContentValues userInfoValues = new ContentValues();
            if (!userName.isEmpty()) {
                userInfoValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_NAME, userName);
            } else {
                userInfoValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_USER_NAME);
            }
            if (!licenseName.isEmpty()) {
                userInfoValues.put(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME, licenseName);
            } else {
                userInfoValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME);
            }
            if (!licenseNumber.isEmpty()) {
                Integer licenseNumberInt = Integer.parseInt(licenseNumber);
                userInfoValues.put(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER, licenseNumberInt);
            } else {
                userInfoValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER);
            }

            if (cursor != null && cursor.getCount() > 0) {
                getContentResolver().update(
                        uri,
                        userInfoValues,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(userInfoId)}
                );
            } else {
                getContentResolver().insert(
                        UnifiedDataContentProvider.CONTENT_URI_USER_INFO,
                        userInfoValues
                );
            }
            Intent editIntent = new Intent();
            setResult(RESULT_OK, editIntent);
            finish();
        } else {
            Toast.makeText(this, "不当な文字列", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputMethodManager.hideSoftInputFromWindow(layout.getWindowToken(), inputMethodManager.HIDE_NOT_ALWAYS);
        layout.requestFocus();
        return true;
    }
}
