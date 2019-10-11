package com.pos.sdkdemo.trade;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.SpUtil;

public class Config {
    public static final String AMOUNT="amount";
    public static final String TRANS_TYPE="transType";

    public static String TerminalNo="12345678";
    public static String MerchantNo="123456789012345";
    public static String IP="140.206.168.98";
    public static String PORT="4900";
    public static String OPERATOR_NO="01";
    public static boolean SupportSSL = false;
    public static boolean NeedTDKey = false;
    public static boolean isRSA = true;

    public static String TPDU="6000030000";
    public static final String CurrencyCode="156";
    public static final String versionNum = "320003";
    public static final String HEADER = "613200" + versionNum;
    public static final String APP_VER = "310";
    public static final String MAIN_KEY= "22222222222222222222222222222222";


    //trace 流水号
    public static String getTrace(Context context) {
        return SpUtil.getInstance(context).getString("trace","000001");
    }

    public static void setTrace(Context context,String msg) {
        SpUtil.getInstance(context).putString("trace",msg);
    }

    //batch 批次号
    public static String getBatch(Context context) {
        return SpUtil.getInstance(context).getString("batch","000002");
    }

    public static void setBatch(Context context,String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.e("feeling","设置批次号出错 数据为null");
            return;
        }
        String s = PosUtil.numToStr6(msg);
        SpUtil.getInstance(context).putString("batch", s);
    }

    public static void setIp(Context context,String ip){
        if (TextUtils.isEmpty(ip)) {
            return;
        }
        SpUtil.getInstance(context).putString("ip", ip);
    }

    public static String getIp(Context context){
        return SpUtil.getInstance(context).getString("ip",IP);
    }

    public static void setPort(Context context,String ip){
        if (TextUtils.isEmpty(ip)) {
            return;
        }
        SpUtil.getInstance(context).putString("port", ip);
    }

    public static String getPort(Context context){
        return SpUtil.getInstance(context).getString("port",PORT);
    }

    public static void setMerchantNo(Context context,String ip){
        if (TextUtils.isEmpty(ip)) {
            return;
        }
        SpUtil.getInstance(context).putString("MerchantNo", MerchantNo);
    }

    public static String getMerchantNo(Context context){
        return SpUtil.getInstance(context).getString("MerchantNo",MerchantNo);
    }

    public static void setTerminalNo(Context context,String terminal){
        if (TextUtils.isEmpty(terminal)) {
            return;
        }
        SpUtil.getInstance(context).putString("TerminalNo", terminal);
    }

    public static String getTerminalNo(Context context){
        return SpUtil.getInstance(context).getString("TerminalNo",TerminalNo);
    }
}
