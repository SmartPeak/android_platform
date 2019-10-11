package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.interfaces.OnNumKeyListener;
import com.pos.sdkdemo.pboc.pinpad.StringHelper;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.net8583.Auth8583;
import com.pos.sdkdemo.trade.net8583.AuthCancel8583;
import com.pos.sdkdemo.trade.net8583.AuthComplete8583;
import com.pos.sdkdemo.trade.net8583.AuthCompleteCancel8583;
import com.pos.sdkdemo.trade.net8583.Sale8583;
import com.pos.sdkdemo.utils.ToastUtils;
import com.pos.sdkdemo.widgets.KeyBoardView;

public class InputMoneyActivity extends Activity implements View.OnClickListener {
    private TextView amount;
    private FrameLayout fl_keyboard;
    private KeyBoardView keyBoardView;
    private Button confirm;
    private int transType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_money);
        initView();
        initData();
    }

    private void initView() {
        fl_keyboard = (FrameLayout) findViewById(R.id.fl_keyboard);
        fl_keyboard.removeAllViews();
        keyBoardView = new KeyBoardView(this);
        keyBoardView.getKeyBoardView();
        fl_keyboard.addView(keyBoardView.getKeyBoardView(), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        keyBoardView.setOnNumKeyListener(new KeyBoardListener());
        amount = (TextView) findViewById(R.id.amount);
        confirm = (Button) findViewById(R.id.confirm);
    }

    private void initData() {
        transType = (int) GlobleData.getInstance().datas.get(Config.TRANS_TYPE);
        confirm.setOnClickListener(this);
        switch (transType) {
            case TransTypeConstant.ACTION_SALE:
                GlobleData.getInstance().setSend8583Package(new Sale8583());
                break;
            case TransTypeConstant.ACTION_AUTH:
                GlobleData.getInstance().setSend8583Package(new Auth8583());
                break;
            case TransTypeConstant.ACTION_CANCEL:
                GlobleData.getInstance().setSend8583Package(new AuthCancel8583());
                break;
            case TransTypeConstant.ACTION_AUTH_COMPLETE:
                GlobleData.getInstance().setSend8583Package(new AuthComplete8583());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (amount.getText().toString().trim().equals("0.00")) {
                    ToastUtils.shortShow(R.string.input_money);
                } else {
                    GlobleData.getInstance().datas.put(Config.AMOUNT, amount.getText().toString().trim());
                    Intent intent = new Intent(InputMoneyActivity.this, FindCardActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private class KeyBoardListener implements OnNumKeyListener {
        @Override
        public void onClick(View view) {
            StringBuilder builder = new StringBuilder();

            builder.append(amount.getText());
            switch (view.getId()) {
                case R.id.num00:
                    builder.append(00);
                    break;
                case R.id.num0:
                    builder.append(0);
                    break;
                case R.id.num1:
                    builder.append(1);
                    break;
                case R.id.num2:
                    builder.append(2);
                    break;
                case R.id.num3:
                    builder.append(3);
                    break;
                case R.id.num4:
                    builder.append(4);
                    break;
                case R.id.num5:
                    builder.append(5);
                    break;
                case R.id.num6:
                    builder.append(6);
                    break;
                case R.id.num7:
                    builder.append(7);
                    break;
                case R.id.num8:
                    builder.append(8);
                    break;
                case R.id.num9:
                    builder.append(9);
                    break;
                case R.id.num_back:
                    builder = builder.delete(builder.length() - 1, builder.length());
                    break;
                default:
                    break;
            }
            amount.setText(StringHelper.changeAmout(builder.toString()));
        }

    }
}
