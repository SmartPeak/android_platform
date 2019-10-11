package com.pos.sdkdemo.utils;

import android.util.Log;

import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Dao.db.BillBeanDB;
import com.pos.sdkdemo.trade.Dao.db.TransactionDataDB;
import com.pos.sdkdemo.trade.bean.BillBean;
import com.pos.sdkdemo.trade.bean.TransactionData;

import java.math.BigInteger;
import java.util.List;

public class SettleUtil {

    public static void initBill() {
        BillBean billBean = new BillBean();
        String temBatch = PosUtil.getBatch(DemoApplication.getInstance());
        Long batch = Long.valueOf(temBatch);
        List<TransactionData> ls = null;
        try {
            ls = TransactionDataDB.selectAllValid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ls == null || ls.size() == 0) {
            billBean.setBatchNo(batch);
            BillBeanDB.billInsert(billBean);
            return;
        }

        for (int i = 0; i < ls.size(); i++) {
            TransactionData td = ls.get(i);
            String settleType = td.getSettleType();
            if ("1".equals(settleType)) { //借记
                billBean.dAmonutAdd(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                billBean.debitNumAdd();
            } else {//贷记
                billBean.cAmonutAdd(new BigInteger(PosUtil.numToStr12(td.getAmount())));
                billBean.creditNumAdd();
            }
        }

        BillBeanDB.billInsert(billBean);
    }

    public static String getInitField48() {
        BigInteger ddAmount = new BigInteger("000000000000");
        int ddAcount = 0;
        BigInteger dcAmount = new BigInteger("000000000000");
        int dcAcount = 0;

        BigInteger fdAmount = new BigInteger("000000000000");
        int fdAcount = 0;
        BigInteger fcAmount = new BigInteger("000000000000");
        int fcAcount = 0;

        String temBatch = PosUtil.getBatch(DemoApplication.getInstance());
        Long batch = Long.valueOf(temBatch);
        Log.e("feeling", "batch: "+batch);

        BillBean billBean = BillBeanDB.selectBillByID(batch);

        ddAmount = new BigInteger(billBean.getDAmount());
        ddAcount = billBean.getDebitNum();

        dcAmount = new BigInteger(billBean.getCAmount());
        dcAcount = billBean.getCreditNum();

        String field48 = String.format("%012d", ddAmount.longValue()) +
                String.format("%03d", ddAcount) +
                String.format("%012d", dcAmount.longValue()) +
                String.format("%03d", dcAcount) + 0 +
                String.format("%012d", fdAmount.longValue()) +
                String.format("%03d", fdAcount) +
                String.format("%012d", fcAmount.longValue()) +
                String.format("%03d", fcAcount) + 0;
        return field48;
    }

    //更新 内卡外开对账信息
    public static void updateStatement(int n) {
        String naccountsState = getAccountsState(n);
        updateStatement(naccountsState);
    }


    /**
     * 修改内卡外卡 对账 信息
     * @param n 内卡
     */
    public static void updateStatement(String n) {
        String temBatch = PosUtil.getBatch(DemoApplication.getInstance());
        Long batch = Long.valueOf(temBatch);
        BillBean billBean = BillBeanDB.selectBillByID(batch);
        billBean.setIsFlat(n);
        try {
            BillBeanDB.billInsert(billBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAccountsState(int state) {
        String stateStr = "";
        switch (state) {
            case 0:
            case 1:
                stateStr = "平";
                break;
            case 2:
                stateStr = "不平";
                break;
            case 3:
                stateStr = "出错";
                break;
            default:
                break;
        }
        return stateStr;
    }

    public static String getDetial(){
        String data="************************\n" +
                "        Trade Detail Slip      \n" +
                "************************\n";
        try {
            List<TransactionData> tds=TransactionDataDB.selectAllValid();
            for (TransactionData td :
                    tds) {
                data=data+td.getName()+"                Amount:"+PosUtil.changeAmout(td.getAmount())+"\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data= data+"************************\n";
        return data;
    }

    public static String getFailureDetial(){
        String data="************************\n" +
                "    Failure Trade Detail Slip  \n" +
                "************************\n";
        try {
            List<TransactionData> tds=TransactionDataDB.selectAllNotValid();
            for (TransactionData td :
                    tds) {
                data=data+td.getName()+"                Amount:"+PosUtil.changeAmout(td.getAmount())+"\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data= data+"************************\n";
        return data;
    }
}
