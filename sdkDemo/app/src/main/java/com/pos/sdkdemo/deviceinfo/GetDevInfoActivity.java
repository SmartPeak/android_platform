package com.pos.sdkdemo.deviceinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * Created by huyang on 2016/12/5.
 * get the device information module
 * 获取设备信息模块
 */

public class GetDevInfoActivity extends BaseActivity {
    private Button get_info;
    private TextView device_info;
    private StringBuffer sb;
    private ScrollView scrollView;

    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_getdeviceinfo, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }

    private void initView(){
        device_info = (TextView) findViewById(R.id.deviceinfo);
        get_info = (Button) findViewById(R.id.getinfo);
        get_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });
    }

    private void showInfo() {
        sb = new StringBuffer("");
        try {
            sb.append(getString(R.string.get_device_info) + "\n");
            //get the serial number
            //获取序列号
            sb.append("getSN=" + ServiceManager.getInstence().getDeviceinfo().getSN() + "\t\n");
            //get the vendor name
            //获取商户名
            sb.append("getVName=" + ServiceManager.getInstence().getDeviceinfo().getVName() + "\t\n");
            //get the verdor ID
            //获取商户ID
            sb.append("getVID = " + ServiceManager.getInstence().getDeviceinfo().getVID() + "\t\n");
            //get customer-defined key serial number
            sb.append("getKSN = " + ServiceManager.getInstence().getDeviceinfo().getKSN() + "\t\n");
            //system version
            //获取系统AP版本
            sb.append("system version = "+ServiceManager.getInstence().getDeviceinfo().getSystemVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        device_info.setText(sb.toString());
    }
}
