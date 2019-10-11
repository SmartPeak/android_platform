package com.pos.sdkdemo.trade.net8583;

import android.os.RemoteException;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.TradeEncUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Sale8583 extends Trade8583Package {
    @Override
    public Iso8583Manager package8583(Map datas, Card card) {
        Iso8583Manager iso8583Manager = super.package8583(datas, card);
        byte[] pin = card.password;
        String amount = (String) datas.get("amount");
        try {
            iso8583Manager.setBit("msgid", "0200");
            if (card.isIC()) {
                iso8583Manager.setBit(2, card.getPan());
                iso8583Manager.setBit(23, card.get23());
                iso8583Manager.setBinaryBit(55, BCDHelper.StrToBCD(card.get55()));
                Log.e("feeling", "original field 55:" + card.get55());
            } else {
                iso8583Manager.setBit(36, card.getTrack3ToD());
            }

            iso8583Manager.setBit(3, "000000");
            String s = PosUtil.yuanTo12(amount);
            iso8583Manager.setBit(4, s);
            if (card != null) {
                iso8583Manager.setBit(14, card.getExpDate());
            }

            String _22 = PosUtil._22(card);
            iso8583Manager.setBit(22, _22);

            iso8583Manager.setBit("25", "00");
            if (pin != null) {
                iso8583Manager.setBit(26, "12");
                iso8583Manager.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            }
            iso8583Manager.setBit("32", "88888");
            iso8583Manager.setBit(35, card.getTrack2ToD());

            iso8583Manager.setBit("41", Config.TerminalNo);
            iso8583Manager.setBit("42", Config.MerchantNo);

            iso8583Manager.setBit("49", "156");
            iso8583Manager.setBit(53, PosUtil.getSecureSession(_22));
            String batch = PosUtil.getBatch(DemoApplication.getInstance());
            datas.put("batch",batch);
            iso8583Manager.setBit(60, "22" + batch +"000" + "6" + "01");
            byte[] macInput = iso8583Manager.getMacData("msgid", "63");
            String mac = TradeEncUtil.getMacECB(macInput);
            iso8583Manager.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return iso8583Manager;
    }
}
