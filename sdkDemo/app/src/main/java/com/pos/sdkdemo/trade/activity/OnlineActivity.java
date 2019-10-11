package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;

import com.basewin.commu.Commu;
import com.basewin.commu.define.CommuListener;
import com.basewin.commu.define.CommuParams;
import com.basewin.commu.define.CommuStatus;
import com.basewin.commu.define.CommuType;
import com.basewin.define.InputPBOCOnlineData;
import com.basewin.define.OutputQPBOCResult;
import com.basewin.define.PBOCErrorCode;
import com.basewin.define.PBOCTransactionResult;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Dao.db.TransactionDataDB;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.interfac.OnError;
import com.pos.sdkdemo.trade.interfac.OnTransactionResult;
import com.pos.sdkdemo.trade.net.NetManager;
import com.pos.sdkdemo.trade.net8583.Base8583Package;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.DialogUtil;
import com.pos.sdkdemo.utils.GlobalData;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.ToastUtils;
import com.pos.sdkdemo.utils.TradeEncUtil;

import java.io.IOException;

public class OnlineActivity extends Activity implements OnTransactionResult,OnError {
    public static final String TAG = "feeling";
    public static final int COMMU_FINISH = 0x00;

    private Base8583Package sendPackage;
    private Iso8583Manager receiveIso8583 = null;
    private CommuListener mCommuListener = null;
    private byte[] receiveData = null;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetManager.connect(OnlineActivity.this,sendPackage,mCommuListener);
            }
        }).start();

    }

    @Override
    public void finish() {
        super.finish();
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }

    private void initView() {
        loadingDialog = DialogUtil.showLoadingDialog(this,getString(R.string.net_work));
        GlobleData.getInstance().setOnTransactionResult(this);
    }

    private void initData() {
        sendPackage = GlobleData.getInstance().getSend8583Package();
        receiveIso8583 = new Iso8583Manager(this);
        receiveIso8583.Load8583XMLconfigByTag("ISO8583Config");
        mCommuListener = new CommuListener() {
            @Override
            public void OnStatus(int status, byte[] bytes) {
                switch (status) {
                    case CommuStatus.INIT_COMMU:
                        break;
                    case CommuStatus.CONNECTING:
                        break;
                    case CommuStatus.SENDING:
                        break;
                    case CommuStatus.RECVING:
                        break;
                    case CommuStatus.FINISH:
                        Log.e(TAG, "commu finish...");
                        try {
                            receiveIso8583.unpack(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        TransactionDataDB.save(receiveIso8583);
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
    }

    @Override
    public void transactionResult(int result, Intent data) {
        if(result == PBOCTransactionResult.APPROVED){
            Intent intent =new Intent(OnlineActivity.this,PrintWaitActivity.class);
            startActivity(intent);
            finish();
        }else if(result == PBOCTransactionResult.TERMINATED){
            mHandler.sendEmptyMessage(GlobleData.ONERROR);
        }

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==COMMU_FINISH){
                try {
                    InputPBOCOnlineData onlineData = new InputPBOCOnlineData();
                    onlineData.setResponseCode(receiveIso8583.getBit(39));
                    ServiceManager.getInstence().getPboc().inputOnlineProcessResult(onlineData.getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(msg.what==GlobleData.ONERROR){
                ToastUtils.shortShow(R.string.trade_failure);
                finish();
            }
        }
    };

    @Override
    public void onError(Intent intent) {
        int code = intent.getIntExtra("code", 65282);
        String errorMsg = PBOCErrorCode.getErrorMsgByCode(code);
        ToastUtils.shortShow(errorMsg);
        try {
            ServiceManager.getInstence().getPboc().stopTransfer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
