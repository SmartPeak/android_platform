package com.pos.sdkdemo.trade.Dao.db;

import android.util.Log;

import com.pos.sdkdemo.trade.Dao.BillBeanDao;
import com.pos.sdkdemo.trade.Dao.DaoManager;
import com.pos.sdkdemo.trade.Dao.TransactionDataDao;
import com.pos.sdkdemo.trade.bean.BillBean;

public class BillBeanDB {

    public static BillBeanDao db() {
        return DaoManager.getDaoSession().getBillBeanDao();
    }

    public static BillBean selectBillByID (Long id){
        BillBean billBean = null;
        billBean = db().queryBuilder().where(BillBeanDao.Properties.BatchNo.eq(id)).unique();
        return billBean;
    }
    public static void billInsert(BillBean obj) {
        try {
            db().save(obj);
            Log.e("feeling", "billInsert: success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveOrUpdata(BillBean obj) throws Exception {
        try {
            db().save(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
