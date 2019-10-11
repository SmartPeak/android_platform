package com.pos.sdkdemo.trade.net8583;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.PosUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class SignOut8583 extends Trade8583Package {
    @Override
    public Iso8583Manager package8583(Map datas, Card card) {
        Iso8583Manager iso = super.package8583(datas, card);
        try {
            iso.setBit("msgid", "0820");
            iso.setBit(11, PosUtil.getTraceAuto(DemoApplication.getInstance()));
            String batch= (String) datas.get("batch");
            Log.e("feeling", "batch:"+batch);
            iso.setBit(60, "00" + batch + "002");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return iso;
    }
}
