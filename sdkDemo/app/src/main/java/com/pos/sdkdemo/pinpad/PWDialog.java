package com.pos.sdkdemo.pinpad;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.basewin.aidl.OnPinInputListener;
import com.basewin.log.LogUtil;
import com.basewin.services.ServiceManager;
import com.pos.sdk.security.PosSecurityManager;
import com.pos.sdk.security.PosSecurityManager.EventListener;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.utils.BCDHelper;

/**
 * Created by wangdh on 2017/1/23.
 * name：
 * 描述：
 */

public class PWDialog extends Dialog {
    private Context mContext;
    //default pinpad interface version is 3.0
    private int pinpad_version = PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3;
    private int area = 1;
    private int tmkindex = 1;
    private int groupId = 1;
    int mode = 0;
    private int timeout = 60;

    public PWDialog(Context context,int pinpadversion,int area,int tmkindex) {
        super(context);
        mContext = context;
        pinpad_version = pinpadversion;
        this.area = area;
        this.tmkindex = tmkindex;
        init();
    }

    public PWDialog(Context context,int pinpadversion,int groupId,int mode,int timeout) {
        super(context);
        mContext = context;
        pinpad_version = pinpadversion;
        this.groupId = groupId;
        this.mode = mode;
        this.timeout = timeout;
        init();
    }

    public PWDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected PWDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }
    public interface PWListener{
        void onConfirm(byte[] bytes, boolean b);
        void onCancel();
        void onError(int i);
    }
    private PWListener listener;

    public PWListener getListener() {
        return listener;
    }

    public void setListener(PWListener listener) {
        this.listener = listener;
    }

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM); //显示在底部

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        p.height=p.width;
        getWindow().setAttributes(p);
    }

    private TextView tv_pinpad_pw;
    private VirtualPWKeyboardView pwKeyboard;
    private String cardNumber;

    private void init() {
        view = View.inflate(mContext, R.layout.layout_pw, null);
        tv_pinpad_pw = (TextView) view.findViewById(R.id.tv_pinpad_pw);
        pwKeyboard = (VirtualPWKeyboardView) view.findViewById(R.id.pwKeyboard);
    }

    private void setPWShow(int len) {
        String msg = "";
        for (int i = 0; i < len; i++) {
            msg += "*";
        }
        tv_pinpad_pw.setText("password:" + msg);
    }

    public void submit() {
        try {
            ServiceManager.getInstence().getPinpad().setOnPinInputListener(new OnPinInputListener() {
                @Override
                public void onInput(int i, int i1) {
                    //3.Monitor password keyboard keys
                    setPWShow(i);
                }

                @Override
                public void onError(int i) {
                    dismiss();
//                    showResult("pinpad error");
                    if(listener!=null){
                        listener.onError(i);
                    }
                }

                @Override
                public void onConfirm(byte[] bytes, boolean b) {
                    //3.Monitor password keyboard keys
                    dismiss();
//                    showResult("Encrypted password:" + new String(bytes));
                    if(listener!=null){
                        listener.onConfirm(bytes,b);
                    }
                }

                @Override
                public void onCancel()  {
                    //3.Monitor password keyboard keys
                    dismiss();
                    if(listener!=null){
                        listener.onCancel();
                    }
                }

                @Override
                public void onPinpadShow(byte[] bytes)  {
                    //2.drawKeyboard
                    drawKeyboard(bytes);
                }
            });
            //1.Start screen Trusteeship   new byte[]{0, 6}== Allow password length 0 and 6
            //double 6 of end will make pinpad auto confirm if you send new byte[]{0,6,6}
            switch (pinpad_version)
            {
                case PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3:
                    LogUtil.i(getClass(),"enter pinpad interface version 3.0");
                    ServiceManager.getInstence().getPinpad().inputOnlinePinByArea(area,tmkindex,cardNumber, new byte[]{0, 6});
                    break;
                case PinpadInterfaceVersion.PINPAD_INTERFACE_DUKPT:
                    LogUtil.i(getClass(),"enter pinpad interface dukpt");
                    ServiceManager.getInstence().getPinpad().inputOnlinePinDukpt(groupId, mode,timeout, cardNumber, new byte[]{0, 6});
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showForPW(String cardNumber) {
        this.cardNumber = cardNumber;
        this.show();
        submit();
    }

    private void drawKeyboard(byte[] bytes) {
        try {
            pwKeyboard.setVisibility(View.VISIBLE);
            pwKeyboard.setKeyShow(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
