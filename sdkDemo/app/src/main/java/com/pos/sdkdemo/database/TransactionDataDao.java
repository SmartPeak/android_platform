package com.pos.sdkdemo.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.basewin.database.dao.BaseDbManagerDao;
import com.basewin.database.exception.BwDatabaseException;
import com.basewin.database.model.BaseModel;

public class TransactionDataDao extends BaseDbManagerDao {

	@Override
	public BaseModel setData(Cursor cursor) {
		// TODO Auto-generated method stub
		if (cursor == null) {
			return null;
		}
		TransactionData datatmp = new TransactionData();
		datatmp.setAmount(getNameByIndex(cursor, "amount"));
		datatmp.setAuth_no(getNameByIndex(cursor, "auth_no"));
		datatmp.setBatch_no(getNameByIndex(cursor, "batch_no"));
		datatmp.setCard_number(getNameByIndex(cursor, "card_number"));
		datatmp.setDate_time(getNameByIndex(cursor, "date_time"));
		datatmp.setExp_date(getNameByIndex(cursor, "exp_date"));
		datatmp.setFunc(Integer.parseInt(getNameByIndex(cursor, "func")));
		datatmp.setMerchant_name(getNameByIndex(cursor, "merchant_name"));
		datatmp.setMerchant_no(getNameByIndex(cursor, "merchant_no"));
		datatmp.setOperatorNo(getNameByIndex(cursor, "operatorNo"));
		datatmp.setReferenceNo(getNameByIndex(cursor, "referenceNo"));
		datatmp.setTerminal_no(getNameByIndex(cursor, "terminal_no"));
		datatmp.setTicket_no(getNameByIndex(cursor, "ticket_no"));
		datatmp.setTrace(getNameByIndex(cursor, "trace"));
		datatmp.setStatus(Integer.parseInt(getNameByIndex(cursor, "status")));
		return datatmp;
	}

	@Override
	public ContentValues getContentValues(BaseModel data) throws BwDatabaseException {
		// TODO Auto-generated method stub
		TransactionData transactionData = (TransactionData)data;
		ContentValues values = new ContentValues();
		values.put("amount", transactionData.getAmount());
		values.put("auth_no", transactionData.getAuth_no());
		values.put("batch_no", transactionData.getBatch_no());
		values.put("card_number", transactionData.getCard_number());
		values.put("date_time", transactionData.getDate_time());
		values.put("exp_date", transactionData.getExp_date());
		values.put("func", String.valueOf(transactionData.getFunc()));
		values.put("merchant_name", transactionData.getMerchant_name());
		values.put("merchant_no", transactionData.getMerchant_no());
		values.put("operatorNo", transactionData.getOperatorNo());
		values.put("referenceNo", transactionData.getReferenceNo());
		values.put("terminal_no", transactionData.getTerminal_no());
		values.put("ticket_no", transactionData.getTicket_no());
		values.put("trace", transactionData.getTrace());
		values.put("status", String.valueOf(transactionData.getStatus()));
		return values;
	}

}
