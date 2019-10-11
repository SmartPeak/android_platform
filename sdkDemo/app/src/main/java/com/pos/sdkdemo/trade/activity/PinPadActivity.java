package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;

import com.basewin.aidl.OnPinInputListener;
import com.basewin.define.CardType;
import com.basewin.define.GlobalDef;
import com.basewin.define.OutputPBOCAAData;
import com.basewin.define.PBOCErrorCode;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.pboc.pinpad.OnGetLayoutSucListener;
import com.pos.sdkdemo.pboc.pinpad.OnPinDialogListener;
import com.pos.sdkdemo.pboc.pinpad.PinInputDialog;
import com.pos.sdkdemo.pinpad.PinpadInterfaceVersion;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.interfac.OnAARequestOnlineProcess;
import com.pos.sdkdemo.trade.interfac.OnError;
import com.pos.sdkdemo.utils.GlobalData;
import com.pos.sdkdemo.utils.ToastUtils;

public class PinPadActivity extends Activity implements OnAARequestOnlineProcess,OnError {
    public static final String TAG="feeling";
    private final int SETLAYOUT = 0x01; // set key layout the pinpad(设置keys布局)
    private final int INPUT_PIN = 0x02; // display inputting the pinpad(PIN输入值的显示)
    private final int PIN_DIALOG_CANCEL = 0x03; // close pinpad(Pinpad关闭)
    private int cardtype=0;
    private String cardno="";
    private String amount="";
    private PinInputDialog pindialog;
    private boolean ifOnlinePin=true;

