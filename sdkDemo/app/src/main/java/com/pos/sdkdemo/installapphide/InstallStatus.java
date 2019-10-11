package com.pos.sdkdemo.installapphide;

/**
 * Created by Administrator on 2016/12/8.
 */

public class InstallStatus {
    //Installation success responsing code
    public static final int PACKAGE_INSTALL_SUCCESS = 0;
    //Default failed
    public static final int ERROR_PACKAGE_INSTALL_FAILED = -100;
    //Illegal APK type
    public static final int ERROR_PACKAGE_INSTALL_FAILED_INVALID_APK = -101;
    //Insufficient installation rights
    public static final int ERROR_PACKAGE_INSTALL_FAILED_PERMISSION_FAILED = -102;
    //Insufficient space
    public static final int ERROR_PACKAGE_INSTALL_FAILED_NO_SPACE = -103;
    //Insufficient installation rights
    public static final int ERROR_PACKAGE_INSTALL_FAILED_SIGNATURE_FAILED = -104;
    //A higher version of the same name is already installed.
    public static final int ERROR_PACKAGE_INSTALL_FAILED_VERSION_DOWNGRADE = -107;

}
