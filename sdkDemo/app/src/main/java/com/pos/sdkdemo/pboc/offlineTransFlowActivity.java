package com.pos.sdkdemo.pboc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;


import com.pos.sdkdemo.R;

public class offlineTransFlowActivity extends Activity {
    private ImageView ima_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_flow);
        ima_bg = (ImageView) findViewById(R.id.ima_bg);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.offline_trans);
        ima_bg.setImageBitmap(bitmap);
    }
}
