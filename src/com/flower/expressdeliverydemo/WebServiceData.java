package com.flower.expressdeliverydemo;

public class WebServiceData {
	  public static final String NAMESPACE = "JMSWebSrv1";
	  public static final String NAMESPACE1 = "JMSWebSrv";
	//  private static final String URL = "http://www.lyjms.net/jmswebsrv/ClientUser.asmx";
	  public static final String URL = "http://www.lyjms.net/jmswebsrv/Order.asmx";
	  public static final String SOAP_ACTION = "JMSWebSrv1/MobileOrderData";
	  public static final String METHOD_NAME = "MobileOrderData";
	  public static final String METHOD_GET_ORDER_DATA_STRING = "GetOrderData";
	  public static final String METHOD_MOBILE_ORDER_DATA_STRING = "MobileOrderData";
	  public static final String METHOD_NEW_ORDER_DETAIL_STRING = "NewOrderDetail";
	  public static final String METHOD_NEW_ORDER_TRACE_STRING = "NewOrderTrace";
	  public static final String METHOD_NEW_SET_ORDER_STATUS_STRING = "SetOrderStatus";
	  public static final String METHOD_GET_ORDER_DETAIL_STRING = "GetOrderData";
	  public static final String METHOD_APP_NONTAKEN_STRING = "App_NonTaken";
	  public static final String METHOD_APP_TOOK_STRING = "App_Took";
	  public static final String ID_STRING = "ID";
	  public static final String PASSWORD_STRING = "Password";
	  public static final String CUST_ID_STRING = "CUST_ID";
	  public static final String CLIENT_CUST_ID_STRING = "CLIENT_CUST_ID";
	  public static final String CREATE_USR_STRING = "CREATE_USR";
	  public static final String REV_NAME_STRING = "REV_NAME";
	  public static final String STATUS_STRING = "STATUS";
	  public static final String DOC_ID_STRING = "DocID";
	  public static final String BEGIN_DATE_STRING = "BeginDate";
	  public static final String END_DATE_STRING = "EndDate";
	  public static final String SIGNORDER_ID_STRING = "SIGNORDER_ID";
	  public static final String STATUS_CODE_STRING = "StatusCode";	  
	  public static final String SIGN_ID_STRING = "SIGNORDER_ID";
	  public static final String BARCODE_STRING = "BARCODE";	  
	  public static final String StatusCode_STRING = "StatusCode";
	  public static final String ClientID_STRING = "ClientID";
	  public static final String UserName_STRING = "UserName";
	  public static final String BarCode_STRING = "BarCode";
	  public static final String REALLOCATE_STATUS_STRING="A00";
	  public static final String RE_Order_Code="A01";
	  public static final String NON_TAKEN_STATUS_STRING = "B00";
	  public static final String TAKEN_STATUS_STRING = "C00";
	  public static final String TRANSFER_STATUS_STRING = "D00";
	  public static final String TAKED_STATUS_STRING = "E00";
	  public static final String DELIVER_STATUS_STRING = "F00";
	  public static final String MEMO="MEMO";
	  public static final String ANONYMOUS_SIGN_ID_STRING = "0";
	  public static final String NEW_ORDER_DETAIL_RESULT_STRING = "New Order Detail傳送結果: ";
	  public static final String NEW_ORDER_TRACE_RESULT_STRING = "New Order Trace傳送結果: ";
	  public static final String SET_ORDER_STATUS_RESULT_STRING = "SET Order STATUS傳送結果: ";
	  public static final String SUCCESS_STRING = "傳送成功";
	  public static final String FAIL_STRING = "傳送失敗";
	  public static final String LOG_IN_SUCCESS_STRING = "登入成功";
	  public static final String LOG_IN_FAIL_STRING = "帳號密碼、業主代碼 輸入錯誤";
	  public static final String NETWORK_FAIL_STRING = "無網路狀態 請檢查是否已連接網路";
	  public static final String[] DETAIL_STRINGS = {
		  "SIGNORDER_ID",
		  "BARCODE",
		  "GET_COMPANY",
		  "GET_ADDR_CODE",
		  "GET_ADDR",
		  "GET_TEL",
		  "GET_TEL_EXT",
		  "GET_MOBILE",
		  "GET_CONTACT",
		  "REV_CUST",
		  "REV_POSTCODE",
		  "REV_ADDR",
		  "REV_TEL",
		  "REV_TEL_EXT",
		  "REV_MOBILE",
		  "REV_NAME",
		  "TRANS_ID",
		  "TRANS_DATE",
		  "ORDER_ID",
		  "ORDER_DATE",
		  "SHIP_DATE",
		  "SITE_NAME",
		  "SITE_NAME_END",
		  "WEIGHT",
		  "ORDER_TYPE",
		  "QTY",
		  "REV_AMT",
		  "PAY_AMT",
		  "FEE_AMT",
		  "MEMO"};
	  public static final String[] DETAIL_MAPPING_STRINGS =
		  {"案件編號",
		  "簽單號碼",
		  "寄件客戶",
		  "寄件區碼",
		  "寄件地址",
		  "寄件電話",
		  "寄件分機",
		  "寄件手機",
		  "寄件聯繫人",
		  "收件客戶名",
		  "收件區碼",
		  "收件地址",
		  "收件電話",
		  "收件分機",
		  "收件手機",
		  "收件聯繫人",
		  "托運單號",
		  "託運日期",
		  "訂單編號",
		  "訂單日期",
		  "出貨日期",
		  "起站代碼",
		  "訖站代碼",
		  "重量",
		  "件種",
		  "件數",
		  "代收款",
		  "代付款",
		  "運費",
		  "備註"};
	  public static final String[] ALL_DETAIL_STRINGS = {
		  "SIGNORDER_ID",
		  "BARCODE",
		  "CREATE_TIME",
		  "CREATE_USR",
		  "IMG_URL",
		  "STATUS",
		  "CLIENT_CUST_ID",
		  "CUST_ID",
		  "ORDER_ID",
		  "TRANS_ID",
		  "ACCT_DATE",
		  "SEND_DATE",
		  "TRANS_DATE",
		  "ORDER_DATE",
		  "REV_TEL",
		  "MEMO",
		  "QTY",
		  "DROP_CODE",
		  "SITE_NAME",
		  "BATCH_NO",
		  "REV_AMT",
		  "SHIP_DATE",
		  "ORDER_TYPE",
		  "DRIVER_NAME",
		  "DRIVER_NO",
		  "DEL_MARK",
		  "ARRIVE_DATE",
		  "WEIGHT",
		  "PAY_AMT",
		  "GET_COMPANY",
		  "GET_ADDR_CODE",
		  "GET_ADDR",
		  "GET_TEL",
		  "GET_TEL_EXT",
		  "GET_MOBILE",
		  "GET_CONTACT",
		  "REV_POSTCODE",
		  "REV_NAME",
		  "REV_MOBILE",
		  "REV_CUST_ID",
		  "REV_CUST",
		  "REV_TEL_EXT",
		  "REV_ADDR",
		  "FEE_AMT",
		  "SITE_NAME_END",
		  "CAR_NO1",
		  "CAR_NO2",
		  "XDAR_FLAG",
		  "XDAR_BATCH",
		  "TALLY_NO"};
}
