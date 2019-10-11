package com.pos.sdkdemo.trade.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DaoManager {
    //create database name
    private static final String DB_NAME = "SDKDemo.db";

    private static DaoSession daoSession = null;

    private static Context mContext;

    public static DaoSession getDaoSession(){
        if (mContext == null) {
            throw new ExceptionInInitializerError("greendao 数据库上下文未初始化！请调用init(Context context)");
        }
        if (daoSession != null) {
            return daoSession;
        }
        DBHelper helper = new DBHelper(mContext, DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }

    public static void init(Context context){
        mContext=context;
    }
}
