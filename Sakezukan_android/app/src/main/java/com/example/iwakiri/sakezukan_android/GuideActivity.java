package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Button homeButton = (Button) findViewById(R.id.button87);
        Button guideButton = (Button) findViewById(R.id.button86);
        Button tasteButton = (Button) findViewById(R.id.button85);
        Button findButton = (Button) findViewById(R.id.button84);
        Button helpButton = (Button) findViewById(R.id.button83);
        Button noTastedButton = (Button) findViewById(R.id.button76);
        Button tastedButton = (Button) findViewById(R.id.button77);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        noTastedButton.setOnClickListener(this);
        tastedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button87:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button86:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button85:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button84:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button83:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
            case R.id.button76:
                Intent notTastedIntent = new Intent(getApplicationContext(), GuideDetailActivity.class);
                notTastedIntent.putExtra("tasted", false);
                startActivity(notTastedIntent);
                break;
            case R.id.button77:
                Intent tastedIntent = new Intent(getApplicationContext(), GuideDetailActivity.class);
                tastedIntent.putExtra("tasted", true);
                startActivity(tastedIntent);
                break;
        }
    }
}
