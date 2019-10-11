package com.pos.sdkdemo.trade.Dao.db;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BCDHelper;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.Dao.DaoManager;
import com.pos.sdkdemo.trade.Dao.TransactionDataDao;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.trade.bean.TransactionData;
import com.pos.sdkdemo.utils.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.pos.sdkdemo.trade.TransTypeConstant.getNameByTransType;
import static com.pos.sdkdemo.utils.DateUtil.FORMAT_Y;

public class TransactionDataDB {

    public static TransactionDataDao db() {
        return DaoManager.getDaoSession().getTransactionDataDao();
    }

    /**
     * 获取该流水号的交易
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static TransactionData selectByTrace(String msg) throws Exception {
        TransactionData unique = db().queryBuilder().where(TransactionDataDao.Properties.Trace.eq(msg)).unique();
        return unique;
    }

    public static List<TransactionData> selectAll() throws Exception {
        return db().queryBuilder().list();
    }

    /**
     * 查找所有有效的交易
     */
    public static List<TransactionData> selectAllValid() throws Exception {
        List<TransactionData> list = db().queryBuilder()
                .where(TransactionDataDao.Properties.Status.in("+", "-"), TransactionDataDao.Properties._39.eq("00"))
                .list();
        return list;
    }

    //查找所有无效的交易
    public static List<TransactionData> selectAllNotValid() throws Exception {
        List<TransactionData> list = db().queryBuilder()
                .whereOr(TransactionDataDao.Properties._39.notEq("00"), TransactionDataDao.Properties._39.isNull())
                .list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list;
    }

    public static void deleteAll() {
        db().deleteAll();
    }

