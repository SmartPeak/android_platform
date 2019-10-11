package com.pos.sdkdemo.disablekeycode;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.pos.sdkdemo.R;

/**
 * Created by lyw on 2016/12/9.
 * disabled the button
 */

public class DisableKeycodeActivity extends com.pos.sdkdemo.base.BaseActivity implements View.OnClickListener {
    private Button btn_disableKeycode;
    private Context mContext = DisableKeycodeActivity.this;
    private CheckBox cb_homeKey;
    private int state;
    private Spinner sp_selectKeycode;
    private int keycode;

    /**
     * menu
     */
    private static final String DISABLE_RECENT_APPS = "android.intent.action.DISABLE_RECENT_APPS";


    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_disable_keycode, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }

    private void initView() {
        btn_disableKeycode = (Button) findViewById(R.id.btn_disableKeycode);
        btn_disableKeycode.setOnClickListener(this);
        state = 0;
        /**
         * change the keyCode state
         */
        cb_homeKey = (CheckBox) findViewById(R.id.cb_disableKey);
        LOGD("key default state: function");
        cb_homeKey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    state = 1;
                    LOGD("set the key state: unfunction");
                } else {
                    LOGD("set the key state: function");
                    state = 0;
                }

            }

        });

        /**
         * select key
         */
        sp_selectKeycode = (Spinner) findViewById(R.id.sp_selectKeycode);
        sp_selectKeycode.setSelection(0);
        sp_selectKeycode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        keycode=KeyEvent.KEYCODE_HOME;
                        LOGD("select the HOME_KEY");
                        break;
                    case 1:
                        keycode=KeyEvent.KEYCODE_BACK;
                        LOGD("select the BACK_KEY");
                        break;
                    case 2:
                        keycode=KeyEvent.KEYCODE_MENU;
                        LOGD("select the MENU_KEY");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                keycode=KeyEvent.KEYCODE_HOME;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_disableKeycode:
                setBtn_disableKeycode();
                break;
        }
    }

    /**
     * change the  keyCode
     */
    public void setBtn_disableKeycode() {
        LOGD("start set key");
        if (keycode != KeyEvent.KEYCODE_MENU) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DISABLE_KEYCODE");
            intent.putExtra("keycode", keycode);
            intent.putExtra("state", state);
            mContext.sendBroadcast(intent);
        }
        else
        {
            Intent in = new Intent(DISABLE_RECENT_APPS);
            if (state == 1)
                in.putExtra("disable_recent", true);
            else
                in.putExtra("disable_recent", false);
            sendBroadcast(in);
        }
        LOGD("set the key success");
    }

}
