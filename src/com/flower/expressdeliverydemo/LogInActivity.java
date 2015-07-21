package com.flower.expressdeliverydemo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends Activity {

		private static final String NAMESPACE = "JMSWebSrv";
		private static final String URL = "http://www.lyjms.net/jmswebsrv/ClientUser.asmx";
		private static final String SOAP_ACTION = "JMSWebSrv/UserLogin";
		private static final String METHOD_NAME = "UserLogin";
		private static String KeyUserName = "UserName";
//		private static String KeyPassword = "Password";
		private static String KeyCustomerID = "CustomerID";
//	    private static final String UserName = "0502";
//	    private static final String Password = "0502image";
//		private static final String CustomerID = "0502";
	private EditText UserName, Password, CustomerID;
	private TextView logTextView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		Log.v("Larry", "Path:" + getApplicationInfo().dataDir);
		UserName = (EditText)findViewById(R.id.editText1);
		Password = (EditText)findViewById(R.id.editText2);
		CustomerID = (EditText)findViewById(R.id.editText3);
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		UserName.setText(sharedPref.getString(KeyUserName, ""));
//		Password.setText(sharedPref.getString(KeyPassword, ""));
		CustomerID.setText(sharedPref.getString(KeyCustomerID, ""));
		
		logTextView = (TextView)findViewById(R.id.textView2);
		Button b_loginButton = (Button)findViewById(R.id.button1);
		b_loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
				if ( activeNetwork != null && activeNetwork.isConnected()) 
				{
					Log.v("Larry", "Connect");
					RunWebService();
				}
				else
				{
					logTextView.setTextColor(Color.RED);
                	logTextView.setText(WebServiceData.NETWORK_FAIL_STRING);
				}
//				if(logTextView.getText().equals("1"))
//				{
//					Intent intent = new Intent();
//					intent.setClass(LogInActivity.this, MainActivity.class);
//					startActivity(intent);
//					LogInActivity.this.finish();
//				}
			}
		});
	}

	private void RunWebService()
	{
	    Thread networkThread = new Thread() {
	        @Override   

	        public void run() {

	            try {

	                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//	                request.addProperty("SN", SNInput.getText().toString());
	                request.addProperty("UserID", UserName.getText().toString());
	                request.addProperty("UserPwd", Password.getText().toString());
	                request.addProperty("ClientID", CustomerID.getText().toString());
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(URL);         
	                Log.v("Status", "start http");
	                ht.call(SOAP_ACTION, envelope);         
	                Log.v("Status", "end http");
	                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();          
	                Log.v("Status", "response ");
	                runOnUiThread (new Runnable(){      

	                    public void run() {
	    	                Log.v("Status", "response " + response.getValue().toString());
	    	                if(response.toString().contains("1"))
	    	                {
	    						logTextView.setTextColor(Color.BLACK);
	    	                	logTextView.setText(WebServiceData.LOG_IN_SUCCESS_STRING);
	    	                }
	    	                else
	    	                {
	    						logTextView.setTextColor(Color.RED);
	    	                	logTextView.setText(WebServiceData.LOG_IN_FAIL_STRING);
	    	                }
	        				if(response.toString().equals("1"))
	        				{
	        					Intent intent = new Intent();
	        					intent.setClass(LogInActivity.this, MainActivity.class);
	        					intent.putExtra("CustomerID", CustomerID.getText().toString());
	        					intent.putExtra("UserID", UserName.getText().toString());
	        					
	        					SharedPreferences sharedPref = LogInActivity.this.getPreferences(Context.MODE_PRIVATE);
	        					SharedPreferences.Editor editor = sharedPref.edit();
	        					editor.putString(KeyUserName, UserName.getText().toString());
//	        					editor.putString(KeyPassword, Password.getText().toString());
	        					editor.putString(KeyCustomerID, CustomerID.getText().toString());
	        					editor.commit();
	        					
	        					startActivity(intent);
	        					LogInActivity.this.finish();
	        				}
	                    }       

	                });      

	            }catch (Exception e) {         

	                e.printStackTrace();     

	            }    

	        }  

	    };  

	    networkThread.start();//網友說Android 3.0後網路存取必須在Thread中run
	    
	    try {
			networkThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

}
