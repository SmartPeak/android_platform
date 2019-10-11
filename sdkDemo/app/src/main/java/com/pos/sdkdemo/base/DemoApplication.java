package com.pos.sdkdemo.base;

import android.app.Application;

import com.basewin.database.DataBaseManager;
import com.basewin.log.LogUtil;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.trade.Dao.DaoManager;
import com.pos.sdkdemo.utils.GlobalData;

/**
 * Created by Administrator on 2016/11/30.
 */

public class DemoApplication extends Application{
    private static final String TAG = "DemoApplication";
    private static DemoApplication instance;

    public static DemoApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        /**
         * init database
         */
        DataBaseManager.getInstance().init(getApplicationContext());
        DaoManager.init(getApplicationContext());
        /**
         * init the GlobalData cashe
         */
        GlobalData.getInstance().init(this);
        ServiceManager.getInstence().init(getApplicationContext());
        LogUtil.openAllLog();
    }

}
