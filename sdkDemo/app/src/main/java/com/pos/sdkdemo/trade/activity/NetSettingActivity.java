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

public class NetSettingActivity extends Activity implements View.OnClickListener {

    private EditText ed_ip,ed_port;
    private TextView title,menu;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_setting);
        initView();
        initData();
    }

    private void initView(){
        ed_ip = findViewById(R.id.edit_ip);
        ed_port = findViewById(R.id.edit_port);
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
        ed_ip.setText(Config.getIp(this));
        ed_port.setText(Config.getPort(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                Config.IP= ed_ip.getText().toString().trim();
                Config.PORT = ed_port.getText().toString().trim();
                Config.setIp(this,Config.IP);
                Config.setPort(this,Config.PORT);
                ToastUtils.shortShow("save success");
            case R.id.back:
                finish();
                break;
        }
    }
}
