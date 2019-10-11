package com.pos.sdkdemo.base;

import android.util.Log;

import com.basewin.services.BeeperBinder;
import com.basewin.services.DeviceInfoBinder;
import com.basewin.services.LEDBinder;
import com.basewin.services.PBOCBinder;
import com.basewin.services.PinpadBinder;
import com.basewin.services.PrinterBinder;
import com.basewin.services.ScanBinder;
import com.basewin.services.ServiceManager;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/12 10:00<br>
 * 描述: 测试sdk是否有问题的一个类 <br>
 */
public class BaseSDK {
    private static final String TAG = BaseSDK.class.getName();
    public static BeeperBinder beeper;
    public static ScanBinder scan;
    public static LEDBinder led;
    public static PBOCBinder pboc;
    public static PinpadBinder pinpad;
    public static PrinterBinder printer;
    public static DeviceInfoBinder deviceinfo;

    public static void init() {
        Log.e(TAG, "init");
        try {
            pboc = ServiceManager.getInstence().getPboc();
            pinpad = ServiceManager.getInstence().getPinpad();
            printer = ServiceManager.getInstence().getPrinter();
            beeper = ServiceManager.getInstence().getBeeper();
            scan = ServiceManager.getInstence().getScan();
            led = ServiceManager.getInstence().getLed();
            deviceinfo = ServiceManager.getInstence().getDeviceinfo();
        } catch (Exception e) {
            Log.e(TAG, "初始化 aidl api 错误 /n " + e.getMessage().toString());
            e.printStackTrace();
        }
        if (scan == null) {
            Log.e(TAG, "初始化 aidl scan 错误 /n ");
        } else {
            Log.e(TAG, "初始化 aidl scan 成功 /n ");
        }
        Log.e(TAG, "初始化Aidl api 成功 ");
    }

    public static BeeperBinder getBeeper() {
        return beeper;
    }

    public static ScanBinder getScan() {
        return scan;
    }

    public static LEDBinder getLed() {
        return led;
    }

    public static PBOCBinder getPboc() {
        return pboc;
    }

    public static PinpadBinder getPinpad() {
        return pinpad;
    }

    public static PrinterBinder getPrinter() {
        return printer;
    }

    public static DeviceInfoBinder getDeviceinfo() {
        return deviceinfo;
    }
}
