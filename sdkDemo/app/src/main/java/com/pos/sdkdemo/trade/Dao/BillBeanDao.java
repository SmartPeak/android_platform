package com.pos.sdkdemo.trade.Dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.pos.sdkdemo.trade.bean.BillBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BILL_BEAN".
*/
public class BillBeanDao extends AbstractDao<BillBean, Long> {

    public static final String TABLENAME = "BILL_BEAN";

    /**
     * Properties of entity BillBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BatchNo = new Property(0, Long.class, "batchNo", true, "BATCH_NO");
        public final static Property DAmount = new Property(1, String.class, "dAmount", false, "D_AMOUNT");
        public final static Property CAmount = new Property(2, String.class, "cAmount", false, "C_AMOUNT");
        public final static Property DebitNum = new Property(3, int.class, "debitNum", false, "DEBIT_NUM");
        public final static Property CreditNum = new Property(4, int.class, "creditNum", false, "CREDIT_NUM");
        public final static Property IsSettlement = new Property(5, boolean.class, "isSettlement", false, "IS_SETTLEMENT");
        public final static Property IsFlat = new Property(6, String.class, "isFlat", false, "IS_FLAT");
    }


    public BillBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BillBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BILL_BEAN\" (" + //
                "\"BATCH_NO\" INTEGER PRIMARY KEY ," + // 0: batchNo
                "\"D_AMOUNT\" TEXT," + // 1: dAmount
                "\"C_AMOUNT\" TEXT," + // 2: cAmount
                "\"DEBIT_NUM\" INTEGER NOT NULL ," + // 3: debitNum
                "\"CREDIT_NUM\" INTEGER NOT NULL ," + // 4: creditNum
                "\"IS_SETTLEMENT\" INTEGER NOT NULL ," + // 5: isSettlement
                "\"IS_FLAT\" TEXT);"); // 6: isFlat
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BILL_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BillBean entity) {
        stmt.clearBindings();
 
        Long batchNo = entity.getBatchNo();
        if (batchNo != null) {
            stmt.bindLong(1, batchNo);
        }
 
        String dAmount = entity.getDAmount();
        if (dAmount != null) {
            stmt.bindString(2, dAmount);
        }
 
        String cAmount = entity.getCAmount();
        if (cAmount != null) {
            stmt.bindString(3, cAmount);
        }
        stmt.bindLong(4, entity.getDebitNum());
        stmt.bindLong(5, entity.getCreditNum());
        stmt.bindLong(6, entity.getIsSettlement() ? 1L: 0L);
 
        String isFlat = entity.getIsFlat();
        if (isFlat != null) {
            stmt.bindString(7, isFlat);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BillBean entity) {
        stmt.clearBindings();
 
        Long batchNo = entity.getBatchNo();
        if (batchNo != null) {
            stmt.bindLong(1, batchNo);
        }
 
        String dAmount = entity.getDAmount();
        if (dAmount != null) {
            stmt.bindString(2, dAmount);
        }
 
        String cAmount = entity.getCAmount();
        if (cAmount != null) {
            stmt.bindString(3, cAmount);
        }
        stmt.bindLong(4, entity.getDebitNum());
        stmt.bindLong(5, entity.getCreditNum());
        stmt.bindLong(6, entity.getIsSettlement() ? 1L: 0L);
 
        String isFlat = entity.getIsFlat();
        if (isFlat != null) {
            stmt.bindString(7, isFlat);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BillBean readEntity(Cursor cursor, int offset) {
        BillBean entity = new BillBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // batchNo
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dAmount
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // cAmount
            cursor.getInt(offset + 3), // debitNum
            cursor.getInt(offset + 4), // creditNum
            cursor.getShort(offset + 5) != 0, // isSettlement
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // isFlat
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BillBean entity, int offset) {
        entity.setBatchNo(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDAmount(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCAmount(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDebitNum(cursor.getInt(offset + 3));
        entity.setCreditNum(cursor.getInt(offset + 4));
        entity.setIsSettlement(cursor.getShort(offset + 5) != 0);
        entity.setIsFlat(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BillBean entity, long rowId) {
        entity.setBatchNo(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BillBean entity) {
        if(entity != null) {
            return entity.getBatchNo();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BillBean entity) {
        return entity.getBatchNo() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
