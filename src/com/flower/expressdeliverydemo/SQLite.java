package com.flower.expressdeliverydemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLite {
	
	SqliteHelper sqlHelper;
	Context activityContext;
	SQLiteDatabase db;
	public SQLite(Context context)
	{
		activityContext = context;
	}
	
	public void add(String Table, String StatusCode, String CustomerID, String UserID, String Barcode, String SignID, String SendType)
	{
		ContentValues values = new ContentValues();
		
		switch(DatabaseGrammar.TABLES.valueOf(Table))
		{
		case newordertrace:
			values.put(DatabaseGrammar.STATUS_CODE_FIELD, StatusCode);
			values.put(DatabaseGrammar.CUST_ID_FIELD, CustomerID);
			values.put(DatabaseGrammar.USER_ID_FIELD, UserID);
			values.put(DatabaseGrammar.BARCODE_FIELD, Barcode);
			break;
		case setorderstatus:
			values.put(DatabaseGrammar.SIGNORDER_ID_FIELD, SignID);
			values.put(DatabaseGrammar.STATUS_CODE_FIELD, StatusCode);
			break;
		case neworderdetail:
			values.put(DatabaseGrammar.SIGNORDER_ID_FIELD, SignID);
			values.put(DatabaseGrammar.BARCODE_FIELD, Barcode);
			break;
		case senttable:
			values.put(DatabaseGrammar.SIGNORDER_ID_FIELD, SignID);
			values.put(DatabaseGrammar.BARCODE_FIELD, Barcode);
			values.put(DatabaseGrammar.STATUS_CODE_FIELD, StatusCode);
			values.put(DatabaseGrammar.CUST_ID_FIELD, CustomerID);
			values.put(DatabaseGrammar.SEND_TYPE_FIELD, SendType);
			break;
		default:
			break;
		}
		db.insert(Table, null, values);
	}
	
	public void delete(String Table, String ID)
	{
		switch(DatabaseGrammar.TABLES.valueOf(Table))
		{
		case newordertrace: 
			db.delete(Table, DatabaseGrammar.TRACE_ID_FIELD + "=" + ID, null);
			break;
		case setorderstatus:
			db.delete(Table, DatabaseGrammar.REC_ID_FIELD + "=" + ID, null);
			break;
		case neworderdetail:
			db.delete(Table, DatabaseGrammar.DETAIL_ID_FIELD + "=" + ID, null);
			break;
		case senttable:
			db.delete(Table, DatabaseGrammar.DETAIL_ID_FIELD + "=" + ID, null);
			break;
		default:
			break;
		}
	}
	
	public Cursor Query(String Table, String[] Columns, String Clause)
	{
		Log.v("Larry", "sqlite1" + db.isOpen());
		Cursor cursor = db.query(Table, Columns, Clause, null, null, null, null);
		Log.v("Larry", "sqlite2");
		return cursor;
	}
	
	public void UpdateRow(String Table, ContentValues cv, String whereClause)
	{
		db.update(Table, cv, whereClause, null);
	}
	
	public void Update()
	{
//		UpatePart(DatabaseGrammar.NEW_ORDER_TRACE_TABLE);
//		UpatePart(DatabaseGrammar.SET_ORDER_STATUS_TABLE);
//		UpatePart(DatabaseGrammar.NEW_ORDER_DETAIL_TABLE);
		UpdatePart(DatabaseGrammar.SEND_TABLE_TABLE);
		new MainActivity().GetOrderData(db);
	}
	
	public void UpdatePart(String TableName)
	{
		MainActivity main = new MainActivity();
		String sql = "select * from " + TableName;
		Cursor  cursor = db.rawQuery(sql, null);
		String tmp = "";
		String signIDString = "";
		String status = "";
		String sendType = "";
		if (cursor .moveToFirst()) {
            while (cursor.isAfterLast() == false) {
        		switch(DatabaseGrammar.TABLES.valueOf(TableName))
        		{
        		case newordertrace:
        			tmp = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.BARCODE_FIELD));
        			status = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.STATUS_CODE_FIELD));
    				if(main.NewOrderTrace(tmp, status).toString().contains("0"))
    					delete(TableName, cursor.getString(cursor.getColumnIndex(DatabaseGrammar.TRACE_ID_FIELD)));
        			break;
        		case setorderstatus:
        			signIDString = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.SIGNORDER_ID_FIELD));
        			status = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.STATUS_CODE_FIELD));
					if(main.SetOrderStatus(signIDString, status).toString().contains("0"))
    					delete(TableName, cursor.getString(cursor.getColumnIndex(DatabaseGrammar.REC_ID_FIELD)));
        			break;
        		case neworderdetail:
        			tmp = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.BARCODE_FIELD));
        			signIDString = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.SIGNORDER_ID_FIELD));
    				if(main.NewOrderDetail(tmp, signIDString).toString().contains("0"))
    					delete(TableName, cursor.getString(cursor.getColumnIndex(DatabaseGrammar.DETAIL_ID_FIELD)));
        			break;
        		case senttable:
        			tmp = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.BARCODE_FIELD));
        			signIDString = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.SIGNORDER_ID_FIELD));
        			status = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.STATUS_CODE_FIELD));
        			sendType = cursor.getString(cursor.getColumnIndex(DatabaseGrammar.SEND_TYPE_FIELD));
        			if(sendType.equals("1"))
        			{
        				if(main.App_NonTaken(status, tmp, signIDString).toString().contains("0"))
        					delete(TableName, cursor.getString(cursor.getColumnIndex(DatabaseGrammar.DETAIL_ID_FIELD)));
        			}
        			else
        			{
        				if(main.App_Took(status, tmp, signIDString).toString().contains("0"))
        					delete(TableName, cursor.getString(cursor.getColumnIndex(DatabaseGrammar.DETAIL_ID_FIELD)));
        			}
        			break;
				default:
					break;
        		}
                cursor.moveToNext();
            }
        }
	}
	
	public void SQLiteOpen()
	{
		sqlHelper = new SqliteHelper(activityContext);
		db = sqlHelper.getWritableDatabase();
	}
	
	public void SQLiteClose()
	{
		db.close();
		sqlHelper.close();
	}
}
