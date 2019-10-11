package com.pos.sdkdemo.trade.net8583;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.PosUtil;

import java.util.Map;

public class BatchUpEnd8583 extends Trade8583Package {
    @Override
    public Iso8583Manager package8583(Map datas, Card card) {
        Iso8583Manager iso = super.package8583(datas, card);
        try{
            iso.setBit("msgid", "0320");
            String field48 = String.format("%04d", datas.get("field48"));
            iso.setBit(48, field48);
            String batch = PosUtil.getBatch(DemoApplication.getInstance());
            datas.put("batch", batch);
            String field60_3 = (String) datas.get("field60_3");
            iso.setBit(60, "00" + batch + field60_3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return iso;
    }
}
