package com.pos.sdkdemo.trade.net8583;

import android.os.RemoteException;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.Dao.db.TransactionDataDB;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.trade.bean.TransactionData;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.TradeEncUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

public class SaleCancel8583 extends Trade8583Package {

    @Override
    public Iso8583Manager package8583(Map datas, Card card) {
        Iso8583Manager iso = super.package8583(datas, card);
        String trace = (String) datas.get("oldTrace");
        TransactionData tranByTraceNO;
        try {
            tranByTraceNO = TransactionDataDB.selectByTrace(trace);
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        String field22;
        boolean isIC;
        String track2;
        String track3;
        String field2;
        String field23;
        String field14;
        try {
            iso.setBit("msgid", "0200");
            iso.setBit(3, "200000");
            iso.setBit(4, tranByTraceNO.getAmount());
            field14 = card.getExpDate();
            isIC = card.isIC();
            field22 = PosUtil._22(card);
            track2 = card.getTrack2ToD();
            track3 = card.getTrack3ToD();
            field2 = card.getPan();
            field23 = card.get23();
            iso.setBit(14, field14);
            iso.setBit(22, field22);
            if (!isIC) {
                iso.setBit(36, track3);
            } else {
                iso.setBit(2, field2);
                iso.setBit(23, field23);
            }
            iso.setBit(25, "00");
            iso.setBit(26, "12");

            iso.setBit(35, track2);
            iso.setBit(37, tranByTraceNO.getReferenceNo());
            iso.setBit(49, "156");

            byte[] pin = (byte[]) datas.get("pin");
            if (pin != null) {
                iso.setBinaryBit(52, BCDHelper.StrToBCD(new String(pin)));
            }
            iso.setBit(53, PosUtil.getSecureSession(field22));
            String batch = PosUtil.getBatch(DemoApplication.getInstance());
            datas.put("batch", batch);
            iso.setBit(60, "22" + batch + "000" + "6" + "01");
            iso.setBit(61, batch + tranByTraceNO.getTrace());
            byte[] macInput = iso.getMacData("msgid", "63");
            String mac = TradeEncUtil.getMacECB(macInput);
            iso.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return iso;
    }
}
