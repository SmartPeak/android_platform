package com.pos.sdkdemo.pinpad;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.basewin.define.AlgorithmType;
import com.basewin.define.KeyType;
import com.basewin.services.ServiceManager;
import com.pos.sdk.security.PedKcvInfo;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.GlobalData;

public class PinpadTestActivityForDukpt extends BaseActivity {
    /**
     * dukpt group id [1,10]
     */
    int groupid = 1;
    /**
     * protect key data
     */
    byte[] tlk = new byte[]{0x11, 0x22, 0x33, 0x44, 0x11, 0x22, 0x33, 0x44, 0x11, 0x22, 0x33, 0x44, 0x11, 0x22, 0x33,
            0x44};
    /**
     * protect key length
     */
    int tlkLen = 16;
    /**
     * /** TIK data
     */
    byte[] tik = new byte[]{(byte) 0xF1, 0x2a, 0x11, 0x22, (byte) 0xF1, 0x2a, 0x11, 0x22, (byte) 0xF1, 0x2a, 0x11,
            0x22, 0x00, 0x00, 0x00, 0x00};
    /**
     * tik length
     */
    int tikLen = 16;
    /**
     * ksn
     */
    byte[] ksn = new byte[]{(byte) 0xF8, 0x76, 0x54, 0x32, 0x10, 0x0F, 0x0F, 0x10, 0x00, 0x00};
    private TextView tv_pinpad_result;
    private VirtualPWKeyboardView pwKeyboard;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_pinpad_test_dukpt, null);
    }

    @Override
    protected void onInitView() {
        setTitle("pinpad");
        initView();
    }

    private void setHint(String s) {
        Log.e("LOG:", s);
        LOGD(s);
    }

    private boolean loadProtectKeyForInitKey() {
        try {
            return ServiceManager.getInstence().getPinpad().loadDukptTLK(tlk);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void loadDukptKey(View view) {
        // if you need a protect key for init key,you need load like this
        // loadProtectKeyForInitKey();

        int mode = 0;
        byte[] aucCheckBufIn = new byte[5];
        PedKcvInfo kcvInfo = new PedKcvInfo(mode, aucCheckBufIn);
        boolean iRet = false;
        try {
            iRet = ServiceManager.getInstence().getPinpad().loadDukptTIK(groupid, tik, ksn, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (iRet) {
            showToast("load dukpt Key Success!");
            GlobalData.getInstance().setPinpadVersion(PinpadInterfaceVersion.PINPAD_INTERFACE_DUKPT);
        } else {
            showToast("load protect Key error!");
        }

        //test get mac
        byte[] testData = new byte[]{0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11};
        PosByteArray rspmac = new PosByteArray();
        PosByteArray rspksn = new PosByteArray();
        try {
            int ret = ServiceManager.getInstence().getPinpad().calMacForDukpt(groupid, 2, testData, rspmac, rspksn);
            if (ret == 0) {
                showToast("get dukpt mac success!");
                showToast("return mac is " + BCDHelper.bcdToString(rspmac.buffer, 0, rspmac.len));
                showToast("return ksn is " + BCDHelper.bcdToString(rspksn.buffer, 0, rspksn.len));
            } else {
                showToast("get dukpt mac fail,errorcode is " + ret);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //test des enc&dec
        PosByteArray rspdes = new PosByteArray();
        PosByteArray rspksn2 = new PosByteArray();
        try {
            //ecb enc
            int ret = ServiceManager.getInstence().getPinpad().calDesForDukpt(groupid, KeyType.DUKPT_DES, null, AlgorithmType.DUKPT_CBC_ENC, testData, rspdes, rspksn2);
            if (ret == 0) {
                showToast("cbc enc des success!");
                showToast("return repdata is " + BCDHelper.bcdToString(rspdes.buffer, 0, rspdes.len));
                showToast("return ksn is " + BCDHelper.bcdToString(rspksn2.buffer, 0, rspksn2.len));
            } else {
                showToast("cbc enc des fail,errorcode is " + ret);
            }

            //ecb dec
            ret = ServiceManager.getInstence().getPinpad().calDesForDukpt(groupid, KeyType.DUKPT_DES, null, AlgorithmType.DUKPT_ECB_DEC, testData, rspdes, rspksn2);
            if (ret == 0) {
                showToast("ecb dec des success!");
                showToast("return repdata is " + BCDHelper.bcdToString(rspdes.buffer, 0, rspdes.len));
                showToast("return ksn is " + BCDHelper.bcdToString(rspksn2.buffer, 0, rspksn2.len));
            } else {
                showToast("ecb dec des fail,errorcode is " + ret);
            }

            //cbc enc
            ret = ServiceManager.getInstence().getPinpad().calDesForDukpt(groupid, KeyType.DUKPT_DES, null, AlgorithmType.DUKPT_CBC_ENC, testData, rspdes, rspksn2);
            if (ret == 0) {
                showToast("cbc enc des success!");
                showToast("return repdata is " + BCDHelper.bcdToString(rspdes.buffer, 0, rspdes.len));
                showToast("return ksn is " + BCDHelper.bcdToString(rspksn2.buffer, 0, rspksn2.len));
            } else {
                showToast("ecb enc des fail,errorcode is " + ret);
            }

            //cbc dec
            ret = ServiceManager.getInstence().getPinpad().calDesForDukpt(groupid, KeyType.DUKPT_DES, null, AlgorithmType.DUKPT_CBC_DEC, testData, rspdes, rspksn2);
            if (ret == 0) {
                showToast("cbc dec des success!");
                showToast("return repdata is " + BCDHelper.bcdToString(rspdes.buffer, 0, rspdes.len));
                showToast("return ksn is " + BCDHelper.bcdToString(rspksn2.buffer, 0, rspksn2.len));
            } else {
                showToast("ecb dec des fail,errorcode is " + ret);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void inputonlinepin(View view) {
        String cardNumber = getCardNumber();
        if (TextUtils.isEmpty(cardNumber)) {
            setHint("cat Number  is  null !");
            return;
        }
        PWDialog pwDialog = new PWDialog(this, PinpadInterfaceVersion.PINPAD_INTERFACE_DUKPT, 1, 0, 60);
        pwDialog.setListener(new PWDialog.PWListener() {
            @Override
            public void onConfirm(byte[] bytes, boolean b) {
                setHint("Password:" + BCDHelper.bcdToString(bytes));
            }

            @Override
            public void onCancel() {
                setHint("Password Cancel");
            }

            @Override
            public void onError(int i) {
                setHint("Password Error");
            }
        });
        pwDialog.showForPW(cardNumber);
    }

    private void initView() {
    }

    private String getCardNumber() {
        return "6210885200013000000";
    }

    private void showResult(String msg) {
        tv_pinpad_result.setVisibility(View.VISIBLE);
        pwKeyboard.setVisibility(View.GONE);
        tv_pinpad_result.setText(msg);
    }

    private void showToast(String msg) {
        setHint(msg);
        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * format pinpad ,clear all keys
     *
     * @param view
     */
    public void format(View view) {
        try {
            ServiceManager.getInstence().getPinpad().format();
            LOGD("format success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
