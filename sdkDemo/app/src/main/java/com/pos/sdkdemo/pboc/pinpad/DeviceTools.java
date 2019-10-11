package com.pos.sdkdemo.pboc.pinpad;



import android.os.Build;

public class DeviceTools {
	public static final String P8000S = "P8000S";//胜本 P8000S机型
	public static final String P8000 = "P8000";//胜本P8000机型
	
	
	/**
	 * 
	 * @Title: getDeviceModel 
	 * @Description: 获取当前设备型号
	 * @return
	 * @return: String
	 */
	@SuppressWarnings("static-access")
	public static String  getDeviceModel(){
		Build bd = new Build();
		return bd.MODEL;
	}
}
