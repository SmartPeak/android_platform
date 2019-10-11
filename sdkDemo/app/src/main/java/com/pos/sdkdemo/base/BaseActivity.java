package com.pos.sdkdemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.widgets.ProcessDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liudy on 2017/1/23.
 * Base of All Activity
 */

public abstract class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";
    /**
     * show logs
     */
    private static final int SHOWLOG = 1;
    /**
     * clear logs
     */
    protected ProcessDialog processdialog = null;
    private static final int CLEARLOG = 2;
    protected LayoutInflater inflater;
    protected int MAX_TRY_CNT = 100;
    View contentView;
    private StringBuffer sb = new StringBuffer("");
    private TextView showlogs = null;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case SHOWLOG:
                    sb.append(msg.obj + "\n");
                    showlogs.setText(sb.toString());
                    break;
                case CLEARLOG:
                    sb = new StringBuffer("");
                    showlogs.setText(sb.toString());
                    break;
            }
        }

        ;

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (null == contentView) {
            inflater = LayoutInflater.from(this);
            contentView = inflater.inflate(R.layout.activity_base, null);
            setContentView(contentView);
            LinearLayout contentLayout = (LinearLayout) contentView.findViewById(R.id.ContentLayout);
            showlogs = (TextView) contentView.findViewById(R.id.logs);
            View subContent = onCreateView(inflater);
            if (null != subContent) {
                contentLayout.addView(subContent, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            onInitView();
            sb = new StringBuffer("");
        }
    }

    /**
     * for subclass create layout
     *
     * @param inflater
     * @return
     */
    abstract protected View onCreateView(LayoutInflater inflater);

    /**
     * for subclass init view
     */
    abstract protected void onInitView();

    /**
     * show logs
     *
     * @param msg
     */
    public void LOGD(String msg) {
        Message message = new Message();
        message.what = SHOWLOG;
        message.obj = msg;
        handler.sendMessage(message);
    }

    /**
     * clear logs
     */
    protected void CLearLog() {
        Message message = new Message();
        message.what = CLEARLOG;
        handler.sendMessage(message);
    }

    /**
     * 隐藏输入法键盘
     * @param view
     */
    protected void hideSoftKeyBoard(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);// 4.0的是setShowSoftInputOnFocus，4.2的是setSoftInputShownOnFocus
            method.setAccessible(false);
            method.invoke(view, false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示输入法键盘
     * @param view
     */
    protected void showSoftKeyBoard(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);// 4.0的是setShowSoftInputOnFocus，4.2的是setSoftInputShownOnFocus
            method.setAccessible(true);
            method.invoke(view, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * optimize soft input
     * @param view
     */
    protected  void optimizSoftKeyBoard(View view)
    {
        hideSoftKeyBoard(view);
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                {
                    showSoftKeyBoard(view);
                }
                else
                {
                    hideSoftKeyBoard(view);
                }
            }
        });
    }

    /**
     *
     */
    public void showProcessDialog(String title) {
        if (processdialog == null)
            processdialog = new ProcessDialog(this,title);
        else
        {
            processdialog.freshTitle(title);
        }
    }

    /**
     *
     */
    public void dismissProcessDialog() {
        if (processdialog != null && processdialog.isShowing()) {
            processdialog.stopTimer();
            processdialog.dismiss();
            processdialog = null;
        }
    }
}
