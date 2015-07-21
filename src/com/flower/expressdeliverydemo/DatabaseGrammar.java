package com.flower.expressdeliverydemo;

public class DatabaseGrammar {
	public final static String DATABASE_NAME = "Express.db";
	public final static int _DBVersion = 1;
	public final static String SIGN_ORDER_MASTER_TABLE = "signorder_master";
	public final static String NEW_ORDER_TRACE_TABLE = "newordertrace";
	public final static String SET_ORDER_STATUS_TABLE = "setorderstatus";
	public final static String NEW_ORDER_DETAIL_TABLE = "neworderdetail";
	public final static String SEND_TABLE_TABLE = "senttable";
	public final static String STATUS_CODE_FIELD = "status_code";
	public final static String CUST_ID_FIELD = "cust_id";
	public final static String USER_ID_FIELD = "creator";
	public final static String BARCODE_FIELD = "BARCODE";
	public final static String FEEDBACK_TIME_FIELD = "feedback_time";
	public final static String SIGNORDER_ID_FIELD = "signorder_id";
	public final static String TRACE_ID_FIELD = "trace_id";
	public final static String REC_ID_FIELD = "rec_id";
	public final static String DETAIL_ID_FIELD = "DETAIL_ID";
	public final static String STATUS_FIELD = "STATUS";
	public final static String MASTER_ID_FIELD = "master_id";
	public final static String SEND_TYPE_FIELD = "send_type";
	
	public enum TABLES {
		signorder_master,
		newordertrace,
		setorderstatus,
		neworderdetail,
		senttable
	};
	
