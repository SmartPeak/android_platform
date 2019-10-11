package com.pos.sdkdemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.pos.sdkdemo.base.DemoApplication;

/**
 *
 * @ClassName: ToastUtils
 * @Description: 简单提示Toast工具类
 * @author: bo.zhao.pic
 * @date: 2016-3-8 下午3:04:42
 */

public class ToastUtils {

	private static Toast mToast;

	public static void shortShow(String info) {
		if (mToast == null) {
			mToast = Toast.makeText(DemoApplication.getInstance(), info, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(info);
		}
		mToast.show();
	}

	public static void shortShow(Context context, String msg) {
		if (null != msg && msg.length() > 0 && null != context) {
			if (mToast == null) {
				mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			} else {
				mToast.setText(msg);
			}
			mToast.show();
		}
	}

	public static void shortShow(int infoResId) {
		Toast.makeText(DemoApplication.getInstance(), infoResId, Toast.LENGTH_SHORT).show();
	}

	public static void longShow(String info) {
		Toast.makeText(DemoApplication.getInstance(), info, Toast.LENGTH_LONG).show();
	}

	public static void longShow(int infoResId) {
		Toast.makeText(DemoApplication.getInstance(), infoResId, Toast.LENGTH_SHORT).show();
	}
}
