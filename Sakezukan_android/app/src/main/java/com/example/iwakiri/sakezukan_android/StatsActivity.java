package com.example.iwakiri.sakezukan_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StatsActivity extends AppCompatActivity {

    MyGLView myGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLView = new MyGLView(this);
        setContentView(myGLView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }
}
