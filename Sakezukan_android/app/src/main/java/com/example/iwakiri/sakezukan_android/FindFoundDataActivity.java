package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindFoundDataActivity extends AppCompatActivity {

    Boolean isTasted;
    Button button;
    EditText editText;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_found_data);

        Button searchButton = (Button) findViewById(R.id.button30);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                defineData();
//                searchData();
            }
        });

        Intent intent = getIntent();
        isTasted = intent.getBooleanExtra("Tasted", false);
        if (isTasted) {
            Toast.makeText(getApplicationContext(), "既飲酒", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "未飲酒", Toast.LENGTH_LONG).show();
        }
    }
}
