package com.pos.sdkdemo.pboc.pinpad;




import java.util.List;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Copyright © 2016 快钱支付清算信息有限公司. All rights reserved.
 * 
 * @author liudeyu
 * @date 2016年3月1日 上午11:35:28
 * @version 1.0.0
 * @function 实现TextView数组的字符录入，回退等功能
 * @lastmodify
 */
public class TextViewHelper implements TextViewHelperInterface {
	
	private final String TAG = "TextViewHelper";
	private EditText pins = null;
	
	public TextViewHelper(EditText et) {
		pins = et;
	}

	@Override
	public void add(String tx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void back() {
		// TODO Auto-generated method stub
	}


	@Override
	public void addPins(int len, int key) {
		// TODO Auto-generated method stub
		clean();
		String keys = "";
		for (int i = 0; i < len; i++) {
			keys += '*';
		}
		pins.setText(keys);
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		pins.setText("");
	}

	@Override
	public boolean isPwdCorrect(String correct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
