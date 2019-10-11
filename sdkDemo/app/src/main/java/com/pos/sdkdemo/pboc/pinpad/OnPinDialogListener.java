package com.pos.sdkdemo.pboc.pinpad;




/**
 * 
 * 
 * @author hz
 * @date 20160504
 * @version 1.0.0
 * @function
 * @lastmodify
 */
public interface OnPinDialogListener {
	public static final int OK = 0; /* 正确输入密码之后返回 */
	public static final int CANCEL = 1; /* 取消输入密码返回 */
	public void OnPinInput(int result);
	public void OnCreateOver();
}
