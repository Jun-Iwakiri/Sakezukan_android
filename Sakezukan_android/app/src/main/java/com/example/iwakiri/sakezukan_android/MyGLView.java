package com.example.iwakiri.sakezukan_android;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by iwakiri on 2017/05/29.
 */

class MyGLView extends GLSurfaceView {

    MyRenderer myRenderer;

    public MyGLView(Context context) {
        super(context);
        myRenderer = new MyRenderer();
        setRenderer(myRenderer);
    }
}
