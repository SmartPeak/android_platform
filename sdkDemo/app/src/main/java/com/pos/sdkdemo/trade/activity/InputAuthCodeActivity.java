package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.utils.ToastUtils;
import com.pos.sdkdemo.widgets.KeyBoardView;

public class InputAuthCodeActivity extends Activity implements View.OnClickListener {

    private TextView tv_hint, title;
    private EditText tv_input;
    private Button confirm;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_auth_code);
        initView();
        initData();
    }

    protected void initView() {
        tv_hint = (TextView) findViewById(R.id.hint);
        tv_input = (EditText) findViewById(R.id.input);
        confirm = (Button) findViewById(R.id.confirm);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(this);
        confirm.setOnClickListener(this);

        tv_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    confirm.setEnabled(true);
                }else {
                    confirm.setEnabled(false);
                }
            }
        });
    }

    protected void initData() {
        tv_hint.setText(R.string.auth_code_hint);
        int transType = (int) GlobleData.getInstance().datas.get(Config.TRANS_TYPE);
        title.setText(TransTypeConstant.getNameByTransType(this,transType));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                String authCode = tv_input.getText().toString();
                if (!TextUtils.isEmpty(authCode) && authCode.length() == 6) {
                    GlobleData.getInstance().datas.put("authCode",authCode);
                    Intent intent = new Intent(InputAuthCodeActivity.this,InputMoneyActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtils.shortShow(getString(R.string.Please_enter_authorization));
                }
                break;
            default:
                break;
        }
    }
}
