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

public class eclipseprojects extends BaseActivityGuide {
    @Override
    protected void PrepareImgs() {
        mImgBgRes = new int[]{R.drawable.eclipse_step1, R.drawable.eclipse_step2, R.drawable.eclipse_step3,
                R.drawable.eclipse_step4, R.drawable.eclipse_step5, R.drawable.eclipse_step6,
                R.drawable.eclipse_step7};
    }

    @Override
    protected void onFinishGuide() {
        LogUtil.i(getClass(), "onFinishGuide---->");
        if (!GlobalData.getInstance().ifEntransActivityExist)
        {
            finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            startActivity(new Intent(eclipseprojects.this,MenuActivity.class));
        }
    }
}
