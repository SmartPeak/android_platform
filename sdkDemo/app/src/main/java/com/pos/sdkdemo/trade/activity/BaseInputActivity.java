package com.pos.sdkdemo.trade.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.interfaces.OnNumKeyListener;
import com.pos.sdkdemo.pboc.pinpad.StringHelper;
import com.pos.sdkdemo.widgets.KeyBoardView;

public class BaseInputActivity extends Activity implements View.OnClickListener {

    public TextView tv_hint, tv_input, title;
    private FrameLayout fl_keyboard;
    private KeyBoardView keyBoardView;
    public Button confirm;
    public ImageView back;

    public int inputType=1;//1:money , 2:password, 3:normal
    public int maxLength = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_input);
        initView();
        initData();
    }

    protected void initView() {
        tv_hint = (TextView) findViewById(R.id.hint);
        tv_input = (TextView) findViewById(R.id.input);
        fl_keyboard = (FrameLayout) findViewById(R.id.fl_keyboard);
        confirm = (Button) findViewById(R.id.confirm);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);

        fl_keyboard.removeAllViews();
        keyBoardView = new KeyBoardView(this);
        View view = keyBoardView.getKeyBoardView();
        fl_keyboard.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        keyBoardView.setOnNumKeyListener(new KeyBoardListener());
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);

        if(inputType==1){
            keyBoardView.num00.setText("00");
            tv_input.setInputType(InputType.TYPE_CLASS_TEXT);
            tv_input.setText("0.00");
        }else if(inputType==2){
            keyBoardView.num00.setText(".");
            tv_input.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }else {
            keyBoardView.num00.setText(".");
            tv_input.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        tv_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0||s.toString().trim().equals("0.00")){
                    confirm.setEnabled(false);
                }else {
                    confirm.setEnabled(true);
                }
            }
        });

    }

    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private class KeyBoardListener implements OnNumKeyListener {
        @Override
        public void onClick(View view) {
            StringBuilder builder = new StringBuilder();
            builder.append(tv_input.getText());

            switch (view.getId()) {
                case R.id.num00:
                    if (inputType==1){
                        builder.append(00);
                    }else if(inputType==2){
                        break;
                    }else {
                        builder.append(".");
                    }
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
                    if(builder.length()==0){
                        return;
                    }
                    builder = builder.delete(builder.length() - 1, builder.length());
                    break;
                default:
                    break;
            }

            if(builder.length()>maxLength){
                return;
            }

            if(inputType==1){
                tv_input.setText(StringHelper.changeAmout(builder.toString()));
            }else {
                tv_input.setText(builder.toString());
            }
        }

    }
}
