package com.pos.sdkdemo.trade.net8583;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.PosUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Trade8583Package extends Base8583Package {

    private Iso8583Manager iso8583Manager = null;

    public Trade8583Package() {
        super();
        init8583();
    }

    @Override
    public Iso8583Manager init8583() {
        iso8583Manager = new Iso8583Manager(DemoApplication.getInstance());
        iso8583Manager.restore8583XMLconfig();
        return iso8583Manager;
    }

    @Override
    public Iso8583Manager package8583(Map datas, Card card){
        try {
            iso8583Manager.setBit("tpdu", Config.TPDU);
            iso8583Manager.setBit("header", Config.HEADER);
            String trace = PosUtil.getTraceAuto(DemoApplication.getInstance());
            iso8583Manager.setBit(11, trace);
            iso8583Manager.setBit("41", Config.TerminalNo);
            iso8583Manager.setBit("42", Config.MerchantNo);
            datas.put("trace", trace);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return iso8583Manager;
    }
}
