package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TasteNoDataActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_no_data);

        Button homeButton = (Button) findViewById(R.id.button63);
        Button guideButton = (Button) findViewById(R.id.button62);
        Button tasteButton = (Button) findViewById(R.id.button61);
        Button findButton = (Button) findViewById(R.id.button60);
        Button helpButton = (Button) findViewById(R.id.button59);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button63:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button62:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button61:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button60:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button59:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
        }
    }
}
