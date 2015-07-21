package com.flower.expressdeliverydemo;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;



public class ScanActivity extends Activity implements OnClickListener {

	public static String userIDString;
	public static String CustomerID;
	public static String signIDString;
	public static String statusString;
	public static String keyIDString;
//	public ArrayList<String> list = new ArrayList<String>();
	public EditText editText;
//	public TextView orderDetailTextView, detailResultTextView, traceResultTextView, statusResultTextView;
	public TextView orderDetailTextView;
	public TextView barcodeIDTextView;
	public TextView resultTextView;
	public Button reallocateButton;
	public LinearLayout partial_layout;
	public Button tempa;
//	public ListView listView2;
//	public ScanListViewAdapter scanListViewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_scan_view);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra("Name");
		userIDString = intent.getStringExtra("UserID");
		CustomerID = intent.getStringExtra("CustomerID");
		signIDString = intent.getStringExtra("SignID");
		statusString = intent.getStringExtra("Status");
		keyIDString = intent.getStringExtra("KeyID");
		getActionBar().setTitle(name);
		editText = (EditText)findViewById(R.id.editText1);
		resultTextView = (TextView)findViewById(R.id.Result);
//		detailResultTextView = (TextView)findViewById(R.id.NewOrderDetailResult);
//		traceResultTextView = (TextView)findViewById(R.id.NewOrderTraceResult);
//		statusResultTextView = (TextView)findViewById(R.id.SetOrderStatusResult);
		reallocateButton = (Button)findViewById(R.id.button2);
		tempa=(Button)findViewById(R.id.button1);
		partial_layout = (LinearLayout)findViewById(R.id.partial_layout);
		if(statusString.equals(WebServiceData.TAKEN_STATUS_STRING))
		{
//			statusResultTextView.setVisibility(View.INVISIBLE);
			ViewGroup parent = (ViewGroup)partial_layout.getParent();
			parent.removeView(partial_layout);
			reallocateButton.setEnabled(false);
			reallocateButton.setVisibility(View.INVISIBLE);			
			ViewGroup parentview1 = (ViewGroup) reallocateButton.getParent();
			parentview1.removeView(reallocateButton);			
			LayoutParams params6 = tempa.getLayoutParams();
			params6.width = LinearLayout.LayoutParams.MATCH_PARENT;
			tempa.setLayoutParams(params6);
			
			tempa.setText("已取件補件");
			
		
		}
		
		if(statusString.equals(WebServiceData.NON_TAKEN_STATUS_STRING))		
			findViewById(R.id.warning_str).setVisibility(View.INVISIBLE);
		barcodeIDTextView = (TextView)findViewById(R.id.BarcodeID);
		orderDetailTextView = (TextView)findViewById(R.id.orderDetail);
		
