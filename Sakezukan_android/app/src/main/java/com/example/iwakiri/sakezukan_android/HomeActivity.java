package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button homeButton = (Button) findViewById(R.id.button6);
        Button guideButton = (Button) findViewById(R.id.button5);
        Button tasteButton = (Button) findViewById(R.id.button4);
        Button findButton = (Button) findViewById(R.id.button3);
        Button helpButton = (Button) findViewById(R.id.button2);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button6:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.button5:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                break;
            case R.id.button4:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                break;
            case R.id.button3:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                break;
            case R.id.button2:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                break;
        }
    }
}