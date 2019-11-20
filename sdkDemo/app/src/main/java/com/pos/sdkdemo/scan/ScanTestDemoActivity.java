package com.pos.sdkdemo.scan;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.basewin.aidl.OnBarcodeCallBack;
import com.basewin.define.ScanType;
import com.basewin.services.ServiceManager;
import com.basewin.utils.AppUtil;
import com.pos.sdkdemo.R;
public class ScanTestDemoActivity extends Activity {

    private TextView tv_scan_result;
    private EditText tv_scan_timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_test);
        setTitle("scan code");
        initView();
    }

    public void openZxing(View v) {
        AppUtil.setProp(ScanType.scansupport, "true");
        try {
//            ServiceManager.getInstence().getScan().setContinueScanTimeout(1000);
            ServiceManager.getInstence().getScan().setIfneedInvert(true);
            ServiceManager.getInstence().getScan().startScan(getTimeOut(), new OnBarcodeCallBack() {
                @Override
                public void onScanResult(String s) {
                    setShow("Result" + s);
                }

                @Override
                public void onFinish(int code,String msg) {
                    setShow("Scan code failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openZbar(View v) {
        try {
            ServiceManager.getInstence().getScan().startScanZbar(getTimeOut(), new OnBarcodeCallBack() {
                @Override
                public void onScanResult(String s){
                    setShow("Result" + s);
                }

                @Override
                public void onFinish(int code,String msg) {
                    setShow("Scan code failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);
        tv_scan_timeout = (EditText) findViewById(R.id.tv_scan_timeout);
    }

    private void setShow(String msg) {
        tv_scan_result.setText(msg);
    }

    private int getTimeOut() {
        String time = tv_scan_timeout.getText().toString().trim();
        int timeout = 0;
        if (TextUtils.isEmpty(time)) {
            timeout = 10;
        } else {
            try {
                timeout = Integer.parseInt(time);
            } catch (Exception e) {
                timeout = 10;
                System.out.println(e.getMessage());
            }
        }
        return timeout;
    }
}
