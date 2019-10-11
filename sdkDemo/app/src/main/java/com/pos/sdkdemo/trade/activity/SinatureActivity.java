package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.basewin.define.OutputPBOCAAData;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.interfac.OnAARequestOnlineProcess;

public class SinatureActivity extends Activity implements OnAARequestOnlineProcess {
public static final String TAG="feeling";
    private Button button;
    private String amount="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinature);
        initView();
        initData();
    }

    private void initView(){
        button= (Button) findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ServiceManager.getInstence().getPboc().comfirmSinature();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData(){
        amount = (String) GlobleData.getInstance().datas.get(Config.AMOUNT);
        GlobleData.getInstance().setOnAARequestOnlineProcess(this);
    }

    @Override
    public void AARequestOnlineProcess(Intent data) {
        Log.e(TAG, "AARequestOnlineProcess: ");
        OutputPBOCAAData out = new OutputPBOCAAData(data);
        GlobleData.getInstance().card.icAAData = out;
        Intent intent = new Intent(SinatureActivity.this,OnlineActivity.class);
        intent.putExtra("amount",amount);
        startActivity(intent);
        finish();
    }
}
