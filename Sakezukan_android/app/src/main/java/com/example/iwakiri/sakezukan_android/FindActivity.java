package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindActivity extends AppCompatActivity {

    Boolean isExist;
    Boolean isFound;
    Button button;
    EditText editText;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

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
}
