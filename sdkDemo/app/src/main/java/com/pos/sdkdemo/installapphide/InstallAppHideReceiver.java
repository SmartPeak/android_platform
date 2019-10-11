package com.pos.sdkdemo.installapphide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.basewin.log.LogUtil;
import com.pos.sdkdemo.interfaces.OnInstallAppListener;

/**
 * Created by Administrator on 2016/12/8.
 */

public class InstallAppHideReceiver extends BroadcastReceiver {
    private String packageName;
    protected static int respCode;
    private final String TAG = this.getClass().getSimpleName();
    private OnInstallAppListener listener = null;
    private Context mContext = null;

    public InstallAppHideReceiver(Context context, OnInstallAppListener onInstallAppListener)
    {
        this.listener = onInstallAppListener;
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.INSTALL_APP_HIDE")){
            packageName = intent.getStringExtra("packageName");
            respCode = intent.getIntExtra("respCode",-100);
            LogUtil.i(getClass(), "intent = "+intent.toString());
            LogUtil.i(getClass(), "respCode = "+respCode);
            listener.onInstallAppCode(respCode);
        }
    }


}
