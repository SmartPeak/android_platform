package com.pos.sdkdemo.installapphide;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.basewin.define.ConstParam;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.interfaces.OnInstallAppListener;

import java.io.File;

/**
 * Created by lyw on 2016/12/8.
 * Install applications without warning
 */

public class InstallAppHideActivity extends com.pos.sdkdemo.base.BaseActivity implements View.OnClickListener {
    private Button btn_installApp;
    private InstallAppHideReceiver receiver = null;
    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_installapp, null);
    }

    @Override
    protected void onInitView()
    {
        initView();
    }


    private void initView() {
        btn_installApp = (Button) findViewById(R.id.btn_installApp);
        btn_installApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_installApp:
                installApp();
                break;
        }
    }

    public void installApp(){
        //注册广播监听
        receiver = new InstallAppHideReceiver(this, new OnInstallAppListener(){
            @Override
            public void onInstallAppCode(int respCode) {
                switch (respCode){
                    case InstallStatus.PACKAGE_INSTALL_SUCCESS:
                        LOGD("installing app success!");
                        break;
                    case InstallStatus.ERROR_PACKAGE_INSTALL_FAILED:
                        LOGD("install app falied:"+"Default failed");
                        break;
                    case InstallStatus.ERROR_PACKAGE_INSTALL_FAILED_INVALID_APK:
                        LOGD("install app falied:"+"Illegal APK type");
                        break;
                    case InstallStatus.ERROR_PACKAGE_INSTALL_FAILED_PERMISSION_FAILED:
                        LOGD("install app falied:"+"Insufficient installation rights");
                        break;
                    case InstallStatus.ERROR_PACKAGE_INSTALL_FAILED_NO_SPACE:
                        LOGD("install app falied:"+"Insufficient space");
                        break;
                    case InstallStatus.ERROR_PACKAGE_INSTALL_FAILED_SIGNATURE_FAILED:
                        LOGD("install app falied:"+"Signature faided");
                        break;
                    case InstallStatus.ERROR_PACKAGE_INSTALL_FAILED_VERSION_DOWNGRADE:
                        LOGD("install app falied:"+"A higher version of the same name is already installed");
                        break;
                }
            }
        });
        IntentFilter filter = new IntentFilter("android.intent.action.INSTALL_APP_HIDE");
        registerReceiver(receiver, filter);

        Intent intent =new Intent("android.intent.action.VIEW.HIDE");
//        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/IDCertificationDemo.apk")),"application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(new File(ConstParam.SD_Path+"IDCertificationDemo.apk")),"application/vnd.android.package-archive");
        startService(intent);
        LOGD("start installApp");
    }

}
