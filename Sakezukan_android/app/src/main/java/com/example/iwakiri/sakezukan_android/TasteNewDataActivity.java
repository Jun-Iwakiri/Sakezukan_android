package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TasteNewDataActivity extends AppCompatActivity implements View.OnClickListener {

    boolean isFound;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste_new_data);

        Button homeButton = (Button) findViewById(R.id.button58);
        Button guideButton = (Button) findViewById(R.id.button57);
        Button tasteButton = (Button) findViewById(R.id.button56);
        Button findButton = (Button) findViewById(R.id.button55);
        Button helpButton = (Button) findViewById(R.id.button54);
        Button registrationButon = (Button) findViewById(R.id.button70);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        registrationButon.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.textView15);
        Intent intent = getIntent();
        isFound = intent.getBooleanExtra("Found", false);
        if (isFound) {
            Toast.makeText(getApplicationContext(), "既発見未飲酒", Toast.LENGTH_SHORT).show();
            textView.setText("既に見つけていたお酒です");
        } else {
            Toast.makeText(getApplicationContext(), "未発見未飲酒", Toast.LENGTH_SHORT).show();
            textView.setText("新発見情報の表示");
        }

        str = intent.getStringExtra("Requested");
        textView.setText(str);
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
            case R.id.button70:
                Intent registrationIntent = new Intent(getApplicationContext(), TasteRegisteredDataActivity.class);
                startActivity(registrationIntent);
        }
    }
}
