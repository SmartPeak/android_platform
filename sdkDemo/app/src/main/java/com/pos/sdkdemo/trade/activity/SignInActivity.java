package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.basewin.commu.Commu;
import com.basewin.commu.define.CommuListener;
import com.basewin.commu.define.CommuParams;
import com.basewin.commu.define.CommuStatus;
import com.basewin.commu.define.CommuType;
import com.basewin.define.InputPBOCOnlineData;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BytesUtil;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.DialogUtil;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.ToastUtils;
import com.pos.sdkdemo.utils.TradeEncUtil;

import java.io.IOException;
import java.util.Locale;

public class SignInActivity extends Activity {
    public static final String TAG = "feeling";
    public static final int COMMU_FINISH = 0x00;
    private Iso8583Manager sendIso8583 = null;
    private Iso8583Manager receiveIso8583 = null;
    private CommuListener mCommuListener = null;
    private Commu commu = null;
    private byte[] sendData = null;
    private Dialog loadingDialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                commu.dataCommu(SignInActivity.this,sendData,mCommuListener);
            }
        }).start();

    }

    private void initView() {
        loadingDialog = DialogUtil.showLoadingDialog(this,getString(R.string.loading_key));
        TradeEncUtil.loadMainKey(Config.MAIN_KEY);
    }

    private void initNet(){
        CommuParams params = new CommuParams();
        params.setIp(Config.IP);
        params.setType(CommuType.SOCKET);
        params.setPort(Integer.parseInt(Config.PORT));
        params.setTimeout(40);
        params.setIfSSL(Config.SupportSSL);
        if (Config.SupportSSL) {
            params.setCer("Cer_YZF.crt");
        }
        Commu.getInstence().setCommuParams(params);
    }

    private void initData() {
        initNet();
        sendIso8583 = new Iso8583Manager(this);
        receiveIso8583 = new Iso8583Manager(this);
        sendIso8583.Load8583XMLconfigByTag("ISO8583Config");
        receiveIso8583.Load8583XMLconfigByTag("ISO8583Config");
        commu = Commu.getInstence();
        mCommuListener = new CommuListener() {
            @Override
            public void OnStatus(int status, byte[] bytes) {
                switch (status) {
                    case CommuStatus.INIT_COMMU:
                        Log.e(TAG, "commu init...");
                        break;
                    case CommuStatus.CONNECTING:
                        Log.e(TAG, "commu connecting...");
                        break;
                    case CommuStatus.SENDING:
                        Log.e(TAG, "commu send data...");
                        break;
                    case CommuStatus.RECVING:
                        Log.e(TAG, "commu recv data...");
                        break;
                    case CommuStatus.FINISH:
                        Log.e(TAG, "commu finish...");
                        try {
                            receiveIso8583.unpack(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(COMMU_FINISH);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void OnError(int i, String s) {

            }
        };
        try {
            request();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void request() throws Exception {
        setSendData();
        sendData = sendIso8583.pack();
    }

    private void setSendData() throws Exception {
        sendIso8583.setBit("tpdu", Config.TPDU);
        sendIso8583.setBit("header", Config.HEADER);
        sendIso8583.setBit("msgid", "0800");
        sendIso8583.setBit("11",PosUtil.getTraceAuto(this));
        boolean needTdKey = Config.NeedTDKey;
        String netInfoCode = "003";
        if (needTdKey) {
            netInfoCode = "004";
        }
        if (Config.isRSA) {
            netInfoCode = "005";
            if (needTdKey) {
                netInfoCode = "006";
            }
        }
        sendIso8583.setBit(60, "00" + PosUtil.getBatch(this) + netInfoCode);
        String sn = ServiceManager.getInstence().getDeviceinfo().getSN();
        //此序列号是检测中心测试所用序列号
        sn = "041100000026";
        String field62 = "Sequence No" + String.format(Locale.CHINA, "%02d", 4 + sn.length()) + "5555" + sn;
        Log.d(TAG,"设备序列号 field62:" + field62);
        sendIso8583.setBinaryBit(62, field62.getBytes());
        sendIso8583.setBit(63, Config.OPERATOR_NO + " ");
    }

    private void loadWorkKey(){
        byte[] field62 = receiveIso8583.getBitBytes(62);
        String tmpField62 = null;
        if (field62 != null) {
            Log.e(TAG,"field62 length = " + field62.length);

            tmpField62 = BytesUtil.bytes2HexString(field62);
            Log.e(TAG,"field62:" + tmpField62);
        }
        tmpField62 = "08024FCF811DA67208024FCF811DA67282E136650F8ADFFB11DC27840F8ADFFB11DC278400962B60DF801B5B86D24F71DF801B5B86D24F71ADC67D84";
        if (TradeEncUtil.updateWorkKey(tmpField62)) {
            String batchNo = receiveIso8583.getBit(60).substring(2, 8);
            PosUtil.setBatch(this,batchNo);
            ToastUtils.shortShow("load work key success");
        } else {
            ToastUtils.shortShow("load work key failure");
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==COMMU_FINISH){
                loadWorkKey();
                if(loadingDialog!=null){
                    loadingDialog.dismiss();
                }
                finish();
            }
        }
    };
}
