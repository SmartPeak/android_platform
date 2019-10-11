package com.pos.sdkdemo.trade.net8583;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.PosUtil;

import java.util.Map;

public class Settlement8583 extends Trade8583Package {
    @Override
    public Iso8583Manager package8583(Map datas, Card card) {
        Iso8583Manager iso = super.package8583(datas, card);
        try {
            iso.setBit("msgid", "0500");
            iso.setBit(48, (String) datas.get("field48"));
            iso.setBit(49, "156");
            String batch = PosUtil.getBatch(DemoApplication.getInstance());
            datas.put("batch", batch);
            iso.setBit(60, "00" + batch + "201");
            iso.setBit(63, Config.OPERATOR_NO + " ");
        }catch (Exception e){

        }
        return iso;
    }
}
