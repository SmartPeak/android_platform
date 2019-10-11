package com.pos.sdkdemo.trade.net8583;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.TradeEncUtil;

import java.util.Map;

public class AuthCancel8583 extends Trade8583Package {
    @Override
    public Iso8583Manager package8583(Map datas, Card card) {
        Iso8583Manager iso = super.package8583(datas, card);
        try{
            iso.setBit("msgid", "0100");
            iso.setBit(3, "200000");
            String s = PosUtil.yuanTo12((String) datas.get("amount"));
            iso.setBit(4, s);
            if (card.getExpDate().length() > 0) {
                iso.setBit(14, card.getExpDate());
            }
            String _22 = PosUtil._22(card);
            iso.setBit(22, _22);

            iso.setBit(25, "06");
            iso.setBit(26, "12");

            iso.setBit(35, card.getTrack2ToD());
            iso.setBit(38, (String) datas.get("authCode"));

            if (card.isIC()) {
                iso.setBit(2, card.getPan());
                iso.setBit(23, card.get23());
                iso.setBinaryBit(55, BCDHelper.StrToBCD(card.get55()));
            } else {
                iso.setBit(36, card.getTrack3ToD());
            }

            byte[] pin = card.password;
            if (pin != null) {
                iso.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            }
            iso.setBit(49, "156");

            iso.setBit(53, PosUtil.getSecureSession(_22));
            String batch = PosUtil.getBatch(DemoApplication.getInstance());
            datas.put("batch",batch);
            iso.setBit(60, "11" + batch + "000" + "6" + "0");
            iso.setBit(61, "000000" + "000000" + datas.get("oldDate"));
            byte[] macInput = iso.getMacData("msgid", "63");
            String mac = TradeEncUtil.getMacECB(macInput);
            iso.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        }catch (Exception e){

        }
        return iso;
    }
}
