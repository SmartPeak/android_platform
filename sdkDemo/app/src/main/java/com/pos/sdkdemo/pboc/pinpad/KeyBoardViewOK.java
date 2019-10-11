package com.pos.sdkdemo.pboc.pinpad;

import com.basewin.utils.BCDHelper;
import com.pos.sdkdemo.R;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Copyright © 2016 快钱支付清算信息有限公司. All rights reserved.
 * 
 * @ClassName: KeyBoardViewOK
 * @Description: TODO
 * @author: liudeyu
 * @date: 2016年3月2日 下午3:19:11
 */
public class KeyBoardViewOK implements ICupBaseView, OnClickListener {
	private static final String TAG = "KeyBoardViewOK";
	private OnNumKeyListener numKeyListener;
	byte[] keylayout = new byte[104];
	int pos = 0;

	@SuppressWarnings("static-access")
	public KeyBoardViewOK(Context context) {
		this.mContext = context;
	}

	private static Context mContext;
	public TextView num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
	public LinearLayout numBack,numok;
	private View topLine;
	private byte[] layout = new byte[96];
	@Override
	public View getKeyBoardView() {
		View parent = View.inflate(mContext, R.layout.keyboadview2, null);
		numok = (LinearLayout) parent.findViewById(R.id.num_ok);
		num0 = (TextView) parent.findViewById(R.id.num0);
		num1 = (TextView) parent.findViewById(R.id.num1);
		num2 = (TextView) parent.findViewById(R.id.num2);
		num3 = (TextView) parent.findViewById(R.id.num3);
		num4 = (TextView) parent.findViewById(R.id.num4);
		num5 = (TextView) parent.findViewById(R.id.num5);
		num6 = (TextView) parent.findViewById(R.id.num6);
		num7 = (TextView) parent.findViewById(R.id.num7);
		num8 = (TextView) parent.findViewById(R.id.num8);
		num9 = (TextView) parent.findViewById(R.id.num9);
		numBack = (LinearLayout) parent.findViewById(R.id.num_back);
		topLine = (View) parent.findViewById(R.id.topline);
		numok.setOnClickListener(this);
		num0.setOnClickListener(this);
		num1.setOnClickListener(this);
		num2.setOnClickListener(this);
		num3.setOnClickListener(this);
		num4.setOnClickListener(this);
		num5.setOnClickListener(this);
		num6.setOnClickListener(this);
		num7.setOnClickListener(this);
		num8.setOnClickListener(this);
		num9.setOnClickListener(this);
		numBack.setOnClickListener(this);

		return parent;
	}

	/**
	 * 获取按键布局位置
	 * 
	 * @return
	 */
	public byte[] getKeyLayout(final byte[] closeBtn) {
		Log.d(TAG, "getKeyLayout");
		// pos = addToByteArray(getWidgetPosition(num0), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num1), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num2), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num3), keylayout, pos);
		//
		// pos = addToByteArray(getWidgetPosition(num4), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num5), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num6), keylayout, pos);
		//
		// pos = addToByteArray(getWidgetPosition(num7), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num8), keylayout, pos);
		// pos = addToByteArray(getWidgetPosition(num9), keylayout, pos);
		//
		// pos = addToByteArray(getWidgetPosition(numBack), keylayout, pos);

		// pos = addToByteArray(getWidgetPosition(numok), keylayout, pos);
			
		pos=0;
		byte[] keylayout=new byte[104];
		
		pos = addToByteArray(getWidgetPosition(num1), keylayout, pos);
		Log.d(TAG, "getKeyLayout1");
		pos = addToByteArray(getWidgetPosition(num2), keylayout, pos);
		Log.d(TAG, "getKeyLayout2");
		pos = addToByteArray(getWidgetPosition(num3), keylayout, pos);
		Log.d(TAG, "getKeyLayout3");

		pos = addToByteArray(getWidgetPosition(num4), keylayout, pos);
		Log.d(TAG, "getKeyLayout4");
		pos = addToByteArray(getWidgetPosition(num5), keylayout, pos);
		Log.d(TAG, "getKeyLayout5");
		pos = addToByteArray(getWidgetPosition(num6), keylayout, pos);
		Log.d(TAG, "getKeyLayout6");

		pos = addToByteArray(getWidgetPosition(num7), keylayout, pos);
		Log.d(TAG, "getKeyLayout7");
		pos = addToByteArray(getWidgetPosition(num8), keylayout, pos);
		Log.d(TAG, "getKeyLayout8");
		pos = addToByteArray(getWidgetPosition(num9), keylayout, pos);
		Log.d(TAG, "getKeyLayout9");

		pos = addToByteArray(closeBtn, keylayout, pos);
		Log.d(TAG, "getKeyLayout10");
		pos = addToByteArray(getWidgetPosition(num0), keylayout, pos);
		Log.d(TAG, "getKeyLayout11");
		pos = addToByteArray(getWidgetPosition(numok), keylayout, pos);
		Log.d(TAG, "getKeyLayout12");
		pos = addToByteArray(getWidgetPosition(numBack), keylayout, pos);
		Log.d(TAG, "getKeyLayout13");

		Log.d(TAG, "getKeyLayout = " + BCDHelper.hex2DebugHexString(keylayout, keylayout.length));
		return keylayout;
		
	}