    /**
     * 打印数据库中的所有数据
     */
    public static void showDbAll() {
        List<TransactionData> tds = null;
        try {
            tds = selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showDb(tds);
    }

    public static void showDb(List<TransactionData> tds) {
        Log.e("feeling","-------------------------showDb startLifecycle---------------------");
        if (tds != null && tds.size() > 0) {
            for (TransactionData td : tds) {
                Log.e("feeling", td.toString());
            }
        }
        Log.e("feeling","-------------------------showDb end---------------------");
    }

    /**
     * 更新或者保存
     */
    public static void update(TransactionData data) throws Exception {
        db().save(data);
    }

    public static void save(Iso8583Manager iso) {
        boolean result = "00".equals(iso.getBit(39));//result为true，表明后台返回交易成功；否则，表明出于报文上送前

        Map map = GlobleData.getInstance().datas;
        TransactionData transactionData = null;
        Card card = GlobleData.getInstance().card;

        String pan;
        String expDate;
        try {
            Log.e("feeling", (String) map.get("trace"));
            transactionData = TransactionDataDB.selectByTrace((String) map.get("trace"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (transactionData == null) {
            transactionData = new TransactionData();
        }

        int transType = ((Integer) map.get("transType")).intValue();

        transactionData.setTransType(transType);

        transactionData.set_39(iso.getBit(39));

        String bit39 = iso.getBit(39);
        transactionData.set_39(bit39);
        if (TextUtils.isEmpty(bit39)) {
            transactionData.setProcCode(iso.getBit(3));
        }

        transactionData.setTrace(iso.getBit(11));//流水号
        transactionData.setBatch((String) map.get("batch"));//批次号

        transactionData.setName(getNameByTransType(DemoApplication.getInstance(),transType));//交易名称
        transactionData.setPan(card.getPan());
        transactionData.setExpDate(card.getExpDate());//卡有效期
        //去掉39域的条件判断，电子现金交易没有39域，但依然要保存55域

        transactionData.setField55(card.get55());

        transactionData.setAmount(iso.getBit(4));//交易金额
        if (result) {
            transactionData.setSettleDate(iso.getBit(15));//结算日期
        }
        String field22 = iso.getBit(22);
        if (field22 != null && ("01".equals(field22.substring(0, 2)) || "02".equals(field22.substring(0, 2)) || "05".equals(field22.substring(0, 2)) || "07".equals(field22.substring(0, 2)))) {
            transactionData.setServiceCode(field22);
        }

        //交易报文发送前或电子现金交易都要保存
        if (!result) {
            try {
                JSONObject json = new JSONObject();
                if (map.get("noPINFlag") != null) {
                    transactionData.setNoPINFlag((boolean) map.get("noPINFlag"));
                }
                if (map.get("noSignFlag") != null) {
                    transactionData.setNoSignFlag((boolean) map.get("noSignFlag"));
                }
                String AID = (String) map.get("AID");
                if (TextUtils.isEmpty(AID)) {
                    byte[] aid = new byte[0];
                    try {
                        aid = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x4F);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (aid != null) {
                        AID = BCDHelper.bcdToString(aid, 0, aid.length);
                    } else {
                        AID = "";
                    }
                }
                json.put("AID", AID);

                String TC = (String) map.get("TC");
                if (TextUtils.isEmpty(AID)) {
                    byte[] tc = new byte[0];
                    try {
                        tc = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9F26);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (tc != null) {
                        TC = BCDHelper.bcdToString(tc, 0, tc.length);
                    } else {
                        TC = "";
                    }
                }
                json.put("TC", TC);

                String CSN = (String) map.get("CSN");
                if (TextUtils.isEmpty(CSN)) {
                    byte[] csn = new byte[0];
                    try {
                        csn = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x5F34);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (csn != null) {
                        CSN = BCDHelper.bcdToString(csn, 0, csn.length);
                    } else {
                        CSN = "";
                    }
                }
                json.put("CSN", CSN);

                String CVM = (String) map.get("CVM");
                if (TextUtils.isEmpty(CVM)) {
                    byte[] cvm = new byte[0];
                    try {
                        cvm = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9F34);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (cvm != null) {
                        CVM = BCDHelper.bcdToString(cvm, 0, cvm.length);
                    } else {
                        CVM = "";
                    }
                }
                json.put("CVM", CVM);

                String TSI = (String) map.get("TSI");
                if (TextUtils.isEmpty(TSI)) {
                    byte[] tsi = new byte[0];
                    try {
                        tsi = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9B);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (tsi != null) {
                        TSI = BCDHelper.bcdToString(tsi, 0, tsi.length);
                    } else {
                        TSI = "";
                    }
                }
                json.put("TSI", TSI);

                String TVR = (String) map.get("TVR");
                if (TextUtils.isEmpty(TVR)) {
                    byte[] tvr = new byte[0];
                    try {
                        tvr = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x95);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (tvr != null) {
                        TVR = BCDHelper.bcdToString(tvr, 0, tvr.length);
                    } else {
                        TVR = "";
                    }
                }
                json.put("TVR", TVR);

                String ATC = (String) map.get("ATC");
                if (TextUtils.isEmpty(ATC)) {
                    byte[] atc = new byte[0];
                    try {
                        atc = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9f36);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (atc != null) {
                        ATC = BCDHelper.bcdToString(atc, 0, atc.length);
                    } else {
                        ATC = "";
                    }
                }
                json.put("ATC", ATC);

                String Unpr_Num = (String) map.get("Unpr_Num");
                if (TextUtils.isEmpty(Unpr_Num)) {
                    byte[] unpr_num = new byte[0];
                    try {
                        unpr_num = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9F37);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (unpr_num != null) {
                        Unpr_Num = BCDHelper.bcdToString(unpr_num, 0, unpr_num.length);
                    } else {
                        Unpr_Num = "";
                    }
                }
                json.put("Unpr_Num", Unpr_Num);

                String AIP = (String) map.get("AIP");
                if (TextUtils.isEmpty(AIP)) {
                    byte[] aip = new byte[0];
                    try {
                        aip = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x82);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (aip != null) {
                        AIP = BCDHelper.bcdToString(aip, 0, aip.length);
                    } else {
                        AIP = "";
                    }
                }
                json.put("AIP", AIP);

                String Term_Cap = (String) map.get("Term_Cap");
                if (TextUtils.isEmpty(Term_Cap)) {
                    byte[] term_cap = new byte[0];
                    try {
                        term_cap = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9f33);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (term_cap != null) {
                        Term_Cap = BCDHelper.bcdToString(term_cap, 0, term_cap.length);
                    } else {
                        Term_Cap = "";
                    }
                }
                json.put("Term_Cap", Term_Cap);

                String IAD = (String) map.get("IAD");
                if (TextUtils.isEmpty(IAD)) {
                    byte[] iad = new byte[0];
                    try {
                        iad = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9f10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (iad != null) {
                        IAD = BCDHelper.bcdToString(iad, 0, iad.length);
                    } else {
                        IAD = "";
                    }
                }
                json.put("IAD", IAD);

                String APN = (String) map.get("APN");
                if (TextUtils.isEmpty(APN)) {
                    byte[] apn = new byte[0];
                    try {
                        apn = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9f12);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (apn != null) {
                        APN = BCDHelper.bcdToString(apn, 0, apn.length);
                    } else {
                        APN = "";
                    }
                }
                json.put("APN", APN);

                String APP_LABEL = (String) map.get("APP_LABEL");
                if (TextUtils.isEmpty(APP_LABEL)) {
                    byte[] app = new byte[0];
                    try {
                        app = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (app != null) {
                        try {
                            APP_LABEL = new String(BCDHelper.stringToBcd(BCDHelper.bcdToString(app, 0, app.length), BCDHelper.bcdToString(app, 0, app.length).length()), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    } else {
                        APP_LABEL = "";
                    }
                }
                json.put("APP_LABEL", APP_LABEL);

                String ARQC = (String) map.get("ARQC");
                if (TextUtils.isEmpty(ARQC)) {
                    byte[] arqc = new byte[0];
                    try {
                        arqc = ServiceManager.getInstence().getPboc().getEmvTlvData((short) 0x9f26);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (arqc != null) {
                        ARQC = BCDHelper.bcdToString(arqc, 0, arqc.length);
                    } else {
                        ARQC = "";
                    }
                }
                json.put("ARQC", ARQC);
                String data = json.toString();
                transactionData.setEmvIcData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!result) {
            transactionData.setCardSn(iso.getBit(23));//卡序列号
            transactionData.setCurrency(iso.getBit(49));//货币代码
            transactionData.setTrack2(iso.getBit(35));//二磁道
            transactionData.setTrack3(iso.getBit(36));//三磁道
            transactionData.setField61(iso.getBit(61));
            transactionData.setField63(iso.getBit(63));
        }

        long timeMillis = Calendar.getInstance().getTimeInMillis();
        transactionData.setTimeStamp(timeMillis);
        if (TextUtils.isEmpty(iso.getBit(13)) && TextUtils.isEmpty(iso.getBit(12))) {
            transactionData.setDate(DateUtil.getCurDate());//交易日期
            transactionData.setTime(DateUtil.getCurDateStr());//交易时间
        } else {
            transactionData.setDate(iso.getBit(13));//交易日期
            transactionData.setTime(iso.getBit(12));//交易时间
        }
        if (result) {
            transactionData.setField53(iso.getBit(53));
        }
        transactionData.setYear(DateUtil.getCurDateStr(FORMAT_Y));//年份
        String _32 = iso.getBit(32);
        if (!TextUtils.isEmpty(_32)) {
            transactionData.setAIICode(_32);
        }
        if (result) {
            transactionData.setAuthCode(iso.getBit(38));//授权码
        }
        if (result) {
            String status = TransTypeConstant.getStatusByTransType(transType);
            String settleType = TransTypeConstant.getSettleTypeByTransType(transType);
            transactionData.setStatus(status);//消费 撤销退货
            transactionData.setSettleType(settleType);
            if (status.equals("-")) {
                try {
                    String oldTrace = (String) map.get("oldTrace");
                    TransactionData transactionData1 = selectByTrace(oldTrace);
                    transactionData1.setStatus(status);
                    update(transactionData1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            transactionData.setStatus("");
            transactionData.setSettleType("");
        }

        String field44 = iso.getBit(44);
        if (field44 != null && field44.length() > 0) {
            Log.d("feeling","接收机构和收单机构 field44=" + field44);
            transactionData.setIssuerId(field44.substring(0, 11));//发卡行ID
            transactionData.setAcqId(field44.substring(11));//收单行ID
        } else {
            transactionData.setAcqId("");//收卡行ID
            transactionData.setIssuerId("");//发卡行ID
        }
        if (result) {
            transactionData.setReferenceNo(iso.getBit(37));//参考号
        }
        transactionData.setSignDataStatus("");//电子签名数据
        transactionData.setOperator(Config.OPERATOR_NO);//操作员
        if (TransTypeConstant.hasOLDTRACE(transType)) {
            transactionData.setOldTrace((String) map.get("oldTrace"));//原凭证号
        }
        if (TransTypeConstant.isREFUND(transType)) {
            transactionData.setOldDate((String) map.get("oldDate"));//原交易日期
            transactionData.setOldReference((String) map.get("oldReference"));//原交易参考号
        }
        if (TransTypeConstant.hasOLDAUTHCODE(transType)) {
            transactionData.setOldAuthCode((String) map.get("authCode"));
        }

        if (map.get("scriptResult") != null) {
            transactionData.setScriptFlag(true);
            transactionData.setScriptResult((String) map.get("scriptResult"));
        }

        try {
            update(transactionData);
            Log.d("feeling", "save db SUCCESS...");
        } catch (Exception e) {
            Log.d("feeling", "save db FAILURE...");
            e.printStackTrace();
        }
    }


}
