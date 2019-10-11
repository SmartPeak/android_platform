package com.pos.sdkdemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Administrator on 2016/10/28.
 */

public abstract class BaseAidlService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public abstract Binder getBinder();
}
