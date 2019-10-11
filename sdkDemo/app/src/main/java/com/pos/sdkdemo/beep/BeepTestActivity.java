package com.pos.sdkdemo.beep;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by lyw on 2016/12/7.
 * beeper beeps by the way you set ,frequency and time
 */

public class BeepTestActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_staerBeep;
    private EditText et_time,et_voice,et_freq;
    private String sTime,sVoice,sFreq;
    private int time,voice,freq;

    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_beep, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }

    private void initView() {
        btn_staerBeep = (Button) findViewById(R.id.btn_staerBeep);
        btn_staerBeep.setOnClickListener(this);
        et_time = (EditText) findViewById(R.id.et_time);
        et_freq = (EditText) findViewById(R.id.et_freq);
        et_voice = (EditText) findViewById(R.id.et_voice);
        optimizSoftKeyBoard(et_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_staerBeep:
                startBeeper();
                break;
        }
    }

    /**
     * start beeper
     */
    public void startBeeper() {
        try {
            Log.d(TAG, "start beeper");
            LOGD("start beepers now");
            getValue();
            ServiceManager.getInstence().getBeeper().beep(time, freq, voice);
            LOGD("beepers success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /**
     * get the value of editIpnut
     */
    private void getValue() {
        // validate
        sTime = et_time.getText().toString().trim();
        if (TextUtils.isEmpty(sTime)) {
           time=100;//default value is 100ms
        }else {
            time=Integer.valueOf(sTime);
        }

        sFreq = et_freq.getText().toString().trim();
        if (TextUtils.isEmpty(sFreq)) {
          freq=500;//default value is 500
        }else {
            freq = Integer.valueOf(sFreq);
        }

        sVoice = et_voice.getText().toString().trim();
        if (TextUtils.isEmpty(sVoice)) {
           voice=1;//default value is 1
        }else {
            voice = Integer.valueOf(sVoice);
        }

        // TODO validate success, do something


    }
}
