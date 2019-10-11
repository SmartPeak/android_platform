package com.pos.sdkdemo.led;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/1.
 * show the Led how to work
 * if "on(Ms)" is blank or "off(Ms)" is blank ,the led  lighted  aways
 *
 */

public class LedTestActivity extends BaseActivity {


    private Spinner sp1;
    private EditText et_onMs, et_offMs;
    private String sOnMs, sOffMs;
    private int onMs,offMs;
    private int led;
    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_led, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }

    public void open(View v) {
        openLed();
    }

    public void off(View v) {
        LedUtil.close(led);
        LOGD("turn off led success" +
                "");
    }
    public void openScanLed(View v) {
        try {
			ServiceManager.getInstence().getLed().openScannerLed();
	        LOGD("open scanner led");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void closeScanLed(View v) {
        try {
			ServiceManager.getInstence().getLed().closeScannerLed();
			LOGD("close scanner led");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }   


    private void initView() {
        et_onMs = (EditText) findViewById(R.id.et_onMs);
        et_offMs = (EditText) findViewById(R.id.et_offMs);
        optimizSoftKeyBoard(et_onMs);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                String[] colors = getResources().getStringArray(R.array.color);
                if ("green".equals(colors[pos])) {
                    led = BwLedType.LED_GREEN;
                } else if ("blue".equals(colors[pos])) {
                    led = BwLedType.LED_BLUE;
                } else if ("red".equals(colors[pos])) {
                    led = BwLedType.LED_RED;
                } else if ("yellow".equals(colors[pos])) {
                    led = BwLedType.LED_YELLOW;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                led = BwLedType.LED_GREEN;
            }
        });
    }

    private void openLed() {
        sOnMs = et_onMs.getText().toString().trim();
        sOffMs = et_offMs.getText().toString().trim();
        if (sOnMs.isEmpty() || sOffMs.isEmpty()) {
            LOGD("start trun LED");
            LedUtil.open(led);
            LOGD("trun on LED successful");
        }else {
            LOGD("start trun on LED");
            onMs = Integer.valueOf(sOnMs);

            switch (led){
                case BwLedType.LED_BLUE:
                    LOGD("led: blue");
                    break;
                case BwLedType.LED_RED:
                    LOGD("led: red");
                    break;
                case BwLedType.LED_YELLOW:
                    LOGD("led: yellow");
                    break;
                case BwLedType.LED_GREEN:
                    LOGD("led: green");
                    break;
            }
            offMs = Integer.valueOf(sOffMs);
            LOGD("On:"+sOnMs+" ms");
            LOGD("Off:"+sOffMs+" ms");
            LedUtil.open(led,onMs,offMs);
            LOGD("turn on LED successful");

        }

        // TODO validate success, do somethin
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LedUtil.closeAll();


    }
}
