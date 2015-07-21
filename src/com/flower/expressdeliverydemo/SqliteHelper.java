package com.flower.expressdeliverydemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper 
{
	public SqliteHelper(Context context) 
	{
		super(context, DatabaseGrammar.DATABASE_NAME, null, DatabaseGrammar._DBVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(DatabaseGrammar.CREATE_SIGN_ORDER_MASTER);
		db.execSQL(DatabaseGrammar.CREATE_NEW_ORDER_DETAIL);
		db.execSQL(DatabaseGrammar.CREATE_NEW_ORDER_TRACE);
		db.execSQL(DatabaseGrammar.CREATE_SET_ORDER_STATUS);
		db.execSQL(DatabaseGrammar.CREATE_SENT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{
		// TODO Auto-generated method stub
	}

}
