package com.example.iwakiri.sakezukan_android;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_HAS_TASTED;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_ADD;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_EDIT;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_UPDATE;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_MASTER_SAKE_ID;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_IS_REQUESTED_NEW_MASTER;
import static com.example.iwakiri.sakezukan_android.SakeConstants.EXTRA_USER_RECORD_ID;

public class TasteRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "T_RegistrationActivity";

    InputMethodManager inputMethodManager;
    LinearLayout layout;
    Intent intent;
    Cursor sakeCursor;
    Cursor userSakeCursor;
    Cursor userRecordCursor;
    Uri uri;
    Uri sakeUri;
    Uri userRecordUri;
    private long sakeId;
    private long masterSakeId;
    private long userSakeId;
    private long userRecordId;
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
    Boolean hasTasted;
    Boolean isRequestedNewMaster;
    Boolean isRequestedEdit;
    Boolean isRequestedAdd;
    Boolean isRequestedUpdate;
    String foundBrand;
    String foundDate;
    String tastedDate;
    String totalGrade;
    String flavorGrade;
    String tasteGrade;
    String image;
    String review;
    String brand;
    String breweryName;
    String breweryAddress;
    String category;
    ContentValues userRecordValues;
    ContentValues sakeValues;
    Uri imageUri;
    String imagePath = null;
    private String selection = null;
    private String[] selectionArgs = null;

    ImageView imageView;
    EditText reviewEdit;
    EditText brandEdit;
    EditText breweryNameEdit;
    EditText categoryEdit;
    TextView textView;

    Spinner totalGradeSpinner;
    Spinner flavorGradeSpinner;
    Spinner tasteGradeSpinner;
    Spinner breweryAddressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_registration);

        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
        Button homeButton = (Button) findViewById(R.id.button58);
        Button guideButton = (Button) findViewById(R.id.button57);
        Button tasteButton = (Button) findViewById(R.id.button56);
        Button findButton = (Button) findViewById(R.id.button55);
        Button helpButton = (Button) findViewById(R.id.button54);
        Button registrationButon = (Button) findViewById(R.id.button70);
        Button cameraButton = (Button) findViewById(R.id.button8);
        Button cancelButton = (Button) findViewById(R.id.button76);
        imageView = (ImageView) findViewById(R.id.imageView4);
        reviewEdit = (EditText) findViewById(R.id.review_edit);
        brandEdit = (EditText) findViewById(R.id.brand_edit);
        breweryNameEdit = (EditText) findViewById(R.id.brewery_name_edit);
        categoryEdit = (EditText) findViewById(R.id.category_edit);
        totalGradeSpinner = (Spinner) findViewById(R.id.total_grade_spinner);
        flavorGradeSpinner = (Spinner) findViewById(R.id.flavor_grade_spinner);
        tasteGradeSpinner = (Spinner) findViewById(R.id.taste_grade_spinner);
        breweryAddressSpinner = (Spinner) findViewById(R.id.brewery_address_spinner);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        registrationButon.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layout = (LinearLayout) findViewById(R.id.tasteRegistrationLinearLayout);

        createSpinner();

        textView = (TextView) findViewById(R.id.textView15);
        linearLayout3.setVisibility(View.GONE);
        intent = getIntent();
        isRequestedEdit = intent.getBooleanExtra(EXTRA_IS_REQUESTED_EDIT, false);
        isRequestedNewMaster = intent.getBooleanExtra(EXTRA_IS_REQUESTED_NEW_MASTER, false);
        isRequestedAdd = intent.getBooleanExtra(EXTRA_IS_REQUESTED_ADD, false);
        isRequestedUpdate = intent.getBooleanExtra(EXTRA_IS_REQUESTED_UPDATE, false);

        if (!isRequestedNewMaster) {
            //既存日本酒データへの試飲登録時（マスター、ローカル）
            specifyUserRecord();
            //編集リクエスト時
            if (isRequestedEdit || isRequestedUpdate) {
                setUserRecordData();
            } else {
                if (foundDate != null) {
                    if (tastedDate != null) {
                        textView.setText(foundBrand + "の試飲記録を更新");
                    } else {
                        textView.setText(foundBrand + "の試飲記録登録");
                    }
                } else {
                    textView.setText(foundBrand + "の試飲記録登録");
                }
            }
        } else {
            //ローカル日本酒データ作成
            linearLayout3.setVisibility(View.VISIBLE);
            textView.setText("新規登録申請");
            hasTasted = intent.getBooleanExtra(EXTRA_HAS_TASTED, false);
            if (!hasTasted) {
                linearLayout4.setVisibility(View.GONE);
            }
        }

    }

    private void createSpinner() {
        totalGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (position != 0) {
                    totalGrade = (String) spinner.getSelectedItem();
                } else {
                    totalGrade = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        flavorGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (position != 0) {
                    flavorGrade = (String) spinner.getSelectedItem();
                } else {
                    flavorGrade = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tasteGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (position != 0) {
                    tasteGrade = (String) spinner.getSelectedItem();
                } else {
                    tasteGrade = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        breweryAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (position != 0) {
                    breweryAddress = (String) spinner.getSelectedItem();
                } else {
                    breweryAddress = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void specifyUserRecord() {
        sakeId = intent.getLongExtra(EXTRA_ID, 0L);
        masterSakeId = intent.getLongExtra(EXTRA_MASTER_SAKE_ID, 0L);
        userRecordId = intent.getLongExtra(EXTRA_USER_RECORD_ID, 0L);
        if (masterSakeId == 0) {
            uri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
        } else {
            uri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
        }
        //酒id取得
        sakeUri = ContentUris.withAppendedId(
                uri,
                sakeId
        );
        selection = UnifiedDataColumns.DataColumns._ID + "=?";
        selectionArgs = new String[]{Long.toString(sakeId)};
        sakeCursor = getContentResolver().query(
                sakeUri,
                sakeProjection,
                selection,
                selectionArgs,
                null
        );
        sakeCursor.moveToFirst();

        foundBrand = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));

        //該当ユーザ記録ID取得
        userRecordUri = UnifiedDataContentProvider.CONTENT_URI_USER_RECORD;
        selection = UnifiedDataColumns.DataColumns._ID + "=?";
        selectionArgs = new String[]{Long.toString(userRecordId)};
        userRecordCursor = getContentResolver().query(
                userRecordUri,
                userRecordProjection,
                selection,
                selectionArgs,
                null
        );
        userRecordCursor.moveToFirst();


        foundDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE));
        tastedDate = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE));
    }

    private void setUserRecordData() {
        userRecordId = intent.getLongExtra(EXTRA_USER_RECORD_ID, 0L);
        userRecordUri = UnifiedDataContentProvider.CONTENT_URI_USER_RECORD;
        selection = UnifiedDataColumns.DataColumns._ID + "=?";
        selectionArgs = new String[]{Long.toString(userRecordId)};
        userRecordCursor = getContentResolver().query(
                userRecordUri,
                userRecordProjection,
                selection,
                selectionArgs,
                null
        );
        userRecordCursor.moveToFirst();

        totalGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE));
        flavorGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE));
        tasteGrade = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE));
        review = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW));
        imagePath = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE));

        if (imagePath != null) {
            setImage(imagePath);
        }

        //取得した評価値と同じ値を示しているリストアイテムの番地を識別
        TypedArray totalTypedArray = getResources().obtainTypedArray(R.array.grade_list);
        int totalGradeLength = totalTypedArray.length();
        for (int i = 0; i < totalGradeLength; i++) {
            if (totalGrade != null) {
                if (totalGrade.equals(totalGradeSpinner.getItemAtPosition(i))) {
                    totalGradeSpinner.setSelection(i);
                }
            } else {
                totalGradeSpinner.setSelection(0);
            }
        }
        TypedArray flavorTypedArray = getResources().obtainTypedArray(R.array.grade_list);
        int flavorLength = flavorTypedArray.length();
        for (int i = 0; i < flavorLength; i++) {
            if (flavorGrade != null) {
                if (flavorGrade.equals(flavorGradeSpinner.getItemAtPosition(i))) {
                    flavorGradeSpinner.setSelection(i);
                }
            } else {
                flavorGradeSpinner.setSelection(0);
            }
        }
        TypedArray tasteTypedArray = getResources().obtainTypedArray(R.array.grade_list);
        int tasteLength = tasteTypedArray.length();
        for (int i = 0; i < tasteLength; i++) {
            if (tasteGrade != null) {
                if (tasteGrade.equals(tasteGradeSpinner.getItemAtPosition(i))) {
                    tasteGradeSpinner.setSelection(i);
                }
            } else {
                tasteGradeSpinner.setSelection(0);
            }
        }
        reviewEdit.setText(review);
        textView.setText(foundBrand + "の試飲記録編集");
    }

    private void setImage(String path) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        Log.v(TAG, "oldSize_" + imageWidth + ":" + imageHeight);

        int size = 1;
        if (Math.max(imageWidth, imageHeight) > 640) {
            size = Math.max(imageWidth, imageHeight) / 640;
        }
        options.inSampleSize = size;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        Log.v(TAG, "newSize_" + bitmap.getWidth() + ":" + bitmap.getHeight());
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button58:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button57:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button56:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button55:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button54:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button8:
                ContentValues contentValues = new ContentValues();//キーと値を複数格納できる
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                imagePath = getPath(getApplicationContext(), imageUri);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.button70:
                registerUserRecord();
                break;
            case R.id.button76:
                finish();
                break;
        }
    }

    private void registerUserRecord() {
        if (!isRequestedNewMaster) {
            //既存日本酒データへの試飲登録時（マスター、ローカル）
            registerToSake();
        } else {
            //日本酒データ登録時
            brand = brandEdit.getText().toString().trim();
            if (!brand.isEmpty()) {
                registerToNewUserSake();
            } else {
                Toast.makeText(this, "タイトルは必須入力", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent registrationIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
        registrationIntent.putExtra(EXTRA_ID, sakeId);
        registrationIntent.putExtra(EXTRA_MASTER_SAKE_ID, masterSakeId);
        registrationIntent.putExtra(EXTRA_USER_RECORD_ID, userRecordId);
        registrationIntent.putExtra(EXTRA_IS_REQUESTED_NEW_MASTER, isRequestedNewMaster);
        if (isRequestedEdit) {
            setResult(RESULT_OK, registrationIntent);
        } else if (isRequestedAdd) {
            setResult(RESULT_OK, registrationIntent);
        } else {
            startActivity(registrationIntent);
        }
        finish();
    }

    private void registerToSake() {
        review = reviewEdit.getText().toString().trim();
        image = imagePath;
        //試飲日生成
        userRecordValues = new ContentValues();
        tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());

        if (uri == UnifiedDataContentProvider.CONTENT_URI_USER_SAKE) {
            //ローカル日本酒データへの登録時
            userSakeId = sakeId;
            masterSakeId = 0;
        } else {
            //マスター日本酒データへの登録時
            masterSakeId = sakeId;
            userSakeId = 0;
        }
        //試飲登録セット
        if (totalGrade != null) {
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
        } else {
            userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE);
        }
        if (flavorGrade != null) {
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
        } else {
            userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE);
        }
        if (tasteGrade != null) {
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
        } else {
            userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE);
        }
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE, tastedDate);
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE, image);
        if (!review.isEmpty()) {
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
        } else {
            userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_REVIEW);
        }

        sakeValues = new ContentValues();
        if (foundDate != null) {
            //更新
            userRecordUri = ContentUris.withAppendedId(
                    UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                    userRecordId
            );
            selection = UnifiedDataColumns.DataColumns._ID + "=?";
            selectionArgs = new String[]{Long.toString(userRecordId)};
            getContentResolver().update(
                    userRecordUri,
                    userRecordValues,
                    selection,
                    selectionArgs
            );
        } else {
            //挿入
            //発見日の生成
            foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE, foundDate);
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID, userSakeId);

            Uri lastInsertedUri = getContentResolver().insert(
                    UnifiedDataContentProvider.CONTENT_URI_USER_RECORD,
                    userRecordValues
            );
            userRecordId = ContentUris.parseId(lastInsertedUri);
        }
    }

    private void registerToNewUserSake() {
        //ローカル日本酒データ生成用入力文字列取得
        brand = brandEdit.getText().toString().trim();
        breweryName = breweryNameEdit.getText().toString().trim();
        category = categoryEdit.getText().toString().trim();
        //データセット
        ContentValues userSakeValues = new ContentValues();

        //初めてレコードが挿入される場合ID初期値をマスターテーブル最後尾+1とする
        userSakeCursor = getContentResolver().query(
                UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                null,
                null,
                null,
                null
        );
        userSakeCursor.moveToFirst();
        //ローカル日本酒データ有無の識別
        if (userSakeCursor == null || userSakeCursor.getCount() == 0) {
            //無かった場合idをマスターid最後尾+1とする
            sakeCursor = getContentResolver().query(UnifiedDataContentProvider.CONTENT_URI_SAKE,
                    new String[]{UnifiedDataColumns.DataColumns._ID},
                    null,
                    null,
                    UnifiedDataColumns.DataColumns._ID + " DESC LIMIT 1"
            );
            sakeCursor.moveToFirst();
            if (sakeCursor != null && sakeCursor.getCount() > 0) {
                userSakeId = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
                sakeId = userSakeId + 1L;
                userSakeValues.put(UnifiedDataColumns.DataColumns._ID, sakeId);
            }
        }

        masterSakeId = 0;
        userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BRAND, brand);
        if (!breweryName.isEmpty()) {
            userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME, breweryName);
        }
        if (breweryAddress != null) {
            userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS, breweryAddress);
        } else {
            userSakeValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS);
        }
        if (!category.isEmpty()) {
            userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY, category);
        }
        userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);

        //挿入
        Uri lastInsertedSakeUri = getContentResolver().insert(
                UnifiedDataContentProvider.CONTENT_URI_USER_SAKE, userSakeValues
        );
        userSakeId = ContentUris.parseId(lastInsertedSakeUri);
        sakeId = userSakeId;

        //共通カラムのセット
        userRecordValues = new ContentValues();
        foundDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.v("foundDate", foundDate);
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FOUND_DATE, foundDate);
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);
        userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID, userSakeId);


        String json = getString(R.string.json_sample);
        String parsedText = "";
        JSONObject rootObject = null;

        try {
            rootObject = new JSONObject(json);
            parsedText = rootObject.toString(4);
            Log.v("parsedText", parsedText);

            GsonBuilder builder = new GsonBuilder();

            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Gson gson = builder.create();
            JsonData jsonData = gson.fromJson(json, JsonData.class);

            JsonData.HelpCategory helpCategory = new JsonData.HelpCategory();
            helpCategory.setHelpCategoryId(2);
            helpCategory.setHelpCategoryBody("追加ヘルプカテゴリ");
            List<JsonData.HelpCategory> list = new ArrayList<>();

//            List<JsonData.HelpCategory> list = jsonData.getHelpCategories();

            list.add(helpCategory);

            String json2 = gson.toJson(json);
            String parsedText2 = "";
            JSONObject rootObject2 = null;
            try {
                rootObject2 = new JSONObject(json);
                parsedText2 = rootObject2.toString(4);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v("parsedText", parsedText2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //試飲時追加生成
        if (hasTasted) {
            tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            review = reviewEdit.getText().toString().trim();
            image = imagePath;

            if (totalGrade != null) {
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
            } else {
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE);
            }
            if (flavorGrade != null) {
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
            } else {
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE);
            }
            if (tasteGrade != null) {
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
            } else {
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE);
            }
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTED_DATE, tastedDate);
            userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORD_IMAGE, image);
            if (!review.isEmpty()) {
                userRecordValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
            } else {
                userRecordValues.putNull(UnifiedDataColumns.DataColumns.COLUMN_REVIEW);
            }
        }
        //ユーザ記録挿入
        Uri lastInsertedUri = getContentResolver().insert(
                UnifiedDataContentProvider.CONTENT_URI_USER_RECORD, userRecordValues
        );
        userRecordId = ContentUris.parseId(lastInsertedUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:

                    Bitmap bitmap = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imagePath, options);

                    int imageWidth = options.outWidth;
                    int imageHeight = options.outHeight;

                    Log.v(TAG, "oldSize_" + imageWidth + ":" + imageHeight);

                    int size = 1;
                    if (Math.max(imageWidth, imageHeight) > 640) {
                        size = Math.max(imageWidth, imageHeight) / 640;
                    }
                    options.inSampleSize = size;
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeFile(imagePath, options);
                    Log.v(TAG, "newSize_" + bitmap.getWidth() + ":" + bitmap.getHeight());

                    imageView.setImageBitmap(bitmap);
                    break;
                case RESULT_CANCELED:
                    getContentResolver().delete(
                            MediaStore.Files.getContentUri("external"),
                            MediaStore.Files.FileColumns.DATA + "=?",
                            new String[]{imagePath}
                    );
                    break;
                default:
                    break;
            }
        }
    }

    public String getPath(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] colms = {MediaStore.Images.Media.DATA};
        Cursor cursor = contentResolver.query(uri, colms, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(0);
        cursor.close();
        return path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputMethodManager.hideSoftInputFromWindow(layout.getWindowToken(), inputMethodManager.HIDE_NOT_ALWAYS);
        layout.requestFocus();
        return true;
    }
}
