package com.pos.sdkdemo.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseDialog;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @ClassName: ProcessDialog
 * @Description: TODO
 * @author: liudeyu
 * @date: 2016年3月4日 下午8:04:04
 */
public class ProcessDialog extends BaseDialog {
	private static final int SHOW_TIME = 0;
	private static final int CLEAR = 1;
	/**
	 * 超时时间默认为60秒
	 */
	private final int timeout = 60;
	private int count = 0;
	private ImageView loding;
	private Context context;
	private TextView tv_timer,tv_title;
	private Timer timer = null;
	Handler handler = null;
	private String title;

	public ProcessDialog(Context context,String title) {
		super(context, R.layout.process_dialog, Gravity.CENTER);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.title = title;
		initView();
		setCancelable(false);
		setCanceledOnTouchOutside(false);
	}

	private void initView() {
		loding = (ImageView) findViewById(R.id.loding);
		AnimationDrawable animator = (AnimationDrawable) loding.getBackground();
		loding.setImageDrawable(null);
		animator.start();
		tv_timer = (TextView) findViewById(R.id.tv_timer);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(this.title);
		handler = new Handler(context.getMainLooper()) {
			@Override
			@SuppressWarnings("deprecation")
			public void handleMessage(Message msg) {
				if (msg.what == SHOW_TIME) {
					if (tv_timer != null) {
						tv_timer.setText(String.valueOf(msg.obj));
					}
				} else if (msg.what == CLEAR) {
					if (tv_timer != null) {
						tv_timer.setText("");
					}
				}
			};
		};

	}

	class timerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			count++;
			Log.d("processdialog", "显示超时计数器 = " + count);
			if (count >= timeout) {
				stopTimer();
			}
			Message msg = new Message();
			msg.what = SHOW_TIME;
			msg.obj = timeout - count;
			handler.sendMessage(msg);

		}
	}

	public void startTimerTask() {
		timer = new Timer();
		count = 0;
		timerTask task = new timerTask();
		timer.schedule(task, 0, 1 * 1000);
	}

	public void stopTimer() {
		if (null != timer) {
			timer.cancel();
			timer = null;
			handler.sendEmptyMessage(CLEAR);
		}
	}

	public void freshTitle(String title)
	{
		this.tv_title.setText(title);
	}

}
