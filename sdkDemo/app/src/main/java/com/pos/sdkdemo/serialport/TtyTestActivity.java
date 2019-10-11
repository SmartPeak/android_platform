package com.pos.sdkdemo.serialport;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.utils.BCDHelper;

/**
 * Created by lyw on 2016/12/6.
 * setial port function:
 * Exchange data through serial port between computer and pos ,or between m-pos and child-pos.
 */

public class TtyTestActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private static final int READ_BUF_LENGTH = 1024;
    private static final String TEST_COMMAND = "abcdefghijklmn";
    private static final int MSG_SHOW_TEXT = 1;
    private TextView tv_serialPortMsg;
    private Button button_send_command;

    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_tty, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }


    private void initView() {
        tv_serialPortMsg = (TextView) findViewById(R.id.tv_serialPortMsg);
        button_send_command = (Button) findViewById(R.id.button_send_command);
        button_send_command.setOnClickListener(this);
        deviceOpen();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_TEXT:
                    appendText((String) msg.obj);
                    break;
            }
        }
    };

    /**
     * open tty
     * init tty
     */
    private void deviceOpen() {
        try {
            boolean open = ServiceManager.getInstence().getSerialPort().open();
            LOGD("deviceOpen:" + open );
            LOGD("ttyOpen");
            ServiceManager.getInstence().getSerialPort().init(9600, (byte)8, (byte)0, (byte)0, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * colse tty
     * @param
     */
    private void deviceClose() {
        try {
            boolean close = ServiceManager.getInstence().getSerialPort().close();
            LOGD("ttyClose");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test of sending msg
     * @param
     */
    private void doDeviceTest() {
        if (sendCommand(TEST_COMMAND.getBytes()))
        {
            try {
                byte[] recv = ServiceManager.getInstence().getSerialPort().readData(10*1000);
                if (recv.length > 0)
                {
                    LOGD("recv num:"+recv.length);
                    LOGD(BCDHelper.bcdToString(recv));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * send msg
     * @param
     * @param cmd
     * @return
     */
    private boolean sendCommand(byte[] cmd) {
        boolean writeRet = false;

        if (cmd != null) {
            try {
                writeRet = ServiceManager.getInstence().getSerialPort().sendData(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (writeRet == true) {
                LOGD("Send success :"+ BCDHelper.bcdToString(cmd));
            } else {
                LOGD("Send failed ");
            }
        } else {
            LOGD("sendCommand failed without cmd");
        }
        LOGD("sendCommand");
        return writeRet;
    }

    private void appendText(String text) {
        tv_serialPortMsg.append(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_command:
                //do test
                doDeviceTest();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // colse ttyDevice
        deviceClose();
    }

}
