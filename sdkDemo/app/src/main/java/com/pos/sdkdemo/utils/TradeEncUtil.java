package com.pos.sdkdemo.utils;

import android.os.RemoteException;
import android.util.Log;

import com.basewin.define.BwPinpadSource;
import com.basewin.services.PBOCBinder;
import com.basewin.services.PinpadBinder;
import com.basewin.services.ServiceManager;
import com.pos.sdk.security.PosSecurityManager;
import com.pos.sdkdemo.trade.Config;

public class TradeEncUtil {
    static String TAG="feeling";
    static String DES = "DES/ECB/NoPadding";
    static String TriDes = "DESede/ECB/NoPadding";

    private static PinpadBinder mPinPadService = null;
    private static PBOCBinder mPbocService = null;

    static {

        try {
            mPinPadService = ServiceManager.getInstence().getPinpad();
            mPbocService = ServiceManager.getInstence().getPboc();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String getMacECB(byte[] data) throws RemoteException {
        String result = null;
        try {
            result = mPinPadService.calcMACByArea(2, 1, BCDHelper.bcdToString(data, 0, data.length), BwPinpadSource.MAC_ECB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("feeling","硬件MAC结果：" + result);
        return result;
    }

    //联机签到成功更新工作密钥
    public static boolean updateWorkKey(String data) {
        if (data == null || (data.length() != 48 && data.length() != 80 && data.length() != 112 && data.length() != 120 && data.length() != 168)) {
            Log.e(TAG,"返回的工作密钥长度不对！");
            return false;
        }
        Log.e(TAG,"data[" + data.length() + "]=" + data);
        boolean dataPin = false;
        boolean dataMac = false;
        boolean dataTdk = false;
        boolean needTdKey = Config.NeedTDKey;
        try {
            Log.e(TAG,"data.substring(0, 32)=" + data.substring(0, 32));
            Log.e(TAG,"data.substring(32, 40)=" + data.substring(32, 40));
            Log.e(TAG,"data.substring(40, 72)=" + data.substring(40, 72));
            Log.e(TAG,"data.substring(72, 80)=" + data.substring(72, 80));
            if (needTdKey) {
                Log.e(TAG,"data.substring(80, 112)=" + data.substring(80, 112));
                Log.e(TAG,"data.substring(112, 120)=" + data.substring(112, 120));
            }
            if (Config.isRSA) {
                dataPin = mPinPadService.loadPinKeyByArea(2, 1, data.substring(0, 32), data.substring(32, 40));
                dataMac = mPinPadService.loadMacKeyByArea(2, 1, data.substring(40, 56), data.substring(72, 80));
                if (needTdKey) {
                    dataTdk = mPinPadService.loadTDKeyByArea(2, 1, data.substring(80, 112), data.substring(112, 120));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG,"dataPin=" + dataPin);
        Log.e(TAG,"dataMac=" + dataMac);
        if (needTdKey) {
            Log.e(TAG,"dataTdk=" + dataTdk);
        }

        return !needTdKey ? dataPin && dataMac : dataPin && dataMac && dataTdk;
    }

    /**
     * 更新主密钥
     *
     * @param key
     * @return
     */
    public static boolean loadMainKey(String key) {
        Log.e(TAG,"loadMainKey(String key)  load start。。。");
        boolean b = false;
        try {
            if (Config.isRSA) {
                b = mPinPadService.loadMainKeyByArea(2, 1, key);
            } else {
                b = mPinPadService.loadMainKeySM4ByArea(2, 1, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (b) {
            PosSecurityManager.getDefault().SysSetWriteKeyResult(0);
        } else {
            ToastUtils.shortShow("load failure。。。");
            PosSecurityManager.getDefault().SysSetWriteKeyResult(2);
        }
        return b;
    }
}
