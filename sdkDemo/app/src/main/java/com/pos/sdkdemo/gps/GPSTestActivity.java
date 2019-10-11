package com.pos.sdkdemo.gps;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basewin.aidl.OnGpsCallBack;
import com.basewin.define.GpsSource;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * Created by lyw on 2016/12/7.
 * get the location of the Pos-Divice
 */

public class GPSTestActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_gps;
    private Button btn_gps;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_gps, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }

    private void initView() {
        tv_gps = (TextView) findViewById(R.id.tv_gps);
        btn_gps = (Button) findViewById(R.id.btn_startgps);
        btn_gps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startgps:
                getGPSData();
                break;
        }
    }

    /**
     * get the gps data from net ande set the msg to the activity
     */
    public void getGPSData() {
        try {
            //get the gps data from net
            LOGD("start GPS");
            ServiceManager.getInstence().getGPS().startGPS(new OnGpsCallBack() {

                @Override
                public void onGetGps(GpsSource gps) {
                    LOGD("start GPS Success");
                    LOGD("the GPS data:" + gps.toString());
                    tv_gps.setText("GPS DATA:"+"\n"+ gps.toString());
                    // 获取完成之后需要关闭GPS服务，不然的话会一直接收到GPS数据
                    //After the completion of acquisition need to close the GPS service, otherwise would have been to GPS data receiving
                    try {
                        //close the GPS Service
                        ServiceManager.getInstence().getGPS().closeGPS();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String msg) {
                    LOGD("start GPS failed");
                    LOGD(" GPS error msg:" + msg);
                }
            });
        } catch (RuntimeException e1){
            e1.printStackTrace();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