    private byte[] keylayout = new byte[96];

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_pad);
        initData();
        initHander();
        initPinpad();
    }
    private void initData(){
        cardtype=getIntent().getIntExtra("cardtype",0);
        cardno=getIntent().getStringExtra("cardno");
        amount= (String) GlobleData.getInstance().datas.get(Config.AMOUNT);
        ifOnlinePin =getIntent().getBooleanExtra("ifOnlinePin",true);
        GlobleData.getInstance().setOnAARequestOnlineProcess(this);
    }

    private void initPinpad(){
        try {
            // set the pinpad display mode(设置pinpad显示模式)
            ServiceManager.getInstence().getPinpad().setPinpadMode(GlobalDef.MODE_RANDOM);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // Pinpad parameter settings[context,card
        // no,tips,amount,callback](pinpad参数设置[上下文,卡号,提示,金额,回调])
        pindialog = new PinInputDialog(this, cardno, "Please enter the Bank card password", amount,
                new OnPinDialogListener() {

                    @Override
                    public void OnPinInput(int result) {

                    }

                    @Override
                    public void OnCreateOver() {
                        // get layout(获取布局)
                        try {
                            ServiceManager.getInstence().getPinpad().setOnPinInputListener(pinpadListener);
                            // set the pinpad information[key index,card no,password
                            // length](设置pinpad相关信息)

                            if (ifOnlinePin) {
                                switch (GlobalData.getInstance().getPinpadVersion()) {
                                    case PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3:
                                        ServiceManager.getInstence().getPinpad().inputOnlinePinByArea(
                                                2, 1, cardno,
                                                new byte[]{ 0, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
                                        break;
                                    case PinpadInterfaceVersion.PINPAD_INTERFACE_DUKPT:
                                        ServiceManager.getInstence().getPinpad().inputOnlinePinDukpt(1, 0, 60, cardno,
                                                new byte[]{ 0, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
                                        break;
                                }
                            } else {
                                ServiceManager.getInstence().getPinpad().inputOfflinePin(new byte[]{ 0, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, 60);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    // pinpad the callback(pinpad的回调)
    private OnPinInputListener pinpadListener = new OnPinInputListener() {

        @Override
        public void onInput(int len, int key) throws RemoteException {
            // returns the pinpad the length of the input, the key is
            // invalid(返回pinpad输入中的长度，Key无效)
            Log.e(TAG, "onInput: ");
            Message message = new Message();
            message.what = INPUT_PIN;
            Bundle bundle = new Bundle();
            bundle.putInt("len", len);
            bundle.putInt("key", key);
            message.setData(bundle);
            mHandler.sendMessage(message);
        }

        @Override
        public void onError(int errorCode) throws RemoteException {
            // pinpad result to error(pinpad出错)
            Log.e(TAG, "onError: ");
            if ((errorCode & 0x63c0) == 0x63c0) {
                initPinpad();
            } else {
                mHandler.sendEmptyMessage(PIN_DIALOG_CANCEL);
            }
        }

        @Override
        public void onConfirm(byte[] data, boolean isNonePin) {
            // the user to identify the input password,this Data is cryptography
            // encrypted to the password(用户确定了输入的密码,Data为加密了的密码密文)
            GlobleData.getInstance().card.password = data;
            Log.e(TAG, "onConfirm: isNonePin:"+isNonePin);
            if (!isNonePin) {
                // Encrypted transaction(加密交易)
                if (cardtype == CardType.IC_CARD) {
                    try {
                        ServiceManager.getInstence().getPboc().comfirmPinpad(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (pindialog != null) {
                        pindialog.dismiss();
                        pindialog = null;
                    }
                    Intent intent = new Intent(PinPadActivity.this,OnlineActivity.class);
                    intent.putExtra("amount",amount);
                    startActivity(intent);
                    finish();
                }
            } else {
                // no secret trading(无密交易)
                if (cardtype == CardType.IC_CARD) {
                    try {
                        ServiceManager.getInstence().getPboc().comfirmPinpad(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (pindialog != null) {
                        pindialog.dismiss();
                        pindialog = null;
                    }
                    Intent intent = new Intent(PinPadActivity.this,OnlineActivity.class);
                    intent.putExtra("amount",amount);
                    startActivity(intent);
                    finish();
                }
            }

        }

        @Override
        public void onCancel() throws RemoteException {
            // if you click on the cancel button(点了取消按钮)
            Log.e(TAG, "onCancel: ");
            mHandler.sendEmptyMessage(PIN_DIALOG_CANCEL);
        }

        @Override
        public void onPinpadShow(byte[] data) throws RemoteException {
            // result Key values,use this on setting the pinpad
            // layout(从底层返回键值，使用此去设置密码键盘)
            Log.e(TAG, "onPinpadShow: ");
            Message message = new Message();
            message.what = SETLAYOUT;
            message.obj = data;
            mHandler.sendMessage(message);
        }
    };

    private void initHander(){
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SETLAYOUT:
                        pindialog.setKeyShow((byte[]) msg.obj, new OnGetLayoutSucListener() {
                            @Override
                            public void onSuc() {
                                keylayout = pindialog.getKeyLayout();
                                try {
                                    ServiceManager.getInstence().getPinpad().setPinpadLayout(keylayout);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case INPUT_PIN:
                        if (pindialog != null) {
                            Bundle bundle = msg.getData();
                            pindialog.setPins(bundle.getInt("len"), bundle.getInt("key"));
                        }
                        break;
                    case PIN_DIALOG_CANCEL:
                        if (pindialog != null) {
                            pindialog.dismiss();
                            pindialog = null;
                            finish();
                        }
                        break;
                        default:
                            break;
                }
            }
        };
    }


    @Override
    public void AARequestOnlineProcess(Intent data) {
        Log.e(TAG, "AARequestOnlineProcess: ");
        if (pindialog != null) {
            pindialog.dismiss();
            pindialog = null;
        }
        OutputPBOCAAData out = new OutputPBOCAAData(data);
        GlobleData.getInstance().card.icAAData = out;
        Intent intent = new Intent(PinPadActivity.this,OnlineActivity.class);
        intent.putExtra("amount",amount);
        startActivity(intent);

        finish();
    }

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
        if (pindialog != null) {
            pindialog.dismiss();
            pindialog = null;
        }
        finish();
    }
}
