package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.pboc.pinpad.StringHelper;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.Dao.db.TransactionDataDB;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.bean.TransactionData;
import com.pos.sdkdemo.utils.DialogUtil;
import com.pos.sdkdemo.utils.PosUtil;

public class CheckTradeInfoActivity extends Activity implements View.OnClickListener {

    private int transType;
    private String originalTrace="";
    private TextView name,cardNum,amount,reference,title;
    private Button confirm;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_trade_info);
        initView();
        initData();
    }

    private void initView(){
        name= (TextView) findViewById(R.id.trans_type);
        cardNum= (TextView) findViewById(R.id.card_number);
        amount= (TextView) findViewById(R.id.amount);
        reference= (TextView) findViewById(R.id.reference);
        confirm= (Button) findViewById(R.id.confirm);
        title= (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    private void initData(){
        transType= (int) GlobleData.getInstance().datas.get(Config.TRANS_TYPE);
        originalTrace= (String) GlobleData.getInstance().datas.get("oldTrace");
        title.setText(TransTypeConstant.getNameByTransType(this,transType));
        selectTransByTrace();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                Intent intent = new Intent(CheckTradeInfoActivity.this,FindCardActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void selectTransByTrace(){
        TransactionData data = null;
        try {
            data = TransactionDataDB.selectByTrace(originalTrace);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(data==null){
            confirm.setEnabled(false);
            DialogUtil.showDialog(this, getString(R.string.no_trace), new DialogUtil.OnClickListener() {
                @Override
                public void onConfirm() {
                    finish();
                }

                @Override
                public void onCancel() {
                    finish();
                }
            });
            return;
        }

        name.setText(TransTypeConstant.getNameByTransType(this,data.getTransType()));
        cardNum.setText(PosUtil.hiddenCardNum(data.getPan()));
        amount.setText(PosUtil.centToYuan(data.getAmount()));
        reference.setText(data.getReferenceNo());
        GlobleData.getInstance().datas.put(Config.AMOUNT,amount.getText().toString());
    }
}
