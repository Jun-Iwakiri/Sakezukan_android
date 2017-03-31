package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean isExist;
    Boolean isFound;
    Button button;
    EditText editText;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Button homeButton = (Button) findViewById(R.id.button21);
        Button guideButton = (Button) findViewById(R.id.button20);
        Button tasteButton = (Button) findViewById(R.id.button19);
        Button findButton = (Button) findViewById(R.id.button18);
        Button helpButton = (Button) findViewById(R.id.button17);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defineData();
                searchData();
            }
        });
    }

    private void defineData() {
        isExist = null;
        isFound = null;
        str = null;

        str = editText.getText().toString().trim();
        switch (str) {
            case "0":
                isExist = true;
                isFound = true;
                break;
            case "1":
                isExist = true;
                isFound = false;
                break;
            case "2":
                isExist = false;
                isFound = false;
                break;
        }
    }

    private void searchData() {
        if (!str.isEmpty()) {
            if (isExist) {
                if (isFound) {
                    Intent intent = new Intent(this, FindNewDataActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, FindFoundDataActivity.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(this, FindNoDataActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "0,1,2以外", Toast.LENGTH_SHORT).show();
        }
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
        }
    }
}
