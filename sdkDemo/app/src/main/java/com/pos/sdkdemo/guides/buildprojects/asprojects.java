package com.pos.sdkdemo.guides.buildprojects;

import android.content.Intent;

import com.basewin.log.LogUtil;
import com.pos.sdkdemo.MenuActivity;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivityGuide;
import com.pos.sdkdemo.utils.GlobalData;

/**
 * Created by liudy on 2017/3/29.
 */

public class asprojects extends BaseActivityGuide{


    @Override
    protected void PrepareImgs() {
        mImgBgRes = new int[] { R.drawable.androidstudio_step1, R.drawable.androidstudio_step2, R.drawable.androidstudio_step3,
                R.drawable.androidstudio_step4, R.drawable.androidstudio_step5, R.drawable.androidstudio_step6,
                R.drawable.androidstudio_step7, R.drawable.androidstudio_step8};
    }

    @Override
    protected void onFinishGuide() {
        LogUtil.i(getClass(), "onFinishGuide---->");
        if (!GlobalData.getInstance().ifEntransActivityExist)
        {
            finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            startActivity(new Intent(asprojects.this,MenuActivity.class));
        }
    }
}
