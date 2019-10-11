package com.pos.sdkdemo.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.pos.sdkdemo.R;


/**
 * @author liudeyu
 * @date 2016年2月29日 下午4:12:13
 * @version 1.0.0
 * @function
 * @lastmodify
 */
public abstract class BaseDialog extends Dialog {
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; // 需要自己定义标志

	protected Context iContext;

	/**
	 * 设置dialog位置的重载方式
	 * 
	 * @Title:BaseDialog
	 * @Description:TODO
	 * @param context
	 * @param layoutResID
	 * @param gravity
	 *            传递dialog的位置，CENTER，LEFT等等
	 */
	public BaseDialog(Context context, int layoutResID, int gravity) {
		super(context, R.style.DialogStyle);
		iContext = context;
		setContentView(layoutResID);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		/*
		 * 获取对话框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
		 * 对象,这样这可以以同样的方式改变这个Activity的属性.
		 */
		Window dialogWindow = getWindow();
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		// dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(gravity);
		lp.width = wm.getDefaultDisplay().getWidth();
		/*
		 * lp.x与lp.y表示相对于原始位置的偏移.
		 * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
		 * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
		 * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
		 * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
		 * 当参数值包含Gravity.CENTER_HORIZONTAL时
		 * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
		 * 当参数值包含Gravity.CENTER_VERTICAL时
		 * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
		 * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
		 * Gravity.CENTER_VERTICAL.
		 * 
		 * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
		 * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了, Gravity.LEFT, Gravity.TOP,
		 * Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
		 */

		// lp.x = 0; // 新位置X坐标
		// lp.y = 54; // 新位置Y坐标
		dialogWindow.setAttributes(lp);
		// 设置载入载出动画
		if (gravity != Gravity.CENTER) {
			dialogWindow.setWindowAnimations(R.style.dialog_anim_bottom);
		} else {
			dialogWindow.setWindowAnimations(R.style.dialog_anim_center);
		}

		show();
	}

	/**
	 * 设置是否全屏的重载方式
	 * 
	 * @Title:BaseDialog
	 * @Description:TODO
	 * @param context
	 * @param layoutResID
	 * @param gravity
	 * @param fullscreen
	 */
	public BaseDialog(Context context, int layoutResID, int gravity, boolean fullscreen) {
		super(context, R.style.DialogStyle);
		iContext = context;
		setContentView(layoutResID);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		/*
		 * 获取对话框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
		 * 对象,这样这可以以同样的方式改变这个Activity的属性.
		 */
		Window dialogWindow = getWindow();
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		// dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(gravity);
		lp.width = wm.getDefaultDisplay().getWidth();
		if (fullscreen)
			lp.height = wm.getDefaultDisplay().getHeight();
		/*
		 * lp.x与lp.y表示相对于原始位置的偏移.
		 * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
		 * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
		 * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
		 * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
		 * 当参数值包含Gravity.CENTER_HORIZONTAL时
		 * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
		 * 当参数值包含Gravity.CENTER_VERTICAL时
		 * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
		 * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
		 * Gravity.CENTER_VERTICAL.
		 * 
		 * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
		 * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了, Gravity.LEFT, Gravity.TOP,
		 * Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
		 */

		// lp.x = 0; // 新位置X坐标
		// lp.y = 54; // 新位置Y坐标
		dialogWindow.setAttributes(lp);
		// 设置载入载出动画
		if (gravity != Gravity.CENTER) {
			dialogWindow.setWindowAnimations(R.style.dialog_anim_bottom);
		} else {
			dialogWindow.setWindowAnimations(R.style.dialog_anim_center);
		}
		show();
	}

	/**
	 * 设置x,y相对的位置的dialog重载方式，如果是TOP，设置了x=0,y=50,就是上面，靠左，并且距离上面50sp的位置
	 * 
	 * @Title:BaseDialog
	 * @Description:TODO
	 * @param context
	 * @param layoutResID
	 * @param x
	 * @param gravity
	 */
	public BaseDialog(Context context, int layoutResID, int x, int y, int gravity) {
		super(context, R.style.DialogStyle);
		iContext = context;
		setContentView(layoutResID);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		/*
		 * 获取对话框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
		 * 对象,这样这可以以同样的方式改变这个Activity的属性.
		 */
		Window dialogWindow = getWindow();
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		// dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(gravity);
		lp.x = x;
		lp.y = y;
		dialogWindow.setAttributes(lp);
		// 设置载入载出动画
		dialogWindow.setWindowAnimations(R.style.dialog_anim);
		show();
	}
	public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
	
	/* (non-Javadoc)
	 * @see android.app.Dialog#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {// MENU键
			// 拦截菜单键
			return true;
		}
		// 拦截HOME
		else if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		} 
		return super.onKeyDown(keyCode, event);
	}
}
