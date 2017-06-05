package com.example.iwakiri.sakezukan_android;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;

    private ContentValues userRecordValues;
    private int userRecordCount = 0;
    boolean isParsed = false;

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
        TextView emptyText = (TextView) findViewById(R.id.textView17);
        userInfoLinear = (LinearLayout) findViewById(R.id.userInfoLinear);
        ListView informationList = (ListView) findViewById(R.id.information_listview);
        informationList.setEmptyView(emptyText);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        userInfoEditButton.setOnClickListener(this);

        if (!isParsed) {
            isParsed = true;
            String json = getString(R.string.json_sample);
            String parsedText = "";
            try {
                JSONObject rootObject = new JSONObject(json);
                parsedText = rootObject.toString(4);
                Log.v("parsedText", parsedText);

                GsonBuilder builder = new GsonBuilder();

//                builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//                    @Override
//                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//                        String dateStr = json.getAsString();
//                        Log.v("dateStr", dateStr);
//                        try {
//                            Date date = sdf.parse(dateStr);
//                            Log.v("date", date.toString());
//                            return date;
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });

                builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Gson gson = builder.create();
                JsonData jsonData = gson.fromJson(json, JsonData.class);

                insertUserInfo(jsonData);
                insertSake(jsonData);
                insertUserSake(jsonData);
                insertUserRecord(jsonData);
                insertInformation(jsonData);
                insertHelpCategory(jsonData);
                insertHelpContent(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        queryUserInfo();

        selection = null;
        selectionArgs = null;
        Cursor informationCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_INFORMATION,
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

    private void insertUserInfo(JsonData jsonData) {
        if (jsonData.getUserInfo() != null) {
            ContentValues userInfoValues = new ContentValues();
            long userInfoId = jsonData.getUserInfo().getUserInfoId();
            String userName = jsonData.getUserInfo().getUserName();
            String licenseName = jsonData.getUserInfo().getLicenseName();
            Integer licenseNumber = jsonData.getUserInfo().getLicenseNumber();

            userInfoValues.put(UnifiedDataColumns.DataColumns._ID, userInfoId);
            userInfoValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_NAME, userName);
            userInfoValues.put(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NAME, licenseName);
            userInfoValues.put(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER, licenseNumber);

            getContentResolver().insert(
                    UnifiedDataContentProvider.CONTENT_URI_USER_INFO,
                    userInfoValues
            );
        }
    }

    private void insertSake(JsonData jsonData) {
        if (jsonData.sakes != null) {
            ContentValues sakeValues = new ContentValues();
            for (int i = 0; i < jsonData.sakes.size(); i++) {
                long sakeId = jsonData.sakes.get(i).sakeId;
                String brand = jsonData.sakes.get(i).brand;
                String breweryName = jsonData.sakes.get(i).breweryName;
                String breweryAddress = jsonData.sakes.get(i).breweryAddress;
                float lowerAlcoholContent = jsonData.sakes.get(i).lowerAlcoholContent;
                float upperAlcoholContent = jsonData.sakes.get(i).upperAlcoholContent;
                String category = jsonData.sakes.get(i).category;
                long masterSakeId = jsonData.sakes.get(i).masterSakeId;

                sakeValues.put(UnifiedDataColumns.DataColumns._ID, sakeId);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BRAND, brand);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME, breweryName);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS, breweryAddress);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT, lowerAlcoholContent);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT, upperAlcoholContent);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY, category);
                sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);

                getContentResolver().insert(
                        UnifiedDataContentProvider.CONTENT_URI_SAKE,
                        sakeValues
                );
            }
        }
    }

    private void insertUserSake(JsonData jsonData) {
        if (jsonData.userSakes != null) {
            ContentValues userSakeValues = new ContentValues();
            for (int i = 0; i < jsonData.userSakes.size(); i++) {
                long userSakeId = jsonData.userSakes.get(i).sakeId;
                String brand = jsonData.userSakes.get(i).brand;
                String breweryName = jsonData.userSakes.get(i).breweryName;
                String breweryAddress = jsonData.userSakes.get(i).breweryAddress;
                float lowerAlcoholContent = jsonData.userSakes.get(i).lowerAlcoholContent;
                float upperAlcoholContent = jsonData.userSakes.get(i).upperAlcoholContent;
                String category = jsonData.userSakes.get(i).category;
                long masterSakeId = jsonData.userSakes.get(i).masterSakeId;

                userSakeValues.put(UnifiedDataColumns.DataColumns._ID, userSakeId);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BRAND, brand);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME, breweryName);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS, breweryAddress);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_LOWER_ALCOHOL_CONTENT, lowerAlcoholContent);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_UPPER_ALCOHOL_CONTENT, upperAlcoholContent);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY, category);
                userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);

                getContentResolver().insert(
                        UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                        userSakeValues
                );
            }
        }
    }

    private void insertUserRecord(JsonData jsonData) {
        if (jsonData.userRecords != null) {
            userRecordValues = new ContentValues();
            for (userRecordCount = 0; userRecordCount < jsonData.userRecords.size(); userRecordCount++) {
                long userRecordId = jsonData.userRecords.get(userRecordCount).userRecordId;
                String foundDate = jsonData.userRecords.get(userRecordCount).foundDate;
                String tastedDate = jsonData.userRecords.get(userRecordCount).tastedDate;
                String totalGrade = jsonData.userRecords.get(userRecordCount).totalGrade;
                String flavorGrade = jsonData.userRecords.get(userRecordCount).flavorGrade;
                String tasteGrade = jsonData.userRecords.get(userRecordCount).tasteGrade;
                String userRecordImage = jsonData.userRecords.get(userRecordCount).userRecordImage;
                String review = jsonData.userRecords.get(userRecordCount).review;
                long recordedMasterSakeId = jsonData.userRecords.get(userRecordCount).recordedMasterSakeId;
                long userSakeId = jsonData.userRecords.get(userRecordCount).userSakeId;
                userRecordValues.put(UnifiedDataColumns.DataColumns._ID, userRecordId);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE, foundDate);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE, tastedDate);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE, userRecordImage);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, recordedMasterSakeId);
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID, userSakeId);

                getContentResolver().insert(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                        userRecordValues
                );
            }
        }
    }

    private void insertInformation(JsonData jsonData) {
        if (jsonData.informations != null) {
            ContentValues informationValues = new ContentValues();
            for (int i = 0; i < jsonData.informations.size(); i++) {
                long informationId = jsonData.informations.get(i).informationId;
                String informationTitle = jsonData.informations.get(i).informationTitle;
                String informationBody = jsonData.informations.get(i).informationBody;

                Uri informationUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_INFORMATION,
                        informationId
                );
                selection = UnifiedDataColumns.DataColumns._ID + "=?";
                selectionArgs = new String[]{Long.toString(informationId)};
                Cursor informationCursor = getContentResolver().query(
                        informationUri,
                        null,
                        selection,
                        selectionArgs,
                        null
                );
                informationCursor.moveToFirst();
                if (informationCursor == null || informationCursor.getCount() == 0) {
                    //SQLite側に同じidのデータがない場合は挿入
                    informationValues.put(UnifiedDataColumns.DataColumns._ID, informationId);
                    informationValues.put(UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE, informationTitle);
                    informationValues.put(UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY, informationBody);

                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_INFORMATION,
                            informationValues
                    );
                } else {
                    //SQLite側に同じidのデータがある場合
                    String dbInformationTitle = informationCursor.getString(informationCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE));
                    String dbInformationBody = informationCursor.getString(informationCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY));
                    if (!informationTitle.equals(dbInformationTitle) || !informationBody.equals(dbInformationBody)) {
                        //他カラムの値に変更があった場合は更新
                        if (!informationTitle.equals(dbInformationTitle)) {
                            informationValues.put(UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_TITLE, informationTitle);
                        }
                        if (!informationBody.equals(dbInformationBody)) {
                            informationValues.put(UnifiedDataColumns.DataColumns.COLUMN_INFORMATION_BODY, informationBody);
                        }
                        getContentResolver().update(
                                informationUri,
                                informationValues,
                                selection,
                                selectionArgs
                        );
                    }
                }
            }
        }
    }

    private void insertHelpCategory(JsonData jsonData) {
        if (jsonData.helpCategories != null) {
            ContentValues helpCategoryValues = new ContentValues();
            for (int i = 0; i < jsonData.helpCategories.size(); i++) {
                Log.v("start", "start");
                long helpCategoryId = jsonData.helpCategories.get(i).helpCategoryId;
                String helpCategory = jsonData.helpCategories.get(i).helpCategoryBody;

                Uri helpCategoryUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_HELP_CATEGORY,
                        helpCategoryId
                );
                selection = UnifiedDataColumns.DataColumns._ID + "=?";
                selectionArgs = new String[]{Long.toString(helpCategoryId)};
                Cursor helpCategoryCursor = getContentResolver().query(
                        helpCategoryUri,
                        null,
                        selection,
                        selectionArgs,
                        null
                );
                helpCategoryCursor.moveToFirst();
                if (helpCategoryCursor == null || helpCategoryCursor.getCount() == 0) {
                    //SQLite側に同じidのデータがない場合は挿入
                    helpCategoryValues.put(UnifiedDataColumns.DataColumns._ID, helpCategoryId);
                    helpCategoryValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_BODY, helpCategory);
                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_HELP_CATEGORY,
                            helpCategoryValues
                    );
                } else {
                    //SQLite側に同じidのデータがある場合
                    String dbHelpCategoryBody = helpCategoryCursor.getString(helpCategoryCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_BODY));
                    if (!helpCategory.equals(dbHelpCategoryBody)) {
                        //他カラムの値に変更があった場合は更新
                        helpCategoryValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_BODY, helpCategory);
                        getContentResolver().update(
                                helpCategoryUri,
                                helpCategoryValues,
                                selection,
                                selectionArgs
                        );
                    }
                }
            }
        }
    }

    private void insertHelpContent(JsonData jsonData) {
        if (jsonData.helpContents != null) {
            ContentValues helpContentValues = new ContentValues();
            for (int i = 0; i < jsonData.helpContents.size(); i++) {
                long helpContentId = jsonData.helpContents.get(i).helpContentId;
                long recordedHelpCategoryId = jsonData.helpContents.get(i).recordedHelpCategoryId;
                String helpTitle = jsonData.helpContents.get(i).helpTitle;
                String helpBody = jsonData.helpContents.get(i).helpBody;

                Uri helpContentUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_HELP_CONTENT,
                        helpContentId
                );
                selection = UnifiedDataColumns.DataColumns._ID + "=?";
                selectionArgs = new String[]{Long.toString(helpContentId)};
                Cursor helpContentCursor = getContentResolver().query(
                        helpContentUri,
                        null,
                        selection,
                        selectionArgs,
                        null
                );
                helpContentCursor.moveToFirst();
                if (helpContentCursor == null || helpContentCursor.getCount() == 0) {
                    helpContentValues.put(UnifiedDataColumns.DataColumns._ID, helpContentId);
                    helpContentValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_ID, recordedHelpCategoryId);
                    helpContentValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_TITLE, helpTitle);
                    helpContentValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY, helpBody);
                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_HELP_CONTENT,
                            helpContentValues
                    );
                } else {
                    long dbRecordedHelpCategoryId = helpContentCursor.getLong(helpContentCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_ID));
                    String dbHelpTitle = helpContentCursor.getString(helpContentCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_TITLE));
                    String dbHelpBody = helpContentCursor.getString(helpContentCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY));
                    if (recordedHelpCategoryId != dbRecordedHelpCategoryId || !helpTitle.equals(dbHelpTitle) || !helpBody.equals(dbHelpBody)) {
                        if (recordedHelpCategoryId != dbRecordedHelpCategoryId) {
                            helpContentValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_CATEGORY_ID, recordedHelpCategoryId);
                        }
                        if (!helpTitle.equals(dbHelpTitle)) {
                            helpContentValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_TITLE, helpTitle);
                        }
                        if (!helpBody.equals(dbHelpBody)) {
                            helpContentValues.put(UnifiedDataColumns.DataColumns.COLUMN_HELP_BODY, helpBody);
                        }
                        getContentResolver().update(
                                helpContentUri,
                                helpContentValues,
                                selection,
                                selectionArgs
                        );
                    }
                }
            }
        }
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
                UnifiedDataContentProvider.CONTENT_URI_USER_INFO,
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
            Integer licenseNumber = userInfoCursor.getInt(userInfoCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_LICENSE_NUMBER));

            userNameText.setText(R.string.display_no_registerd_text);
            licenseNameText.setText(R.string.display_no_registerd_text);
            licenseNumberText.setText(R.string.display_no_registerd_text);
            if (userName != null) {
                userNameText.setText(userName);
            }
            if (licenseName != null) {
                licenseNameText.setText(licenseName);
            }
            if (licenseNumber != 0) {
                licenseNumberText.setText(Integer.toString(licenseNumber));
            }
        } else {
            userInfoLinear.setVisibility(View.GONE);
        }
    }
}
