package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.utils.ToastUtils;

public class MerchantConfigActivity extends Activity implements View.OnClickListener {
    private EditText merchants,terminal;
    private TextView title,menu;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_config);
        initView();
        initData();
    }

    private void initView(){
        merchants = findViewById(R.id.edit_merchants);
        terminal = findViewById(R.id.edit_terminal);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        menu = findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        menu.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initData(){
        title.setText("Net Setting");
        menu.setText("Save");
        merchants.setText(Config.getMerchantNo(this));
        terminal.setText(Config.getTerminalNo(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                Config.IP= merchants.getText().toString().trim();
                Config.PORT = terminal.getText().toString().trim();
                Config.setIp(this,Config.MerchantNo);
                Config.setPort(this,Config.TerminalNo);
                ToastUtils.shortShow("save success");
            case R.id.back:
                finish();
                break;
        }
    }
}
