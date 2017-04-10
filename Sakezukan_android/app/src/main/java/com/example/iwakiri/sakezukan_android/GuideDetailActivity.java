package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuideDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        Button homeButton = (Button) findViewById(R.id.button82);
        Button guideButton = (Button) findViewById(R.id.button81);
        Button tasteButton = (Button) findViewById(R.id.button80);
        Button findButton = (Button) findViewById(R.id.button79);
        Button helpButton = (Button) findViewById(R.id.button78);
        Button goTasteButton = (Button) findViewById(R.id.button83);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        goTasteButton.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.textView20);

        Intent intent = getIntent();
        Boolean isTasted = intent.getBooleanExtra("tasted", false);
        if (isTasted) {
            goTasteButton.setVisibility(View.GONE);
            textView.setText("詳細情報解放時");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button82:
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.button81:
                Intent guideIntent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(guideIntent);
                finish();
                break;
            case R.id.button80:
            case R.id.button83:
                Intent tasteIntent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(tasteIntent);
                finish();
                break;
            case R.id.button79:
                Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(findIntent);
                finish();
                break;
            case R.id.button78:
                Intent helpIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(helpIntent);
                finish();
                break;
        }
    }
}