		MainActivity mainActivity = new MainActivity();
		SQLite sqlite = new SQLite(this);
		sqlite.SQLiteOpen();
		StringBuilder detailStringBuilder = mainActivity.QueryCustomerDetail(this, keyIDString);
//		StringBuilder detailStringBuilder = mainActivity.GetCustomerDetail(CustomerID, statusString, signIDString);
		orderDetailTextView.setText(detailStringBuilder);
//		listView2 = (ListView)findViewById(R.id.listView1);
		
//		scanListViewAdapter = new ScanListViewAdapter(this, list);
//		listView2.setAdapter(scanListViewAdapter);
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(this);
		reallocateButton.setOnClickListener(this);
//		Button B_Transfer = (Button)findViewById(R.id.button2);
//		B_Transfer.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scan, menu);
		return true;
	}

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		final SQLite sqlite = new SQLite(this);
		sqlite.SQLiteOpen();
		final MainActivity main = new MainActivity();
		switch (v.getId()) {
		case R.id.button1:
			
			final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	        dialog.setTitle("");
	        dialog.setMessage("是否上傳?");
	        dialog.setIcon(android.R.drawable.ic_dialog_alert);
	        dialog.setCancelable(false); 
	        dialog.setPositiveButton("上傳",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				

					if(!TextUtils.isEmpty(editText.getText()))
					{
//						ScanListViewAdapter scanListViewAdapter1;
//						scanListViewAdapter1=null;
						String tmp = editText.getText().toString();
						editText.getText().clear();						
						barcodeIDTextView.setText(WebServiceData.BarCode_STRING + ": " + tmp);						
						Boolean b_detail, b_trace, b_status;
						b_detail = b_trace = b_status = true;
						
						int i1=0;
						List<String> items = Arrays.asList(tmp.split("\\n"));
						for( i1=0; i1<items.size();i1++)
						{
							
							//Toast.makeText(getApplicationContext(), items.get(i1) , Toast.LENGTH_LONG).show();
							
						
						//===================================================================
//						if(main.NewOrderDetail(items.get(i1), signIDString).toString().contains("0"))
//						{
//							b_detail = true;
////							detailResultTextView.setText(WebServiceData.NEW_ORDER_DETAIL_RESULT_STRING + WebServiceData.SUCCESS_STRING);
//						}
//						else
//						{
//							b_detail = false;
////							detailResultTextView.setText(WebServiceData.NEW_ORDER_DETAIL_RESULT_STRING + WebServiceData.FAIL_STRING);
//							sqlite.add(DatabaseGrammar.NEW_ORDER_DETAIL_TABLE, null, null, null, items.get(i1), signIDString);
//						}
//						
//						if(main.NewOrderTrace(items.get(i1), WebServiceData.TAKEN_STATUS_STRING).toString().contains("0"))
//						{
//							b_trace = true;
////							traceResultTextView.setText(WebServiceData.NEW_ORDER_TRACE_RESULT_STRING + WebServiceData.SUCCESS_STRING);
//						}
//						else
//						{
//							b_trace = false;
////							traceResultTextView.setText(WebServiceData.NEW_ORDER_TRACE_RESULT_STRING + WebServiceData.FAIL_STRING);
//							sqlite.add(DatabaseGrammar.NEW_ORDER_TRACE_TABLE, WebServiceData.TAKEN_STATUS_STRING, CustomerID, userIDString, tmp, null);
//						}
						
						if(statusString.equals(WebServiceData.NON_TAKEN_STATUS_STRING))
						{
							if(reallocateButton.getVisibility()==0)
							{
								reallocateButton.setVisibility(View.INVISIBLE);
								
								ViewGroup parentview4 = (ViewGroup) reallocateButton.getParent();
								parentview4.removeView(reallocateButton);
								LayoutParams params4 = tempa.getLayoutParams();
								params4.width = LinearLayout.LayoutParams.MATCH_PARENT;
								tempa.setLayoutParams(params4);
								
								
								
							}
//							if(main.SetOrderStatus(signIDString, WebServiceData.TAKEN_STATUS_STRING).toString().contains("0"))
//							{
//								b_status = true;
////									statusResultTextView.setText(WebServiceData.SET_ORDER_STATUS_RESULT_STRING + WebServiceData.SUCCESS_STRING);
//								ContentValues cv = new ContentValues();
//								cv.put(DatabaseGrammar.STATUS_FIELD, WebServiceData.TAKEN_STATUS_STRING);
//								String whereClause = DatabaseGrammar.MASTER_ID_FIELD + "= '" + keyIDString + "'";
//								sqlite.UpdateRow(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE, cv, whereClause);
//							}
//							else
//							{
//								b_status = false;
////									statusResultTextView.setText(WebServiceData.SET_ORDER_STATUS_RESULT_STRING + WebServiceData.FAIL_STRING);
//								sqlite.add(DatabaseGrammar.SET_ORDER_STATUS_TABLE, WebServiceData.TAKEN_STATUS_STRING, CustomerID, userIDString, null, signIDString);
//							}
							if(main.App_NonTaken(WebServiceData.TAKEN_STATUS_STRING, items.get(i1), signIDString).toString().contains("0"))
							{
								b_detail = true;
								ContentValues cv = new ContentValues();
								cv.put(DatabaseGrammar.STATUS_FIELD, WebServiceData.TAKEN_STATUS_STRING);
								String whereClause = DatabaseGrammar.MASTER_ID_FIELD + "= '" + keyIDString + "'";
								sqlite.UpdateRow(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE, cv, whereClause);
							}
							else
							{
								b_detail = false;
								sqlite.add(DatabaseGrammar.SEND_TABLE_TABLE, WebServiceData.TAKEN_STATUS_STRING, CustomerID, userIDString, items.get(i1), signIDString, "1");
							}
						}
						else 
						{
							if(main.App_Took(WebServiceData.TAKEN_STATUS_STRING, items.get(i1), signIDString).toString().contains("0"))
								b_detail = true;
							else
							{
								b_detail = false;
								sqlite.add(DatabaseGrammar.SEND_TABLE_TABLE, WebServiceData.TAKEN_STATUS_STRING, CustomerID, userIDString, items.get(i1), signIDString, "2");
							}
						}
						if(b_detail)
							resultTextView.setText(WebServiceData.SUCCESS_STRING);
						else
							resultTextView.setText(WebServiceData.FAIL_STRING);
						
						//=============================================================
						}
					}
				}
			});
	       
	        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	        	@Override
	        	public void onClick(DialogInterface dialog, int which) {
	    // TODO Auto-generated method stub
	   
	   }
	  });   
	        dialog.show();
			
			
			break;
		case R.id.button2:
			final AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
	        dialog1.setTitle("");
	        dialog1.setMessage("是否重派?");
	        dialog1.setIcon(android.R.drawable.ic_dialog_alert);
	        dialog1.setCancelable(false); 
	        dialog1.setPositiveButton("重派",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					tempa.setVisibility(View.INVISIBLE);			
					ViewGroup parentview = (ViewGroup) tempa.getParent();
					parentview.removeView(tempa);
					LayoutParams params2 = reallocateButton.getLayoutParams();
					params2.width = LinearLayout.LayoutParams.MATCH_PARENT;
					tempa.setLayoutParams(params2);
					
					
					if(main.SetOrderStatus(signIDString, WebServiceData.RE_Order_Code).toString().contains("0"))
					{
		//				statusResultTextView.setText(WebServiceData.SET_ORDER_STATUS_RESULT_STRING + WebServiceData.SUCCESS_STRING);
						resultTextView.setText(WebServiceData.SUCCESS_STRING);
						ContentValues cv = new ContentValues();
						cv.put(DatabaseGrammar.STATUS_FIELD, WebServiceData.RE_Order_Code);
						String whereClause = DatabaseGrammar.MASTER_ID_FIELD + "= '" + keyIDString + "'";
						sqlite.UpdateRow(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE, cv, whereClause);
					}
					else
					{
		//				statusResultTextView.setText(WebServiceData.SET_ORDER_STATUS_RESULT_STRING + WebServiceData.FAIL_STRING);
						resultTextView.setText(WebServiceData.FAIL_STRING);
						sqlite.add(DatabaseGrammar.SET_ORDER_STATUS_TABLE, WebServiceData.RE_Order_Code, CustomerID, userIDString, null, signIDString, null);
					}
				}
			});
		      dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		        	@Override
		        	public void onClick(DialogInterface dialog, int which) {
		    // TODO Auto-generated method stub
		   
		   }
		  });   
		        dialog1.show();
				
			break;

		default:
			break;
		}
	}
	

}
