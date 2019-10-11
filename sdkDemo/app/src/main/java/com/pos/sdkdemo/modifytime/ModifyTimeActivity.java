package com.pos.sdkdemo.modifytime;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.base.DemoApplication;

/**
 * M1卡 (M1 Card)
 */
public class ModifyTimeActivity extends BaseActivity {

    private static final String action = "com.sfexpress.setSystemTime";
    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_modifytime, null, false);
    }

    @Override
    protected void onInitView() {
    }

    public void modifyTime(View view)
    {
        Intent intent = new Intent(action);
        long time = System.currentTimeMillis() + 84000;
        intent.putExtra("system_time", time);
        sendBroadcast(intent);
        LOGD("modify time complete!");
    }
}
