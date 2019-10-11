package com.pos.sdkdemo.base;

import java.util.List;

import com.basewin.log.LogUtil;
import com.basewin.utils.AppTools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public abstract class BaseService {
	public BaseService() {
		// TODO Auto-generated constructor stub
	}
	
	public static Context androidContext = null;
	
	public static String[] ShowPermissions() {
		PackageManager packManager = androidContext.getPackageManager();
		List<ApplicationInfo> infos = packManager.getInstalledApplications(PackageManager.GET_ACTIVITIES);
		ApplicationInfo info = null;
		try {
			info = packManager.getApplicationInfo(androidContext.getPackageName(), PackageManager.GET_PERMISSIONS);
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// LOG.D("appName--->" + packManager.getApplicationLabel(info) + "");
		try {
			PackageInfo packInfo = packManager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
			String[] permissons = packInfo.requestedPermissions;
			// 获取该app的所有权限
			// int length = permissons.length;
			// for (int i = 0; i < length; i++) {
			// LOG.D(permissons[i]);
			// }
			return permissons;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param perStrings
	 * @param perString
	 * @return
	 */
	public static boolean ifInside(String[] perStrings, String perString) {
		for (int i = 0; i < perStrings.length; i++) {
			if (perStrings[i].equals(perString)) {
				LogUtil.i(AppTools.class,"验证包含 " + perString);
				return true;
			}
		}
		return false;
	}
	public static void validatePermission(String permission) throws Exception {
		int ret = PackageManager.PERMISSION_DENIED;
		boolean valid = false;
		try {
			// LOG.D("检查" + permission);
			String[] permiStrings = ShowPermissions();
			valid = ifInside(permiStrings, permission);
			// ret = androidContext.checkPermission(permission,
			// Binder.getCallingPid(), Binder.getCallingUid());
			// LOG.D("检查" + permission + "完成 "+valid);
		} catch (Exception e) {
			// LOG.D("检查" + permission + "异常");
			e.printStackTrace();
		}

		// if (ret != PackageManager.PERMISSION_GRANTED) {
		// LOG.D("检查" + permission + "不满足 "+ret);
		// throw new SecurityException(String.format("Permission denied,
		// requires %s permission.", permission));
		// }
		if (valid != true) {
			// LOG.D("检查" + permission + "不满足 "+ret);
			throw new Exception(String.format("Permission denied, requires %s permission.", permission));
			// throw new SecurityException(String.format("Permission denied,
			// requires %s permission.", permission));
		}
		// LOG.D("检查" + permission + "完成");
	}
	
	/**
	 * 握奇的W9110设备需要屏蔽第1，2套密钥体系
	 * @throws Exception
	 */
	public static void validateW9110() throws Exception {
		validateDeviceModel("W9110");
	}
	
	public static void validateDeviceModel(String model) throws Exception {
		if (Build.MODEL.equals(model)) {
			throw new Exception("Permission denied,please use pinpad version 3.0 interface");
		}
	}

	public static void validateNull(Object object, String objName) throws Exception {
		if (object == null) {
			throw new Exception("参数" + objName + "不可为null");
		}
	}

	public static void validateMinNumber(int src, String srcName, int target) throws Exception {
		if (src < target) {
			throw new Exception("参数" + srcName + "[int]不可小于" + target);
		}
	}

	public static void validateMinEqualNumber(int src, String srcName, int target) throws Exception {
		if (src <= target) {
			throw new Exception("参数" + srcName + "[int]不可小于等于" + target);
		}
	}

	public static void validateMaxNumber(int src, String srcName, int target) throws Exception {
		if (src > target) {
			throw new Exception("参数" + srcName + "[int]不可大于" + target);
		}
	}

	public static void validateMaxEqualNumber(int src, String srcName, int target) throws Exception {
		if (src >= target) {
			throw new Exception("参数" + srcName + "[int]不可大于等于" + target);
		}
	}

	public static void validateMinNumber(long src, String srcName, long target) throws Exception {
		if (src < target) {
			throw new Exception("参数" + srcName + "[long]不可小于" + target);
		}
	}

	public static void validateMinEqualNumber(long src, String srcName, long target) throws Exception {
		if (src <= target) {
			throw new Exception("参数" + srcName + "[long]不可小于等于" + target);
		}
	}

	public static void validateMaxNumber(long src, String srcName, long target) throws Exception {
		if (src > target) {
			throw new Exception("参数" + srcName + "[long]不可大于" + target);
		}
	}

	public static void validateMaxEqualNumber(long src, String srcName, long target) throws Exception {
		if (src >= target) {
			throw new Exception("参数" + srcName + "[long]不可大于等于" + target);
		}
	}

	/**
	 * 判断参数是否在范围之内
	 * 
	 * @param src
	 * @param srcName
	 * @param target
	 * @throws DeviceException
	 */
	public static void validateInclude(int src, String srcName, int[] target) throws Exception {
		boolean valid = false;
		for (int i = 0; i < target.length; i++) {
			if (src == target[i]) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			throw new Exception("参数" + srcName + "[int]异常,不在正常范围!");
		}
	}

	/**
	 * 判断参数是否不在范围之内
	 * 
	 * @param src
	 * @param srcName
	 * @param target
	 * @throws DeviceException
	 */
	public static void validateExInclude(int src, String srcName, int[] target) throws Exception {
		boolean valid = false;
		for (int i = 0; i < target.length; i++) {
			if (src == target[i]) {
				valid = true;
				break;
			}
		}
		if (valid) {
			throw new Exception("参数" + srcName + "[int]异常,不在正常范围!");
		}
	}

	/**
	 * 判断设备是否处于打开状态，非打开的话打开设备，打开了抛出异常，适合open接口
	 * 
	 * @param isopen
	 * @throws DeviceException
	 */
	public static void DeviceOpen(boolean isopen) throws Exception {
		if (isopen) {
			throw new Exception("设备已打开，操作有误!");
		}
	}

	/**
	 * 判断设备是否处于打开状态，非打开状态抛出异常
	 * 
	 * @param isopen
	 * @throws DeviceException
	 */
	public static void validateDeviceIsOpen(boolean isopen) throws Exception {
		if (!isopen) {
			throw new Exception("设备还未打开，操作有误!");
		}
	}


	/**
	 * 判断设备是否处于异步调用状态，非异步调用状态抛出异常，适合cancelreques
	 * 
	 * @param iswork
	 * @throws DeviceException
	 */
	public static void DeviceCancelRequest(boolean iswork) throws Exception {
		if (!iswork) {
			throw new Exception("设备无异步调用,操作有误!");
		}
	}

	/**
	 * 判断设备是否处于异步调用状态，处于异步调用状态抛出异常，适合非cancelrequest接口调用判断
	 * 
	 * @param iswork
	 * @throws DeviceException
	 */
	public static void validateDeviceIsRequstPending(boolean iswork) throws Exception {
		if (iswork) {
			throw new Exception("设备处于异步调用状态,操作有误!");
		}
	}
}
