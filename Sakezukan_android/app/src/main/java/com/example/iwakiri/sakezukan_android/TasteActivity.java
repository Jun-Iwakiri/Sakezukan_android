package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TasteActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean isDataExist;
    Boolean isTasted;
    Boolean isFound;
    String str;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste);

        Button homeButton = (Button) findViewById(R.id.button16);
        Button guideButton = (Button) findViewById(R.id.button15);
        Button tasteButton = (Button) findViewById(R.id.button14);
        Button findButton = (Button) findViewById(R.id.button13);
        Button helpButton = (Button) findViewById(R.id.button12);
        Button searchButton = (Button) findViewById(R.id.button69);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edittext4);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button16:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button15:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button14:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button13:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button12:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button69:
                defineData();
                searchData();
        }
    }

    private void searchData() {
        if (!str.isEmpty()) {
            if (isDataExist) {
                if (isTasted) {
                    Intent intent = new Intent(this, TasteTastedDataActivity.class);
                    startActivity(intent);
                } else {
                    if (isFound) {
                        //初発見初飲酒
                        Intent intent = new Intent(this, TasteNewDataActivity.class);
                        intent.putExtra("massage", 1);
                        startActivity(intent);
                    } else {
                        //初発見飲酒済
                        Intent intent = new Intent(this, TasteNewDataActivity.class);
                        intent.putExtra("massage", 2);
                        startActivity(intent);
                    }
                }
            } else {
                Intent intent = new Intent(this, TasteNoDataActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "不当な文字列", Toast.LENGTH_SHORT);
        }
    }

    private void defineData() {
        isDataExist = null;
        isFound = null;
        isTasted = null;
        str = null;

        str = editText.getText().toString().trim();
        switch (str) {
            case "0":
                //発見済飲酒済
                isDataExist = true;
                isFound = true;
                isTasted = true;
                break;
            case "1":
                //発見済初飲酒
                isDataExist = true;
                isFound = true;
                isTasted = false;
                break;
            case "2":
                //初発見
                isDataExist = true;
                isFound = false;
                isTasted = false;
                break;
            default:
                //非該当
                isDataExist = false;
        }
    }
}
