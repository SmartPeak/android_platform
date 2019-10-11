package com.pos.sdkdemo.widgets;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.interfaces.OnNumKeyListener;
import com.pos.sdkdemo.pboc.pinpad.ICupBaseView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author liudeyu
 * @date 2016年2月29日 下午5:54:17
 * @version 1.0.0
 * @function
 * @lastmodify
 */
public class KeyBoardView implements ICupBaseView, OnClickListener {

	private OnNumKeyListener numKeyListener;

	@SuppressWarnings("static-access")
	public KeyBoardView(Context context) {
		KeyBoardView.mContext = context;
	}

	private static Context mContext;
	public TextView num00, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
	public LinearLayout numBack;

	@Override
	public View getKeyBoardView() {
		View parent = View.inflate(mContext, R.layout.keyboadview, null);
		num00 = (TextView) parent.findViewById(R.id.num00);
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
		num00.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		if (numKeyListener != null) {
			numKeyListener.onClick(v);
		}
	}

	/**
	 * 设置键盘监听
	 * 
	 * @param listener
	 */
	public void setOnNumKeyListener(OnNumKeyListener listener) {
		this.numKeyListener = listener;
	}

}
