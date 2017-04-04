package com.example.iwakiri.sakezukan_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean isDataExist;
    Boolean isFound;
    Boolean isTasted;
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
        Button searchButton = (Button) findViewById(R.id.button);
        homeButton.setOnClickListener(this);
        guideButton.setOnClickListener(this);
        tasteButton.setOnClickListener(this);
        findButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edittext);
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
                isTasted = false;
                break;
            default:
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
            case R.id.button:
                defineData();
                searchData();
                break;
        }
    }
}