	/**
	 * 设置键盘布局
	 * Set the keyboard layout
	 */
	public void setKeyShow(final byte[] keys,final OnGetLayoutSucListener listener) {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (keys != null) {
					Log.d("AIDL", "setKeyShow = " + BCDHelper.hex2DebugHexString(keys, keys.length));
					// TODO Auto-generated method stub
					num1.setText(String.valueOf(keys[0] - 0x30));
					num2.setText(String.valueOf(keys[1] - 0x30));
					num3.setText(String.valueOf(keys[2] - 0x30));

					num4.setText(String.valueOf(keys[3] - 0x30));
					num5.setText(String.valueOf(keys[4] - 0x30));
					num6.setText(String.valueOf(keys[5] - 0x30));

					num7.setText(String.valueOf(keys[6] - 0x30));
					num8.setText(String.valueOf(keys[7] - 0x30));
					num9.setText(String.valueOf(keys[8] - 0x30));

					num0.setText(String.valueOf(keys[10] - 0x30));
					//回显按键顺便获取按键位置
					//According to random keyboard, and obtain the location of the keys
					int pos = 0;
					pos = addToByteArray(getWidgetPosition(num1), layout, pos);
					pos = addToByteArray(getWidgetPosition(num2), layout, pos);
					pos = addToByteArray(getWidgetPosition(num3), layout, pos);

					pos = addToByteArray(getWidgetPosition(num4), layout, pos);
					pos = addToByteArray(getWidgetPosition(num5), layout, pos);
					pos = addToByteArray(getWidgetPosition(num6), layout, pos);

					pos = addToByteArray(getWidgetPosition(num7), layout, pos);
					pos = addToByteArray(getWidgetPosition(num8), layout, pos);
					pos = addToByteArray(getWidgetPosition(num9), layout, pos);

					pos = addToByteArray(getWidgetPosition(numBack), layout, pos);
					pos = addToByteArray(getWidgetPosition(num0), layout, pos);
					pos = addToByteArray(getWidgetPosition(numok), layout, pos);
					listener.onSuc();
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		if (numKeyListener != null) {
			numKeyListener.onClick(v);
		}
		Log.d(TAG, "onClick 获取按键位置 = " + BCDHelper.hex2DebugHexString(getWidgetPosition(v), getWidgetPosition(v).length));
	}

	/**
	 * 设置键盘监听
	 * 
	 * @param listener
	 */
	public void setOnNumKeyListener(OnNumKeyListener listener) {
		this.numKeyListener = listener;
	}

	/**
	 * 
	 * @Title: showTopLine
	 * @Description: 显示顶部横线
	 * @return: void
	 */
	public void showTopLine(boolean ifshow) {
		if (ifshow) {
			topLine.setVisibility(View.VISIBLE);
		} else {
			topLine.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 将src的数组接到des的数组
	 * 
	 * @param src
	 * @param dest
	 * @param possition
	 * @return
	 */
	public int addToByteArray(byte[] src, byte[] dest, int position) {
		Log.d(TAG, "addToByteArray:position before = " + position);
		System.arraycopy(src, 0, dest, position, src.length);
		return position += src.length;
	}

	/**
	 * 获取控件的位置
	 * 
	 * @param widget
	 * @return
	 */
	public byte[] getWidgetPosition(View widget) {
		int[] location = new int[2];
		// widget.getLocationInWindow(location);
		widget.getLocationOnScreen(location);
		int leftx, lefty, rightx, righty;
		leftx = location[0];
		lefty = location[1];
		rightx = location[0] + widget.getWidth();
		righty = location[1] + widget.getHeight();
		Log.d(TAG, "getWidgetPosition: leftx = " + leftx + " lefty = " + lefty + " rightx = " + rightx + " righty = " + righty);
		byte[] pos = new byte[8];
		// 0,768 0x0000 0x02fc
		// 0x00,0x00,0x02,0xfc

		byte[] tmp = BCDHelper.intToBytes2(leftx);
		Log.d(TAG, "getWidgetPosition: tmp = " + BCDHelper.hex2DebugHexString(tmp, tmp.length));
		byte[] tmp1 = BCDHelper.intToBytes2(lefty);
		Log.d(TAG, "getWidgetPosition: tmp1 = " + BCDHelper.hex2DebugHexString(tmp1, tmp1.length));
		byte[] tmp2 = BCDHelper.intToBytes2(rightx);
		Log.d(TAG, "getWidgetPosition: tmp2 = " + BCDHelper.hex2DebugHexString(tmp2, tmp2.length));
		byte[] tmp3 = BCDHelper.intToBytes2(righty);
		Log.d(TAG, "getWidgetPosition: tmp3 = " + BCDHelper.hex2DebugHexString(tmp3, tmp3.length));
		// 178,910 0x00b2 0x038e
		// 0x00,0xb2,0x03,0x8e
		// 左上x高位
		pos[0] = tmp[2];
		// 左上x低位
		pos[1] = tmp[3];
		// 左上y高位
		pos[2] = tmp1[2];
		// 左上y低位
		pos[3] = tmp1[3];
		// 右下x高位
		pos[4] = tmp2[2];
		// 右下x低位
		pos[5] = tmp2[3];
		// 右下y高位
		pos[6] = tmp3[2];
		// 右下y低位
		pos[7] = tmp3[3];
		Log.d(TAG, "getWidgetPosition: position = " + BCDHelper.hex2DebugHexString(pos, pos.length));
		return pos;
	}



}
