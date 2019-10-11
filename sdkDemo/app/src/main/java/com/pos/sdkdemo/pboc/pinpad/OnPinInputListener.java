package com.pos.sdkdemo.pboc.pinpad;

/**
 * Copyright © 2016 快钱支付清算信息有限公司. All rights reserved.
 * 
 * @author liudeyu
 * @date 2016年3月1日 上午10:04:28
 * @version 1.0.0
 * @function
 * @lastmodify
 */
public interface OnPinInputListener {
	public static final int OK = 0; /* 正确输入密码之后返回 */
	public static final int CANCEL = 1; /* 取消输入密码返回 */

	public void OnPinInput(int result);

	public void OnCreateOver();
}