	public final static String CREATE_SIGN_ORDER_MASTER = "CREATE TABLE IF NOT EXISTS [signorder_master] (" +
			"[master_id] INTEGER PRIMARY KEY AUTOINCREMENT,"+
			"[SIGNORDER_ID] NVARCHAR(50) NOT NULL,"+
			"[BARCODE] NVARCHAR(50),"+
			"[CREATE_TIME] DATETIME NOT NULL,"+
			"[CREATE_USR] NVARCHAR(20) NOT NULL,"+
			"[IMG_URL] NVARCHAR(50),"+
			"[STATUS] NVARCHAR(10),"+
			"[CLIENT_CUST_ID] [INT	],"+
			"[CUST_ID] NVARCHAR(10) NOT NULL,"+
			"[ORDER_ID] NVARCHAR(50),"+
			"[TRANS_ID] NVARCHAR(50),"+
			"[ACCT_DATE] DATE,"+
			"[SEND_DATE] NVARCHAR(20),"+
			"[TRANS_DATE] DATE,"+
			"[ORDER_DATE] DATE,"+
			"[REV_TEL] NVARCHAR(20),"+
			"[MEMO] NTEXT,"+
			"[QTY] [INT	],"+
			"[DROP_CODE] NVARCHAR(50),"+
			"[SITE_NAME] NVARCHAR(50),"+
			"[BATCH_NO] [INT	],"+
			"[REV_AMT] [INT	],"+
			"[SHIP_DATE] DATETIME,"+
			"[ORDER_TYPE] NVARCHAR(20),"+
			"[DRIVER_NAME] NVARCHAR(20),"+
			"[DRIVER_NO] NVARCHAR(20),"+
			"[DEL_MARK] [INT	],"+
			"[ARRIVE_DATE] NVARCHAR(20),"+
			"[WEIGHT] [DECIMAL	],"+
			"[PAY_AMT] [INT	],"+
			"[GET_COMPANY] NVARCHAR(50),"+
			"[GET_ADDR_CODE] NVARCHAR(10),"+
			"[GET_ADDR] NVARCHAR(50),"+
			"[GET_TEL] NVARCHAR(20),"+
			"[GET_TEL_EXT] NVARCHAR(10),"+
			"[GET_MOBILE] NVARCHAR(20),"+
			"[GET_CONTACT] NVARCHAR(20),"+
			"[REV_POSTCODE] NVARCHAR(10),"+
			"[REV_NAME] NVARCHAR(20),"+
			"[REV_MOBILE] NVARCHAR(20),"+
			"[REV_CUST_ID] NVARCHAR(10),"+
			"[REV_CUST] NVARCHAR(10),"+
			"[REV_TEL_EXT] NVARCHAR(20),"+
			"[REV_ADDR] NVARCHAR(50),"+
			"[FEE_AMT] [INT	],"+
			"[SITE_NAME_END] NVARCHAR(10),"+
			"[CAR_NO1] NVARCHAR(10),"+
			"[CAR_NO2] NVARCHAR(10),"+
			"[XDAR_FLAG] [CHAR	],"+
			"[XDAR_BATCH] NVARCHAR(10),"+
			"[TALLY_NO] NVARCHAR(25),"+
			"[feedback_date] DATE,"+
			"[feedback_status] INT DEFAULT 0"+
			");";
	public final static String CREATE_NEW_ORDER_TRACE = "CREATE TABLE IF NOT EXISTS [newordertrace] (" +
			 "[trace_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + 
			 "[status_code] NVARCHAR(10) NOT NULL," + 
			 "[cust_id] NVARCHAR(20) NOT NULL," + 
			 "[creator] NVARCHAR(20) NOT NULL," + 
			 "[BARCODE] NVARCHAR(50) NOT NULL," + 
			 "[feedback_status] INT NOT NULL DEFAULT 1," + 
			 "[feedback_time] DATETIME," + 
			 "[create_time] DATETIME NOT NULL DEFAULT (Datetime('Now')));";
	public final static String CREATE_SET_ORDER_STATUS = "CREATE TABLE IF NOT EXISTS [setorderstatus] (" +
			 "[rec_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + 
			 "[signorder_id] NVARCHAR(50) NOT NULL," + 
			 "[status_code] NVARCHAR(10)," + 
			 "[feedback_code] INTEGER NOT NULL DEFAULT 1," + 
			 "[create_date] DATETIME NOT NULL DEFAULT (Datetime('Now'))," + 
			 "[feedback_time] DATETIME);";
	public final static String CREATE_NEW_ORDER_DETAIL = "CREATE TABLE IF NOT EXISTS [neworderdetail] (" +
			 "[DETAIL_ID] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + 
			 "[signorder_id] NVARCHAR(50) NOT NULL DEFAULT 0," + 
			 "[BARCODE] NVARCHAR(50) NOT NULL," + 
			 "[feedback_code] INT NOT NULL DEFAULT 1," + 
			 "[feedback_time] DATETIME," + 
			 "[create_time] DATETIME NOT NULL DEFAULT (Datetime('Now')));";
	
	public final static String CREATE_SENT_TABLE = "CREATE TABLE IF NOT EXISTS [senttable] (" +
			 "[DETAIL_ID] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + 
			 "[signorder_id] NVARCHAR(50) NOT NULL ," + 
			 "[BARCODE] NVARCHAR(50) NOT NULL," + 
			 "[status_code] NVARCHAR(10) NOT NULL," + 
			 "[cust_id] NVARCHAR(10) NOT NULL," +
			 "[send_type] NVARCHAR(10) NOT NULL," +
			 "[create_time] DATETIME);";
	public final static String[] CUSTOMER_LIST = {
		"master_id",
		"STATUS",
		"SIGNORDER_ID",
		"GET_COMPANY",
		"GET_CONTACT",
		"GET_TEL",
		"GET_TEL_EXT",
		"GET_ADDR_CODE",
		"GET_ADDR",
		"GET_MOBILE",
		"REV_CUST",
		"REV_NAME",
		"REV_TEL",
		"REV_TEL_EXT",
		"REV_POSTCODE",
		"REV_ADDR",
		"REV_MOBILE",
		"ORDER_TYPE"
	};
}
