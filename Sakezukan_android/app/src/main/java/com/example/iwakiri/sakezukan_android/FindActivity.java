package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindActivity extends AppCompatActivity {

    Boolean isExist = true;
    Boolean isFound = true;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData();
            }
        });
    }

    private void searchData() {
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
    }
}
