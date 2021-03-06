package com.pos.sdkdemo.trade.Dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.pos.sdkdemo.trade.bean.TransactionData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TRANSACTION_DATA".
*/
public class TransactionDataDao extends AbstractDao<TransactionData, Long> {

    public static final String TABLENAME = "TRANSACTION_DATA";

    /**
     * Properties of entity TransactionData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TransType = new Property(1, int.class, "transType", false, "TRANS_TYPE");
        public final static Property _39 = new Property(2, String.class, "_39", false, "_39");
        public final static Property Status = new Property(3, String.class, "status", false, "STATUS");
        public final static Property SettleType = new Property(4, String.class, "settleType", false, "SETTLE_TYPE");
        public final static Property UploadStatus = new Property(5, String.class, "uploadStatus", false, "UPLOAD_STATUS");
        public final static Property BatchStatus = new Property(6, String.class, "batchStatus", false, "BATCH_STATUS");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
        public final static Property Trace = new Property(8, String.class, "trace", false, "TRACE");
        public final static Property Batch = new Property(9, String.class, "batch", false, "BATCH");
        public final static Property ProcCode = new Property(10, String.class, "procCode", false, "PROC_CODE");
        public final static Property Pan = new Property(11, String.class, "pan", false, "PAN");
        public final static Property Amount = new Property(12, String.class, "amount", false, "AMOUNT");
        public final static Property ExpDate = new Property(13, String.class, "expDate", false, "EXP_DATE");
        public final static Property SettleDate = new Property(14, String.class, "settleDate", false, "SETTLE_DATE");
        public final static Property CardSn = new Property(15, String.class, "cardSn", false, "CARD_SN");
        public final static Property ServiceCode = new Property(16, String.class, "serviceCode", false, "SERVICE_CODE");
        public final static Property MaxPinLen = new Property(17, String.class, "maxPinLen", false, "MAX_PIN_LEN");
        public final static Property Currency = new Property(18, String.class, "currency", false, "CURRENCY");
        public final static Property AIICode = new Property(19, String.class, "aIICode", false, "A_IICODE");
        public final static Property Track2 = new Property(20, String.class, "track2", false, "TRACK2");
        public final static Property Track3 = new Property(21, String.class, "track3", false, "TRACK3");
        public final static Property Field53 = new Property(22, String.class, "field53", false, "FIELD53");
        public final static Property Field55 = new Property(23, String.class, "field55", false, "FIELD55");
        public final static Property Field61 = new Property(24, String.class, "field61", false, "FIELD61");
        public final static Property Field63 = new Property(25, String.class, "field63", false, "FIELD63");
        public final static Property Date = new Property(26, String.class, "date", false, "DATE");
        public final static Property Time = new Property(27, String.class, "time", false, "TIME");
        public final static Property Year = new Property(28, String.class, "year", false, "YEAR");
        public final static Property ReferenceNo = new Property(29, String.class, "referenceNo", false, "REFERENCE_NO");
        public final static Property AuthCode = new Property(30, String.class, "authCode", false, "AUTH_CODE");
        public final static Property OldTrace = new Property(31, String.class, "oldTrace", false, "OLD_TRACE");
        public final static Property OldAuthCode = new Property(32, String.class, "oldAuthCode", false, "OLD_AUTH_CODE");
        public final static Property CardId = new Property(33, String.class, "cardId", false, "CARD_ID");
        public final static Property IssuerMsg = new Property(34, String.class, "issuerMsg", false, "ISSUER_MSG");
        public final static Property CupMsg = new Property(35, String.class, "cupMsg", false, "CUP_MSG");
        public final static Property AquMsg = new Property(36, String.class, "aquMsg", false, "AQU_MSG");
        public final static Property IssuerId = new Property(37, String.class, "issuerId", false, "ISSUER_ID");
        public final static Property AcqId = new Property(38, String.class, "acqId", false, "ACQ_ID");
        public final static Property PosCenter = new Property(39, String.class, "posCenter", false, "POS_CENTER");
        public final static Property Operator = new Property(40, String.class, "operator", false, "OPERATOR");
        public final static Property Remark = new Property(41, String.class, "remark", false, "REMARK");
        public final static Property SignDataStatus = new Property(42, String.class, "signDataStatus", false, "SIGN_DATA_STATUS");
        public final static Property SignDataSend = new Property(43, String.class, "signDataSend", false, "SIGN_DATA_SEND");
        public final static Property SpecialCode = new Property(44, String.class, "specialCode", false, "SPECIAL_CODE");
        public final static Property SignData = new Property(45, byte[].class, "signData", false, "SIGN_DATA");
        public final static Property ScriptResult = new Property(46, String.class, "scriptResult", false, "SCRIPT_RESULT");
        public final static Property SignPath = new Property(47, String.class, "signPath", false, "SIGN_PATH");
        public final static Property SignICData = new Property(48, String.class, "signICData", false, "SIGN_ICDATA");
        public final static Property Sign_phone = new Property(49, String.class, "sign_phone", false, "SIGN_PHONE");
        public final static Property Sign_balance = new Property(50, String.class, "sign_balance", false, "SIGN_BALANCE");
        public final static Property IsQPSTrans = new Property(51, String.class, "isQPSTrans", false, "IS_QPSTRANS");
        public final static Property OldReference = new Property(52, String.class, "oldReference", false, "OLD_REFERENCE");
        public final static Property OldDate = new Property(53, String.class, "oldDate", false, "OLD_DATE");
        public final static Property EmvIcData = new Property(54, String.class, "emvIcData", false, "EMV_IC_DATA");
        public final static Property NeedPrint = new Property(55, boolean.class, "needPrint", false, "NEED_PRINT");
        public final static Property ScriptFlag = new Property(56, boolean.class, "scriptFlag", false, "SCRIPT_FLAG");
        public final static Property NoPINFlag = new Property(57, boolean.class, "noPINFlag", false, "NO_PINFLAG");
        public final static Property NoSignFlag = new Property(58, boolean.class, "noSignFlag", false, "NO_SIGN_FLAG");
        public final static Property TimeStamp = new Property(59, long.class, "timeStamp", false, "TIME_STAMP");
    }


    public TransactionDataDao(DaoConfig config) {
        super(config);
    }
    
    public TransactionDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TRANSACTION_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TRANS_TYPE\" INTEGER NOT NULL ," + // 1: transType
                "\"_39\" TEXT," + // 2: _39
                "\"STATUS\" TEXT," + // 3: status
                "\"SETTLE_TYPE\" TEXT," + // 4: settleType
                "\"UPLOAD_STATUS\" TEXT," + // 5: uploadStatus
                "\"BATCH_STATUS\" TEXT," + // 6: batchStatus
                "\"NAME\" TEXT," + // 7: name
                "\"TRACE\" TEXT," + // 8: trace
                "\"BATCH\" TEXT," + // 9: batch
                "\"PROC_CODE\" TEXT," + // 10: procCode
                "\"PAN\" TEXT," + // 11: pan
                "\"AMOUNT\" TEXT," + // 12: amount
                "\"EXP_DATE\" TEXT," + // 13: expDate
                "\"SETTLE_DATE\" TEXT," + // 14: settleDate
                "\"CARD_SN\" TEXT," + // 15: cardSn
                "\"SERVICE_CODE\" TEXT," + // 16: serviceCode
                "\"MAX_PIN_LEN\" TEXT," + // 17: maxPinLen
                "\"CURRENCY\" TEXT," + // 18: currency
                "\"A_IICODE\" TEXT," + // 19: aIICode
                "\"TRACK2\" TEXT," + // 20: track2
                "\"TRACK3\" TEXT," + // 21: track3
                "\"FIELD53\" TEXT," + // 22: field53
                "\"FIELD55\" TEXT," + // 23: field55
                "\"FIELD61\" TEXT," + // 24: field61
                "\"FIELD63\" TEXT," + // 25: field63
                "\"DATE\" TEXT," + // 26: date
                "\"TIME\" TEXT," + // 27: time
                "\"YEAR\" TEXT," + // 28: year
                "\"REFERENCE_NO\" TEXT," + // 29: referenceNo
                "\"AUTH_CODE\" TEXT," + // 30: authCode
                "\"OLD_TRACE\" TEXT," + // 31: oldTrace
                "\"OLD_AUTH_CODE\" TEXT," + // 32: oldAuthCode
                "\"CARD_ID\" TEXT," + // 33: cardId
                "\"ISSUER_MSG\" TEXT," + // 34: issuerMsg
                "\"CUP_MSG\" TEXT," + // 35: cupMsg
                "\"AQU_MSG\" TEXT," + // 36: aquMsg
                "\"ISSUER_ID\" TEXT," + // 37: issuerId
                "\"ACQ_ID\" TEXT," + // 38: acqId
                "\"POS_CENTER\" TEXT," + // 39: posCenter
                "\"OPERATOR\" TEXT," + // 40: operator
                "\"REMARK\" TEXT," + // 41: remark
                "\"SIGN_DATA_STATUS\" TEXT," + // 42: signDataStatus
                "\"SIGN_DATA_SEND\" TEXT," + // 43: signDataSend
                "\"SPECIAL_CODE\" TEXT," + // 44: specialCode
                "\"SIGN_DATA\" BLOB," + // 45: signData
                "\"SCRIPT_RESULT\" TEXT," + // 46: scriptResult
                "\"SIGN_PATH\" TEXT," + // 47: signPath
                "\"SIGN_ICDATA\" TEXT," + // 48: signICData
                "\"SIGN_PHONE\" TEXT," + // 49: sign_phone
                "\"SIGN_BALANCE\" TEXT," + // 50: sign_balance
                "\"IS_QPSTRANS\" TEXT," + // 51: isQPSTrans
                "\"OLD_REFERENCE\" TEXT," + // 52: oldReference
                "\"OLD_DATE\" TEXT," + // 53: oldDate
                "\"EMV_IC_DATA\" TEXT," + // 54: emvIcData
                "\"NEED_PRINT\" INTEGER NOT NULL ," + // 55: needPrint
                "\"SCRIPT_FLAG\" INTEGER NOT NULL ," + // 56: scriptFlag
                "\"NO_PINFLAG\" INTEGER NOT NULL ," + // 57: noPINFlag
                "\"NO_SIGN_FLAG\" INTEGER NOT NULL ," + // 58: noSignFlag
                "\"TIME_STAMP\" INTEGER NOT NULL );"); // 59: timeStamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TRANSACTION_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TransactionData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTransType());
 
        String _39 = entity.get_39();
        if (_39 != null) {
            stmt.bindString(3, _39);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(4, status);
        }
 
        String settleType = entity.getSettleType();
        if (settleType != null) {
            stmt.bindString(5, settleType);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(6, uploadStatus);
        }
 
        String batchStatus = entity.getBatchStatus();
        if (batchStatus != null) {
            stmt.bindString(7, batchStatus);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String trace = entity.getTrace();
        if (trace != null) {
            stmt.bindString(9, trace);
        }
 
        String batch = entity.getBatch();
        if (batch != null) {
            stmt.bindString(10, batch);
        }
 
        String procCode = entity.getProcCode();
        if (procCode != null) {
            stmt.bindString(11, procCode);
        }
 
        String pan = entity.getPan();
        if (pan != null) {
            stmt.bindString(12, pan);
        }
 
        String amount = entity.getAmount();
        if (amount != null) {
            stmt.bindString(13, amount);
        }
 
        String expDate = entity.getExpDate();
        if (expDate != null) {
            stmt.bindString(14, expDate);
        }
 
        String settleDate = entity.getSettleDate();
        if (settleDate != null) {
            stmt.bindString(15, settleDate);
        }
 
        String cardSn = entity.getCardSn();
        if (cardSn != null) {
            stmt.bindString(16, cardSn);
        }
 
        String serviceCode = entity.getServiceCode();
        if (serviceCode != null) {
            stmt.bindString(17, serviceCode);
        }
 
        String maxPinLen = entity.getMaxPinLen();
        if (maxPinLen != null) {
            stmt.bindString(18, maxPinLen);
        }
 
        String currency = entity.getCurrency();
        if (currency != null) {
            stmt.bindString(19, currency);
        }
 
        String aIICode = entity.getAIICode();
        if (aIICode != null) {
            stmt.bindString(20, aIICode);
        }
 
        String track2 = entity.getTrack2();
        if (track2 != null) {
            stmt.bindString(21, track2);
        }
 
        String track3 = entity.getTrack3();
        if (track3 != null) {
            stmt.bindString(22, track3);
        }
 
        String field53 = entity.getField53();
        if (field53 != null) {
            stmt.bindString(23, field53);
        }
 
        String field55 = entity.getField55();
        if (field55 != null) {
            stmt.bindString(24, field55);
        }
 
        String field61 = entity.getField61();
        if (field61 != null) {
            stmt.bindString(25, field61);
        }
 
        String field63 = entity.getField63();
        if (field63 != null) {
            stmt.bindString(26, field63);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(27, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(28, time);
        }
 
        String year = entity.getYear();
        if (year != null) {
            stmt.bindString(29, year);
        }
 
        String referenceNo = entity.getReferenceNo();
        if (referenceNo != null) {
            stmt.bindString(30, referenceNo);
        }
 
        String authCode = entity.getAuthCode();
        if (authCode != null) {
            stmt.bindString(31, authCode);
        }
 
        String oldTrace = entity.getOldTrace();
        if (oldTrace != null) {
            stmt.bindString(32, oldTrace);
        }
 
        String oldAuthCode = entity.getOldAuthCode();
        if (oldAuthCode != null) {
            stmt.bindString(33, oldAuthCode);
        }
 
        String cardId = entity.getCardId();
        if (cardId != null) {
            stmt.bindString(34, cardId);
        }
 
        String issuerMsg = entity.getIssuerMsg();
        if (issuerMsg != null) {
            stmt.bindString(35, issuerMsg);
        }
 
        String cupMsg = entity.getCupMsg();
        if (cupMsg != null) {
            stmt.bindString(36, cupMsg);
        }
 
        String aquMsg = entity.getAquMsg();
        if (aquMsg != null) {
            stmt.bindString(37, aquMsg);
        }
 
        String issuerId = entity.getIssuerId();
        if (issuerId != null) {
            stmt.bindString(38, issuerId);
        }
 
        String acqId = entity.getAcqId();
        if (acqId != null) {
            stmt.bindString(39, acqId);
        }
 
        String posCenter = entity.getPosCenter();
        if (posCenter != null) {
            stmt.bindString(40, posCenter);
        }
 
        String operator = entity.getOperator();
        if (operator != null) {
            stmt.bindString(41, operator);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(42, remark);
        }
 
        String signDataStatus = entity.getSignDataStatus();
        if (signDataStatus != null) {
            stmt.bindString(43, signDataStatus);
        }
 
        String signDataSend = entity.getSignDataSend();
        if (signDataSend != null) {
            stmt.bindString(44, signDataSend);
        }
 
        String specialCode = entity.getSpecialCode();
        if (specialCode != null) {
            stmt.bindString(45, specialCode);
        }
 
        byte[] signData = entity.getSignData();
        if (signData != null) {
            stmt.bindBlob(46, signData);
        }
 
        String scriptResult = entity.getScriptResult();
        if (scriptResult != null) {
            stmt.bindString(47, scriptResult);
        }
 
        String signPath = entity.getSignPath();
        if (signPath != null) {
            stmt.bindString(48, signPath);
        }
 
        String signICData = entity.getSignICData();
        if (signICData != null) {
            stmt.bindString(49, signICData);
        }
 
        String sign_phone = entity.getSign_phone();
        if (sign_phone != null) {
            stmt.bindString(50, sign_phone);
        }
 
        String sign_balance = entity.getSign_balance();
        if (sign_balance != null) {
            stmt.bindString(51, sign_balance);
        }
 
        String isQPSTrans = entity.getIsQPSTrans();
        if (isQPSTrans != null) {
            stmt.bindString(52, isQPSTrans);
        }
 
        String oldReference = entity.getOldReference();
        if (oldReference != null) {
            stmt.bindString(53, oldReference);
        }
 
        String oldDate = entity.getOldDate();
        if (oldDate != null) {
            stmt.bindString(54, oldDate);
        }
 
        String emvIcData = entity.getEmvIcData();
        if (emvIcData != null) {
            stmt.bindString(55, emvIcData);
        }
        stmt.bindLong(56, entity.getNeedPrint() ? 1L: 0L);
        stmt.bindLong(57, entity.getScriptFlag() ? 1L: 0L);
        stmt.bindLong(58, entity.getNoPINFlag() ? 1L: 0L);
        stmt.bindLong(59, entity.getNoSignFlag() ? 1L: 0L);
        stmt.bindLong(60, entity.getTimeStamp());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TransactionData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTransType());
 
        String _39 = entity.get_39();
        if (_39 != null) {
            stmt.bindString(3, _39);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(4, status);
        }
 
        String settleType = entity.getSettleType();
        if (settleType != null) {
            stmt.bindString(5, settleType);
        }
 
        String uploadStatus = entity.getUploadStatus();
        if (uploadStatus != null) {
            stmt.bindString(6, uploadStatus);
        }
 
        String batchStatus = entity.getBatchStatus();
        if (batchStatus != null) {
            stmt.bindString(7, batchStatus);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String trace = entity.getTrace();
        if (trace != null) {
            stmt.bindString(9, trace);
        }
 
        String batch = entity.getBatch();
        if (batch != null) {
            stmt.bindString(10, batch);
        }
 
        String procCode = entity.getProcCode();
        if (procCode != null) {
            stmt.bindString(11, procCode);
        }
 
        String pan = entity.getPan();
        if (pan != null) {
            stmt.bindString(12, pan);
        }
 
        String amount = entity.getAmount();
        if (amount != null) {
            stmt.bindString(13, amount);
        }
 
        String expDate = entity.getExpDate();
        if (expDate != null) {
            stmt.bindString(14, expDate);
        }
 
        String settleDate = entity.getSettleDate();
        if (settleDate != null) {
            stmt.bindString(15, settleDate);
        }
 
        String cardSn = entity.getCardSn();
        if (cardSn != null) {
            stmt.bindString(16, cardSn);
        }
 
        String serviceCode = entity.getServiceCode();
        if (serviceCode != null) {
            stmt.bindString(17, serviceCode);
        }
 
        String maxPinLen = entity.getMaxPinLen();
        if (maxPinLen != null) {
            stmt.bindString(18, maxPinLen);
        }
 
        String currency = entity.getCurrency();
        if (currency != null) {
            stmt.bindString(19, currency);
        }
 
        String aIICode = entity.getAIICode();
        if (aIICode != null) {
            stmt.bindString(20, aIICode);
        }
 
        String track2 = entity.getTrack2();
        if (track2 != null) {
            stmt.bindString(21, track2);
        }
 
        String track3 = entity.getTrack3();
        if (track3 != null) {
            stmt.bindString(22, track3);
        }
 
        String field53 = entity.getField53();
        if (field53 != null) {
            stmt.bindString(23, field53);
        }
 
        String field55 = entity.getField55();
        if (field55 != null) {
            stmt.bindString(24, field55);
        }
 
        String field61 = entity.getField61();
        if (field61 != null) {
            stmt.bindString(25, field61);
        }
 
        String field63 = entity.getField63();
        if (field63 != null) {
            stmt.bindString(26, field63);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(27, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(28, time);
        }
 
        String year = entity.getYear();
        if (year != null) {
            stmt.bindString(29, year);
        }
 
        String referenceNo = entity.getReferenceNo();
        if (referenceNo != null) {
            stmt.bindString(30, referenceNo);
        }
 
        String authCode = entity.getAuthCode();
        if (authCode != null) {
            stmt.bindString(31, authCode);
        }
 
        String oldTrace = entity.getOldTrace();
        if (oldTrace != null) {
            stmt.bindString(32, oldTrace);
        }
 
        String oldAuthCode = entity.getOldAuthCode();
        if (oldAuthCode != null) {
            stmt.bindString(33, oldAuthCode);
        }
 
        String cardId = entity.getCardId();
        if (cardId != null) {
            stmt.bindString(34, cardId);
        }
 
        String issuerMsg = entity.getIssuerMsg();
        if (issuerMsg != null) {
            stmt.bindString(35, issuerMsg);
        }
 
        String cupMsg = entity.getCupMsg();
        if (cupMsg != null) {
            stmt.bindString(36, cupMsg);
        }
 
        String aquMsg = entity.getAquMsg();
        if (aquMsg != null) {
            stmt.bindString(37, aquMsg);
        }
 
        String issuerId = entity.getIssuerId();
        if (issuerId != null) {
            stmt.bindString(38, issuerId);
        }
 
        String acqId = entity.getAcqId();
        if (acqId != null) {
            stmt.bindString(39, acqId);
        }
 
        String posCenter = entity.getPosCenter();
        if (posCenter != null) {
            stmt.bindString(40, posCenter);
        }
 
        String operator = entity.getOperator();
        if (operator != null) {
            stmt.bindString(41, operator);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(42, remark);
        }
 
        String signDataStatus = entity.getSignDataStatus();
        if (signDataStatus != null) {
            stmt.bindString(43, signDataStatus);
        }
 
        String signDataSend = entity.getSignDataSend();
        if (signDataSend != null) {
            stmt.bindString(44, signDataSend);
        }
 
        String specialCode = entity.getSpecialCode();
        if (specialCode != null) {
            stmt.bindString(45, specialCode);
        }
 
        byte[] signData = entity.getSignData();
        if (signData != null) {
            stmt.bindBlob(46, signData);
        }
 
        String scriptResult = entity.getScriptResult();
        if (scriptResult != null) {
            stmt.bindString(47, scriptResult);
        }
 
        String signPath = entity.getSignPath();
        if (signPath != null) {
            stmt.bindString(48, signPath);
        }
 
        String signICData = entity.getSignICData();
        if (signICData != null) {
            stmt.bindString(49, signICData);
        }
 
        String sign_phone = entity.getSign_phone();
        if (sign_phone != null) {
            stmt.bindString(50, sign_phone);
        }
 
        String sign_balance = entity.getSign_balance();
        if (sign_balance != null) {
            stmt.bindString(51, sign_balance);
        }
 
        String isQPSTrans = entity.getIsQPSTrans();
        if (isQPSTrans != null) {
            stmt.bindString(52, isQPSTrans);
        }
 
        String oldReference = entity.getOldReference();
        if (oldReference != null) {
            stmt.bindString(53, oldReference);
        }
 
        String oldDate = entity.getOldDate();
        if (oldDate != null) {
            stmt.bindString(54, oldDate);
        }
 
        String emvIcData = entity.getEmvIcData();
        if (emvIcData != null) {
            stmt.bindString(55, emvIcData);
        }
        stmt.bindLong(56, entity.getNeedPrint() ? 1L: 0L);
        stmt.bindLong(57, entity.getScriptFlag() ? 1L: 0L);
        stmt.bindLong(58, entity.getNoPINFlag() ? 1L: 0L);
        stmt.bindLong(59, entity.getNoSignFlag() ? 1L: 0L);
        stmt.bindLong(60, entity.getTimeStamp());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TransactionData readEntity(Cursor cursor, int offset) {
        TransactionData entity = new TransactionData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // transType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // _39
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // status
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // settleType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // uploadStatus
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // batchStatus
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // name
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // trace
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // batch
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // procCode
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // pan
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // amount
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // expDate
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // settleDate
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // cardSn
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // serviceCode
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // maxPinLen
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // currency
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // aIICode
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // track2
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // track3
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // field53
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // field55
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // field61
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // field63
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // date
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // time
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // year
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // referenceNo
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // authCode
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // oldTrace
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // oldAuthCode
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // cardId
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // issuerMsg
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // cupMsg
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // aquMsg
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // issuerId
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // acqId
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // posCenter
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // operator
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // remark
            cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42), // signDataStatus
            cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43), // signDataSend
            cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44), // specialCode
            cursor.isNull(offset + 45) ? null : cursor.getBlob(offset + 45), // signData
            cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46), // scriptResult
            cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47), // signPath
            cursor.isNull(offset + 48) ? null : cursor.getString(offset + 48), // signICData
            cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49), // sign_phone
            cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50), // sign_balance
            cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51), // isQPSTrans
            cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52), // oldReference
            cursor.isNull(offset + 53) ? null : cursor.getString(offset + 53), // oldDate
            cursor.isNull(offset + 54) ? null : cursor.getString(offset + 54), // emvIcData
            cursor.getShort(offset + 55) != 0, // needPrint
            cursor.getShort(offset + 56) != 0, // scriptFlag
            cursor.getShort(offset + 57) != 0, // noPINFlag
            cursor.getShort(offset + 58) != 0, // noSignFlag
            cursor.getLong(offset + 59) // timeStamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TransactionData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTransType(cursor.getInt(offset + 1));
        entity.set_39(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStatus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSettleType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUploadStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBatchStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTrace(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setBatch(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setProcCode(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPan(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setAmount(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setExpDate(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setSettleDate(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCardSn(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setServiceCode(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setMaxPinLen(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCurrency(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setAIICode(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTrack2(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setTrack3(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setField53(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setField55(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setField61(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setField63(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setDate(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setTime(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setYear(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setReferenceNo(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setAuthCode(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setOldTrace(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setOldAuthCode(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setCardId(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setIssuerMsg(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setCupMsg(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setAquMsg(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setIssuerId(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setAcqId(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setPosCenter(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setOperator(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setRemark(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setSignDataStatus(cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42));
        entity.setSignDataSend(cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43));
        entity.setSpecialCode(cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44));
        entity.setSignData(cursor.isNull(offset + 45) ? null : cursor.getBlob(offset + 45));
        entity.setScriptResult(cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46));
        entity.setSignPath(cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47));
        entity.setSignICData(cursor.isNull(offset + 48) ? null : cursor.getString(offset + 48));
        entity.setSign_phone(cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49));
        entity.setSign_balance(cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50));
        entity.setIsQPSTrans(cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51));
        entity.setOldReference(cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52));
        entity.setOldDate(cursor.isNull(offset + 53) ? null : cursor.getString(offset + 53));
        entity.setEmvIcData(cursor.isNull(offset + 54) ? null : cursor.getString(offset + 54));
        entity.setNeedPrint(cursor.getShort(offset + 55) != 0);
        entity.setScriptFlag(cursor.getShort(offset + 56) != 0);
        entity.setNoPINFlag(cursor.getShort(offset + 57) != 0);
        entity.setNoSignFlag(cursor.getShort(offset + 58) != 0);
        entity.setTimeStamp(cursor.getLong(offset + 59));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TransactionData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TransactionData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TransactionData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
