package com.example.iwakiri.sakezukan_android;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TasteRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "T_RegistrationActivity";
    public static final String LAST_INSERT_ROWID = "ROWID = last_insert_rowid()";
    public static final String EXTRA_USER_SAKE_ID = "EXTRA_USER_SAKE_ID";

    private long sakeId;
    private long userSakeId;
    private long userRecordId;
    Boolean hasFound;
    Boolean hasTasted;
    Boolean isRequestedNewMaster;
    Boolean isUserSake;
    Boolean isRequestedEdit;
    Cursor sakeCursor;
    String foundDate;
    String tastedDate;
    Integer totalGrade;
    Integer flavorGrade;
    Integer tasteGrade;
    String image;
    String review;
    ContentValues userRecordsValues;
    ContentValues sakeValues;
    Uri imageUri;
    String imagePath = null;

    ImageView imageView;
    EditText totalGradeEdit;
    EditText flavorGradeEdit;
    EditText tasteGradeEdit;
    EditText reviewEdit;
    EditText brandEdit;
    EditText breweryNameEdit;
    EditText breweryAddressEdit;
    EditText categoryEdit;

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
        totalGradeEdit = (EditText) findViewById(R.id.total_grade_edit);
        flavorGradeEdit = (EditText) findViewById(R.id.flavor_grade_edit);
        tasteGradeEdit = (EditText) findViewById(R.id.taste_grade_edit);
        reviewEdit = (EditText) findViewById(R.id.review_edit);
        brandEdit = (EditText) findViewById(R.id.brand_edit);
        breweryNameEdit = (EditText) findViewById(R.id.brewery_name_edit);
        breweryAddressEdit = (EditText) findViewById(R.id.brewery_address_edit);
        categoryEdit = (EditText) findViewById(R.id.category_edit);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        registrationButon.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.textView15);

        linearLayout3.setVisibility(View.GONE);
        Intent intent = getIntent();
        isRequestedEdit = intent.getBooleanExtra(TasteRegisteredDataActivity.EXTRA_IS_REQUESTED_EDIT, false);
        isRequestedNewMaster = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_REQUEST, false);
        isUserSake = intent.getBooleanExtra(FindActivity.EXTRA_IS_USER_SAKE, false);
        if (!isRequestedNewMaster) {
            sakeId = intent.getLongExtra(FindActivity.EXTRA_ID, 0L);
            Log.v("tag", String.valueOf(sakeId));
            Uri uri;
            if (isUserSake) {
                uri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                        sakeId
                );
            } else {
                uri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_SAKE,
                        sakeId
                );
            }
            String[] projection = {
                    UnifiedDataColumns.DataColumns._ID,
                    UnifiedDataColumns.DataColumns.COLUMN_BRAND,
                    UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND,
                    UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED,
            };
            sakeCursor = getContentResolver().query(
                    uri,
                    projection,
                    UnifiedDataColumns.DataColumns._ID + "=?",
                    new String[]{Long.toString(sakeId)},
                    null
            );
            sakeCursor.moveToFirst();

            int hasFoundInt = sakeCursor.getInt(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND));
            int hasTastedInt = sakeCursor.getInt(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED));

            //integerで格納された値をbooleanに変換
            switch (hasFoundInt) {
                case 0:
                    hasFound = false;
                    break;
                case 1:
                    hasFound = true;
                    break;
            }
            switch (hasTastedInt) {
                case 0:
                    hasTasted = false;
                    break;
                case 1:
                    hasTasted = true;
                    break;
            }

            String foundStr = sakeCursor.getString(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_BRAND));
            if (isRequestedEdit) {

                userRecordId = intent.getLongExtra(TasteActivity.EXTRA_USER_RECORDS_ID, 0L);

                Uri userRecordUri = ContentUris.withAppendedId(
                        UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                        userRecordId
                );
                String[] userRecordProjection = {
                        UnifiedDataColumns.DataColumns._ID,
                        UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND,
                        UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
                        UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
                        UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE,
                        UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE,
                        UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE,
                        UnifiedDataColumns.DataColumns.COLUMN_REVIEW
                };
                Cursor userRecordCursor = getContentResolver().query(
                        userRecordUri,
                        userRecordProjection,
                        UnifiedDataColumns.DataColumns._ID + "=?",
                        new String[]{Long.toString(userRecordId)},
                        null
                );
                userRecordCursor.moveToFirst();

                totalGrade = userRecordCursor.getInt(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE));
                flavorGrade = userRecordCursor.getInt(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE));
                tasteGrade = userRecordCursor.getInt(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE));
                review = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_REVIEW));
                imagePath = userRecordCursor.getString(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE));

                if (imagePath != null) {
                    //重複
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
                }

                totalGradeEdit.setText(Integer.toString(totalGrade));
                flavorGradeEdit.setText(Integer.toString(flavorGrade));
                tasteGradeEdit.setText(Integer.toString(tasteGrade));
                reviewEdit.setText(review);
                textView.setText(foundStr + "の記録編集");
            } else {
                if (hasFound) {
                    if (hasTasted) {
                        textView.setText(foundStr + "の試飲記録を追加登録");
                    } else {
                        textView.setText(foundStr + "の以前発見した情報の表示");
                    }
                } else {
                    textView.setText(foundStr + "の発見情報の表示");
                }
            }
        } else {
            linearLayout3.setVisibility(View.VISIBLE);
            textView.setText("新規登録申請");
            hasTasted = intent.getBooleanExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, false);
            if (!hasTasted) {
                linearLayout4.setVisibility(View.GONE);
            }
        }

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
                if (!isRequestedNewMaster) {

                    totalGrade = Integer.parseInt(totalGradeEdit.getText().toString().trim());
                    flavorGrade = Integer.parseInt(flavorGradeEdit.getText().toString().trim());
                    tasteGrade = Integer.parseInt(tasteGradeEdit.getText().toString().trim());
                    review = reviewEdit.getText().toString().trim();

                    image = imagePath;

                    //試飲日生成
                    userRecordsValues = new ContentValues();
                    tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                    long masterSakeId;
                    if (isUserSake) {
                        userSakeId = sakeId;
                        masterSakeId = 0;
                    } else {
                        masterSakeId = sakeId;
                        userSakeId = 0;
                    }
                    //試飲登録セット
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED, tastedDate);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE, image);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID, userSakeId);
                    //条件処理必要
                    int setTrueInt = 1;
                    sakeValues = new ContentValues();
                    if (!hasFound) {
                        //ユーザ情報挿入時
                        //発見日の生成
                        foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND, foundDate);

                        //挿入
                        getContentResolver().insert(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
                        );
                        Uri lastInsertedUri = getContentResolver().insert(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
                        );
                        userRecordId = ContentUris.parseId(lastInsertedUri);
                    } else {
                        //該当日本酒データIDを持っているユーザ記録を検索
                        String[] projection = {
                                UnifiedDataColumns.DataColumns._ID,
                                UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND,
                                UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED,
                                UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE,
                                UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE,
                                UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE,
                                UnifiedDataColumns.DataColumns.COLUMN_REVIEW,
                                UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID,
                                UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID
                        };
                        long id;
                        String selectionColumn;
                        Log.v("tag", String.valueOf(isUserSake));
                        if (isUserSake) {
                            id = userSakeId;
                            selectionColumn = UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID;
                        } else {
                            id = sakeId;
                            selectionColumn = UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID;
                        }
                        Uri userRecordUri = ContentUris.withAppendedId(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                                id
                        );
                        Cursor userRecordCursor = getContentResolver().query(
                                userRecordUri,
                                projection,
                                selectionColumn + "=?",
                                new String[]{Long.toString(id)},
                                null
                        );
                        userRecordCursor.moveToFirst();
                        userRecordId = userRecordCursor.getLong(userRecordCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));

                        Uri uri = ContentUris.withAppendedId(
                                UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS,
                                userRecordId
                        );
                        getContentResolver().update(
                                uri,
                                userRecordsValues,
                                UnifiedDataColumns.DataColumns._ID + "=?",
                                new String[]{Long.toString(userRecordId)}
                        );
                    }

                    //試飲フラグの更新
                    int hasTastedInt = setTrueInt;
                    sakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED, hasTastedInt);
                    //更新
                    Uri uri;
                    if (isUserSake) {
                        uri = UnifiedDataContentProvider.CONTENT_URI_USER_SAKE;
                    } else {
                        uri = UnifiedDataContentProvider.CONTENT_URI_SAKE;
                    }
                    Uri updateUri = ContentUris.withAppendedId(
                            uri,
                            sakeId
                    );
                    getContentResolver().update(
                            updateUri,
                            sakeValues,
                            UnifiedDataColumns.DataColumns._ID + "=?",
                            new String[]{Long.toString(sakeId)}
                    );
                } else {
                    //日本酒データ登録時

                    //ローカル日本酒データ生成用入力文字列取得
                    String brand = brandEdit.getText().toString().trim();
                    String breweryName = breweryNameEdit.getText().toString().trim();
                    String breweryAddress = breweryAddressEdit.getText().toString().trim();
                    String category = categoryEdit.getText().toString().trim();
                    int setTrueInt = 1;
                    int hasFoundInt = setTrueInt;
                    int hasTastedInt;
                    if (hasTasted) {
                        hasTastedInt = setTrueInt;
                    } else {
                        hasTastedInt = 0;
                    }
                    //データセット
                    ContentValues userSakeValues = new ContentValues();

//                    //初めてレコードが挿入される場合ID初期値をマスターテーブル最後尾+1とする
//                    Cursor cursor = getContentResolver().query(
//                            UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
//                            null,
//                            null,
//                            null,
//                            null
//                    );
//                    cursor.moveToFirst();
//                    if (cursor == null || cursor.getCount() == 0) {
//                        long _id;
//                        String[] lastInsertProjection = {
//                                UnifiedDataColumns.DataColumns._ID,
//                                UnifiedDataColumns.DataColumns.COLUMN_BRAND
//                        };
//                        sakeCursor = getContentResolver().query(
//                                UnifiedDataContentProvider.CONTENT_URI_SAKE,
//                                lastInsertProjection,
//                                LAST_INSERT_ROWID,
//                                null,
//                                null
//                        );
//                        sakeCursor.moveToFirst();
//                        _id = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));
//                        long masId = sakeCursor.getLong(sakeCursor.getColumnIndex(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID));
//                        Log.v("tag", String.valueOf(_id));
//                        Log.v("tag", String.valueOf(masId));
//                        long plusOneId = _id + 1L;
//                        Log.v("tag", String.valueOf(plusOneId));
//                        userSakeValues.put(UnifiedDataColumns.DataColumns._ID, _id + 1L);
//                    }

                    long masterSakeId = 0;
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BRAND, brand);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_NAME, breweryName);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_BREWERY_ADDRESS, breweryAddress);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_CATEGORY, category);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_FOUND, hasFoundInt);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_HAS_TASTED, hasTastedInt);
                    userSakeValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);

                    //挿入
                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_USER_SAKE, userSakeValues
                    );

                    //挿入したレコードIDを取得する
                    String[] userSakeProjection = {UnifiedDataColumns.DataColumns._ID};
                    String userSakeSelection = LAST_INSERT_ROWID;
                    Cursor insertedCursor = getContentResolver().query(
                            UnifiedDataContentProvider.CONTENT_URI_USER_SAKE,
                            userSakeProjection,
                            userSakeSelection,
                            null,
                            null
                    );
                    insertedCursor.moveToFirst();
                    sakeId = 0;
                    userSakeId = insertedCursor.getLong(insertedCursor.getColumnIndex(UnifiedDataColumns.DataColumns._ID));

                    //共通カラムのセット
                    userRecordsValues = new ContentValues();
                    foundDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_FOUND, foundDate);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_MASTER_SAKE_ID, masterSakeId);
                    userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_SAKE_ID, userSakeId);
                    //試飲時追加生成
                    if (hasTasted) {
                        tastedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                        totalGrade = Integer.parseInt(totalGradeEdit.getText().toString().trim());
                        flavorGrade = Integer.parseInt(flavorGradeEdit.getText().toString().trim());
                        tasteGrade = Integer.parseInt(tasteGradeEdit.getText().toString().trim());
                        review = reviewEdit.getText().toString().trim();
                        image = imagePath;

                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_DATE_TASTED, tastedDate);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TOTAL_GRADE, totalGrade);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_FLAVOR_GRADE, flavorGrade);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_TASTE_GRADE, tasteGrade);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_USER_RECORDS_IMAGE, image);
                        userRecordsValues.put(UnifiedDataColumns.DataColumns.COLUMN_REVIEW, review);
                    }
                    //ユーザ記録挿入
                    getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
                    );
                    Uri lastInsertedUri = getContentResolver().insert(
                            UnifiedDataContentProvider.CONTENT_URI_USER_RECORDS, userRecordsValues
                    );
                    userRecordId = ContentUris.parseId(lastInsertedUri);
                }
                Intent registrationIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
                registrationIntent.putExtra(EXTRA_USER_SAKE_ID, userSakeId);
                registrationIntent.putExtra(TasteActivity.EXTRA_USER_RECORDS_ID, userRecordId);
                registrationIntent.putExtra(TasteNoDataActivity.EXTRA_REQUEST, isRequestedNewMaster);
                registrationIntent.putExtra(TasteNoDataActivity.EXTRA_HAS_TASTED, hasTasted);
                registrationIntent.putExtra(FindActivity.EXTRA_IS_USER_SAKE, isUserSake);
                if (isRequestedEdit) {
                    setResult(RESULT_OK, registrationIntent);
                } else {
                    startActivity(registrationIntent);
                }
                finish();
                break;
            case R.id.button76:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:

                    //重複
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
}
