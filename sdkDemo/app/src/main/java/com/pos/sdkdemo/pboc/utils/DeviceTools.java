package com.pos.sdkdemo.pboc.utils;

import android.os.Build;

public class DeviceTools {
    public static final String P8000S = "P8000S";// P8000S机型//P8000S models
    public static final String P8000 = "P8000";//P8000机型//P8000 models


    /**
     * @return
     * @Title: getDeviceModel
     * @Description: 获取当前设备型号
     * @Description: Gets the current equipment type
     * @return: String
     */
    @SuppressWarnings("static-access")
    public static String getDeviceModel() {
        Build bd = new Build();
        return bd.MODEL;
    }
}
