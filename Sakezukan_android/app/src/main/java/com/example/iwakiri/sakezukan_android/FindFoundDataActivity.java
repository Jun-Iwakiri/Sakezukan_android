package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindFoundDataActivity extends AppCompatActivity {

    Boolean isDataExist;
    Boolean isFound;
    Boolean isTasted;
    Button searchButton;
    Button tasteButton;
    EditText editText;
    TextView textView;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_found_data);

        textView = (TextView) findViewById(R.id.textView2);

        editText = (EditText) findViewById(R.id.edittext2);
        searchButton = (Button) findViewById(R.id.button29);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defineData();
                searchData();
            }
        });
        tasteButton = (Button) findViewById(R.id.button30);
        tasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TasteActivity.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        isTasted = intent.getBooleanExtra("Tasted", false);
        if (isTasted) {
            Toast.makeText(getApplicationContext(), "既飲酒", Toast.LENGTH_LONG).show();
            tasteButton.setVisibility(View.GONE);
        } else {
            Toast.makeText(getApplicationContext(), "未飲酒", Toast.LENGTH_LONG).show();
            textView.setText("以前見つけたお酒です。飲んでみましたか？");
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
                //既発見既飲酒
                isDataExist = true;
                isFound = true;
                isTasted = true;
                break;
            case "1":
                //既発見未飲酒
                isDataExist = true;
                isFound = true;
                isTasted = false;
                break;
            case "2":
                //未発見
                isDataExist = true;
                isFound = false;
                break;
            case "3":
                //非該当
                isDataExist = false;
        }
    }

    private void searchData() {
        if (!str.isEmpty()) {
            if (isDataExist) {
                if (isFound) {
                    if (isTasted) {
                        //既に見つけていて飲んでいる
                        Intent intent = new Intent(this, FindFoundDataActivity.class);
                        intent.putExtra("Tasted", isTasted);
                        startActivity(intent);
                    } else {
                        //既に見つけているが飲んでいない
                        Intent intent = new Intent(this, FindFoundDataActivity.class);
                        intent.putExtra("Tasted", isTasted);
                        startActivity(intent);
                    }
                } else {
                    //始めて見つけた
                    Intent intent = new Intent(this, FindNewDataActivity.class);
                    startActivity(intent);
                }
            } else {
                //マスターデータそのものがない
                Intent intent = new Intent(this, FindNoDataActivity.class);
                startActivity(intent);
            }
        } else {
            //適切な文字列を入れていない
            Toast.makeText(this, "不当な文字列", Toast.LENGTH_SHORT).show();
        }
    }
}
