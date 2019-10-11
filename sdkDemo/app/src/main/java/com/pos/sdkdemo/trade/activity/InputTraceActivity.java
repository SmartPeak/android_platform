package com.pos.sdkdemo.trade.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.net8583.AuthCompleteCancel8583;
import com.pos.sdkdemo.trade.net8583.SaleCancel8583;
import com.pos.sdkdemo.utils.PosUtil;

public class InputTraceActivity extends BaseInputActivity {
    private int transType=-1;
    @Override
    public void initData() {
        transType= (int) GlobleData.getInstance().datas.get(Config.TRANS_TYPE);
        tv_hint.setText(getString(R.string.trace_hint));
        title.setText(TransTypeConstant.getNameByTransType(this,transType));
        init8583();
    }

    @Override
    protected void initView() {
        inputType=3;
        maxLength=6;
        super.initView();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.confirm:
                String originalTrace = tv_input.getText().toString();
                GlobleData.getInstance().datas.put("oldTrace",PosUtil.numToStr6(originalTrace));
                Intent intent = new Intent(InputTraceActivity.this,CheckTradeInfoActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    private void init8583(){
        switch (transType){
            case TransTypeConstant.ACTION_VOID:
                SaleCancel8583 saleCancel8583 = new SaleCancel8583();
                GlobleData.getInstance().setSend8583Package(saleCancel8583);
                break;
            case TransTypeConstant.ACTION_COMPLETE_VOID:
                AuthCompleteCancel8583 authCompleteCancel8583 = new AuthCompleteCancel8583();
                GlobleData.getInstance().setSend8583Package(authCompleteCancel8583);
                break;
        }
    }
}
