package com.pos.sdkdemo.trade.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.net8583.Auth8583;
import com.pos.sdkdemo.trade.net8583.SaleCancel8583;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.ToastUtils;

public class InputDateActivity extends BaseInputActivity {

    private int transType=-1;

    @Override
    protected void initView() {
        maxLength=4;
        inputType=3;
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        transType= (int) GlobleData.getInstance().datas.get(Config.TRANS_TYPE);
        tv_hint.setText(R.string.date_hint);
        title.setText(TransTypeConstant.getNameByTransType(this,transType));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.confirm:
                String date = tv_input.getText().toString();
                if(PosUtil.isValidDate(date)){
                    GlobleData.getInstance().datas.put("oldDate",date);
                    Intent intent = new Intent(InputDateActivity.this,InputAuthCodeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtils.shortShow(R.string.month_date_format_error);
                }
                break;
        }
    }
}
