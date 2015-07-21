package com.flower.expressdeliverydemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
import android.R.string;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;



public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public static String userIDString;
	public static String CustomerID;
	public static int lastPage = 1;
	
	public static Boolean state = false;
	public Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				lastPage = arg0 + 1;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if(arg0 == ViewPager.SCROLL_STATE_SETTLING)
				{
					if(lastPage != 1 && lastPage != 2)
					{
	                	View view = mViewPager.findViewWithTag(lastPage);
	                	EditText editText = (EditText)view.findViewById(R.id.editText1);
	                	editText.getText().clear();
//	                	TextView detailTextView = (TextView)view.findViewById(R.id.NewOrderDetailResult);
//	                	TextView traceTextView = (TextView)view.findViewById(R.id.NewOrderTraceResult);
	                	TextView resultTextView = (TextView)view.findViewById(R.id.Result);
	                	TextView barcodeTextView = (TextView)view.findViewById(R.id.BarcodeID);
//	                	detailTextView.setText("");
//	                	traceTextView.setText("");
	                	barcodeTextView.setText("");
	                	resultTextView.setText("");
					}
				}
			}
		});
		
		userIDString = getIntent().getStringExtra("UserID");
		CustomerID = getIntent().getStringExtra("CustomerID");
		Log.v("Larry", "Oncreate");
		SQLite sqlite = new SQLite(this);
		sqlite.SQLiteOpen();
		sqlite.Update();
		sqlite.SQLiteClose();
		//sqlite.SQLiteClose();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		moveTaskToBack(true);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		state = true;
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(state)
		{
			Log.v("Larry", "resume");
			UpdateLists();
			state = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.topbutton, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		return super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.leave:
				System.exit(0);
				return true;
			case R.id.update:
				SQLite sqlite = new SQLite(this);
				sqlite.SQLiteOpen();
				sqlite.Update();
				sqlite.SQLiteClose();
				UpdateLists();
				
				Toast.makeText(this, "資料刷新完畢!!", Toast.LENGTH_LONG).show();
				break;
		}
		
		return super.onOptionsItemSelected(item); 
	}
	
	public void UpdateLists()
	{
		for(int page = 0; page < 2; page++)
		{
			Fragment fragment = (Fragment)mViewPager.getAdapter().instantiateItem(mViewPager, page);
			View view = fragment.getView();
			if(view == null)
			{
				state = false;
				return;
			}
			
			ListView list = (ListView)view.findViewById(R.id.listView1);
			MyAdapter adapter = (MyAdapter)list.getAdapter();
			adapter.UpdateData();
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetInvalidated();
			view.refreshDrawableState();
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static ArrayList<String> TakenList =  new ArrayList<String>();
		public static ArrayList<String> TransferList =  new ArrayList<String>();
		public static ArrayList<String> TakedList =  new ArrayList<String>();
		public static ArrayList<String> DeliverList =  new ArrayList<String>();
		
		
		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView;
			TextView dummyTextView;
			MyAdapter myAdapter;
//			ArrayAdapter<String> listAdapter ;
			Log.v("Larry", "Dummy " + getArguments().getInt(
					ARG_SECTION_NUMBER));
			switch (getArguments().getInt(
					ARG_SECTION_NUMBER)) {
			case 1:
//				rootView = inflater.inflate(R.layout.fragment_listlayout,
//						container, false);
//				ListView listView = (ListView) rootView.findViewById(R.id.listView1);
////				List<DataStruct> list = new ArrayList<DataStruct>();
//				final List<DataStruct> list =  new MainActivity().GetCustomersList(WebServiceData.NON_TAKEN_STATUS_STRING);
//
//				myAdapter = new MyAdapter(listView.getContext(),list);
//				listView.setAdapter(myAdapter);
//				listView.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//						// TODO Auto-generated method stub
//						TextView name = (TextView)arg1.findViewById(R.id.Name);
////						Intent intent = new Intent(arg1.getContext(), ListProductActivity.class);
//						Intent intent = new Intent(arg1.getContext(), ScanActivity.class);
//						intent.putExtra("Name", name.getText());
//						intent.putExtra("UserID", userIDString);
//						intent.putExtra("CustomerID", CustomerID);
//						intent.putExtra("SignID", list.get(arg2).ID);
//						startActivity(intent);
//					}
//					
//				});
				return ChangeListView(inflater, container, WebServiceData.NON_TAKEN_STATUS_STRING);
			
			case 2:
//				return ChangeScanView(inflater, container, TakenList,getArguments().getInt(
//						ARG_SECTION_NUMBER));
				return ChangeListView(inflater, container, WebServiceData.TAKEN_STATUS_STRING);
			case 3:
				return ChangeScanView(inflater, container,getArguments().getInt(
						ARG_SECTION_NUMBER));
			case 4:
				return ChangeScanView(inflater, container,getArguments().getInt(
						ARG_SECTION_NUMBER));
			case 5:
				return ChangeScanView(inflater, container,getArguments().getInt(
						ARG_SECTION_NUMBER));
			default:
				rootView = inflater.inflate(R.layout.scan_view,
						container, false);
				return rootView;
			}
		}

		class MyItemClickListener implements OnItemClickListener
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView textView = (TextView)arg1.findViewById(R.id.Name);
				
			}
		}
		
		public View ChangeScanView(LayoutInflater inflater, ViewGroup container, final int Page)
		{
			View rootView = inflater.inflate(R.layout.scan_view, container, false);
			rootView.setTag(Page);
			final EditText editText = (EditText)rootView.findViewById(R.id.editText1);
			
			Button button = (Button)rootView.findViewById(R.id.button1);
			final TextView barcodeIDTextView = (TextView)rootView.findViewById(R.id.BarcodeID);
			final TextView resultTextView = (TextView)rootView.findViewById(R.id.Result);
			
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {					
					AlertDialog.Builder dialog1 =new AlertDialog.Builder(v.getContext());
			        dialog1.setTitle("");
			        dialog1.setMessage("是否上傳?");
			        dialog1.setIcon(android.R.drawable.ic_dialog_alert);
			        dialog1.setCancelable(false); 
			        dialog1.setPositiveButton("上傳",new DialogInterface.OnClickListener() {
			        	@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(!TextUtils.isEmpty(editText.getText()))
							{
//								ScanListViewAdapter scanListViewAdapter1;
//								scanListViewAdapter1=null;
								String tmp = editText.getText().toString();
								editText.getText().clear();
								barcodeIDTextView.setText(WebServiceData.BarCode_STRING + ": " + tmp);	
								String status = "";
								int i1=0;
								List<String> items = Arrays.asList(tmp.split("\\n"));
								for( i1=0; i1<items.size();i1++)
								{	
								//Toast.makeText(getActivity(), items.get(i1), Toast.LENGTH_LONG).show();								
								//===================================================================
								switch(Page)
								{
								case 3:
									status = WebServiceData.TRANSFER_STATUS_STRING;
									break;
								case 4:
									status = WebServiceData.TAKED_STATUS_STRING;
									break;
								case 5:
									status = WebServiceData.DELIVER_STATUS_STRING;
									break;
								}
								SQLite sqlite = new SQLite(getActivity());
								sqlite.SQLiteOpen();
								MainActivity main = new MainActivity();
								Boolean b_detail, b_trace;
//								if(main.NewOrderDetail(items.get(i1), WebServiceData.ANONYMOUS_SIGN_ID_STRING).toString().contains("0"))
//									b_detail = true;
////									detailTextView.setText(WebServiceData.NEW_ORDER_DETAIL_RESULT_STRING + WebServiceData.SUCCESS_STRING);
//								else
//								{
//									Log.v("Larry", "detail fail");
//									b_detail = false;
////									detailTextView.setText(WebServiceData.NEW_ORDER_DETAIL_RESULT_STRING + WebServiceData.FAIL_STRING);
//									sqlite.add(DatabaseGrammar.NEW_ORDER_DETAIL_TABLE, null, null, null, items.get(i1), WebServiceData.ANONYMOUS_SIGN_ID_STRING);
//								}
//								if(main.NewOrderTrace(items.get(i1),status).toString().contains("0"))
//									b_trace = true;
////									traceTextView.setText(WebServiceData.NEW_ORDER_TRACE_RESULT_STRING + WebServiceData.SUCCESS_STRING);
//								else
//								{
//									Log.v("Larry", "trace fail");
//									b_trace = false;
////									traceTextView.setText(WebServiceData.NEW_ORDER_TRACE_RESULT_STRING + WebServiceData.FAIL_STRING);
//									sqlite.add(DatabaseGrammar.NEW_ORDER_TRACE_TABLE, status, CustomerID, userIDString, tmp, null);
//								}
								
								if(main.App_Took(status, items.get(i1), WebServiceData.ANONYMOUS_SIGN_ID_STRING).toString().contains("0"))
									b_detail = true;
								else
								{
									b_detail = false;
									sqlite.add(DatabaseGrammar.SEND_TABLE_TABLE, status, CustomerID, userIDString, items.get(i1), WebServiceData.ANONYMOUS_SIGN_ID_STRING, "3");
								}
								if(b_detail)
									resultTextView.setText(WebServiceData.SUCCESS_STRING);
								else
									resultTextView.setText(WebServiceData.FAIL_STRING);
								}
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
		        
		        //====================

				}
			});
			
			return rootView;
		}
	
		public View ChangeListView(LayoutInflater inflater, ViewGroup container, final String statusString)
		{
			View rootView = inflater.inflate(R.layout.fragment_listlayout,
					container, false);
			ListView listView = (ListView) rootView.findViewById(R.id.listView1);
//			final List<DataStruct> list =  new MainActivity().GetCustomersList(statusString);
			List<DataStruct> list =  new MainActivity().QueryCustomersList(getActivity(), statusString);
			final MyAdapter myAdapter = new MyAdapter(listView.getContext(),list, statusString);
			listView.setAdapter(myAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					TextView name = (TextView)arg1.findViewById(R.id.Name);
//					Intent intent = new Intent(arg1.getContext(), ListProductActivity.class);
					Intent intent = new Intent(arg1.getContext(), ScanActivity.class);
					intent.putExtra("Name", name.getText());
					intent.putExtra("UserID", userIDString);
					intent.putExtra("CustomerID", CustomerID);
					intent.putExtra("SignID", myAdapter.getItem(arg2).ID);
					intent.putExtra("Status", statusString);
					intent.putExtra("KeyID", myAdapter.getItem(arg2).KeyID);
					startActivity(intent);
				}
				
			});

			return rootView;
		}
	}
	
	public List<DataStruct> QueryCustomersList(Context cxt, String statusString)
	{
		String where_clause = DatabaseGrammar.STATUS_FIELD + "= '" + statusString + "'";
		SQLite sqlite = new SQLite(cxt);
		sqlite.SQLiteOpen();
		Cursor cursor = sqlite.Query(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE,
				DatabaseGrammar.CUSTOMER_LIST,
				where_clause);
		final List<DataStruct> list = new ArrayList<DataStruct>();
		if(cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				DataStruct tmp = new DataStruct();
				tmp.KeyID = cursor.getString(cursor.getColumnIndex("master_id"));
				tmp.OrderType = cursor.getString(cursor.getColumnIndex("ORDER_TYPE"));
    			tmp.ID = cursor.getString(cursor.getColumnIndex("SIGNORDER_ID"));
    			tmp.Company = cursor.getString(cursor.getColumnIndex("GET_COMPANY"));
    			tmp.Name = cursor.getString(cursor.getColumnIndex("GET_CONTACT"));
    			tmp.H_Tel = cursor.getString(cursor.getColumnIndex("GET_TEL"));
    			tmp.H_Ext = cursor.getString(cursor.getColumnIndex("GET_TEL_EXT"));
    			tmp.Address_Code = cursor.getString(cursor.getColumnIndex("GET_ADDR_CODE"));
    			tmp.Address = cursor.getString(cursor.getColumnIndex("GET_ADDR"));
    			tmp.Mobile = cursor.getString(cursor.getColumnIndex("GET_MOBILE"));
    			tmp.r_Company = cursor.getString(cursor.getColumnIndex("REV_CUST"));
    			tmp.r_Name = cursor.getString(cursor.getColumnIndex("REV_NAME"));
    			tmp.r_H_Tel = cursor.getString(cursor.getColumnIndex("REV_TEL"));
    			tmp.r_H_Ext = cursor.getString(cursor.getColumnIndex("REV_TEL_EXT"));
    			tmp.r_Address_Code = cursor.getString(cursor.getColumnIndex("REV_POSTCODE"));
    			tmp.r_Address = cursor.getString(cursor.getColumnIndex("REV_ADDR"));
    			tmp.r_Mobile = cursor.getString(cursor.getColumnIndex("REV_MOBILE"));
    			
    			list.add(tmp);
                cursor.moveToNext();
			}
		}
		sqlite.SQLiteClose();
		return list;
	}
	
	public StringBuilder QueryCustomerDetail(Context cxt, String KeyID)
	{
		String where_clause = DatabaseGrammar.MASTER_ID_FIELD + "= '" + KeyID + "'";
		SQLite sqlite = new SQLite(cxt);
		sqlite.SQLiteOpen();
		Cursor cursor = sqlite.Query(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE,
				null,
				where_clause);
		final StringBuilder sb = new StringBuilder();
		if(cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				for(int i = 0; i < WebServiceData.DETAIL_STRINGS.length; i++)
				{
					
					String tmp = cursor.getString(cursor.getColumnIndex(WebServiceData.DETAIL_STRINGS[i]));
					if(WebServiceData.DETAIL_STRINGS[i].contains("ORDER_TYPE"))
					{
						if(tmp.contains("特急件"))
						{
							sb.append(WebServiceData.DETAIL_MAPPING_STRINGS[i] + " : " + "特急件" + "\n");
						}
						else if(tmp.contains("專件"))
						{
							sb.append(WebServiceData.DETAIL_MAPPING_STRINGS[i] + " : " + "專件" + "\n");
						}
						else if(tmp.contains("一般件"))
						{
							sb.append(WebServiceData.DETAIL_MAPPING_STRINGS[i] + " : " + "一般件" + "\n");
						}
						else
							sb.append(WebServiceData.DETAIL_MAPPING_STRINGS[i] + " : " + tmp + "\n");
					}
					else
					{
						sb.append(WebServiceData.DETAIL_MAPPING_STRINGS[i] + " : " + tmp + "\n");
					}
					if(i==8)
					{sb.append("\n");}
					if(i==15)
					{sb.append("\n");}
					
				}
                cursor.moveToNext();
			}
		}
		sqlite.SQLiteClose();
		return sb;
	}
	
	public List<DataStruct> GetCustomersList(final String statusString)
	{
		final List<DataStruct> list = new ArrayList<DataStruct>();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {

	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_MOBILE_ORDER_DATA_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.CUST_ID_STRING, CustomerID);
	                request.addProperty(WebServiceData.CLIENT_CUST_ID_STRING, "");
	                request.addProperty(WebServiceData.CREATE_USR_STRING,"");
	                request.addProperty(WebServiceData.REV_NAME_STRING,"");
	                request.addProperty(WebServiceData.STATUS_STRING,statusString);
	                request.addProperty(WebServiceData.DOC_ID_STRING,"");
	                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	                String date = sDateFormat.format(new java.util.Date());
	                request.addProperty(WebServiceData.BEGIN_DATE_STRING,date);
	                Calendar c = Calendar.getInstance();
	                c.setTime(sDateFormat.parse(date));
	                c.add(Calendar.DATE, 1);
	                date = sDateFormat.format(c.getTime());
	                request.addProperty(WebServiceData.END_DATE_STRING,date);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                Log.v("Larry", "start http");
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_MOBILE_ORDER_DATA_STRING, envelope);
	                Log.v("Larry", "end http");

	                final SoapObject response = (SoapObject)envelope.getResponse();
	                Log.v("Larry",String.valueOf(response.getPropertyCount()));
	                
                	for(int i = 0; i < response.getPropertyCount(); i++)
                	{
    					DataStruct tmp = new DataStruct();
                		SoapObject object = (SoapObject)response.getProperty(i);
                		
                		if(object.getPropertySafely("SIGNORDER_ID") != null)
                			tmp.ID = String.valueOf(object.getPropertySafely("SIGNORDER_ID"));
                		if(object.getPropertySafely("GET_COMPANY") != null)
                			tmp.Company = String.valueOf(object.getPropertySafely("GET_COMPANY"));
                		if(object.getPropertySafely("GET_CONTACT") != null)
                			tmp.Name = String.valueOf(object.getPropertySafely("GET_CONTACT"));
                		if(object.getPropertySafely("GET_TEL") != null)
                			tmp.H_Tel = String.valueOf(object.getPropertySafely("GET_TEL"));
                		if(object.getPropertySafely("GET_TEL_EXT") != null)
                			tmp.H_Ext = String.valueOf(object.getPropertySafely("GET_TEL_EXT"));
                		if(object.getPropertySafely("GET_ADDR_CODE") != null)
                			tmp.Address_Code = String.valueOf(object.getPropertySafely("GET_ADDR_CODE"));
                		if(object.getPropertySafely("GET_ADDR") != null)
                			tmp.Address = String.valueOf(object.getPropertySafely("GET_ADDR"));
                		if(object.getPropertySafely("GET_MOBILE") != null)
                			tmp.Mobile = String.valueOf(object.getPropertySafely("GET_MOBILE"));
                		
                		if(object.getPropertySafely("REV_CUST") != null)
                			tmp.r_Company = String.valueOf(object.getPropertySafely("REV_CUST"));
                		if(object.getPropertySafely("REV_NAME") != null)
                			tmp.r_Name = String.valueOf(object.getPropertySafely("REV_NAME"));
                		if(object.getPropertySafely("REV_TEL") != null)
                			tmp.r_H_Tel = String.valueOf(object.getPropertySafely("REV_TEL"));
                		if(object.getPropertySafely("REV_TEL_EXT") != null)
                			tmp.r_H_Ext = String.valueOf(object.getPropertySafely("REV_TEL_EXT"));
                		if(object.getPropertySafely("REV_POSTCODE") != null)
                			tmp.r_Address_Code = String.valueOf(object.getPropertySafely("REV_POSTCODE"));
                		if(object.getPropertySafely("REV_ADDR") != null)
                			tmp.r_Address = String.valueOf(object.getPropertySafely("REV_ADDR"));
                		if(object.getPropertySafely("REV_MOBILE") != null)
                			tmp.r_Mobile = String.valueOf(object.getPropertySafely("REV_MOBILE"));
                		
                		list.add(tmp);
                	}
	                runOnUiThread (new Runnable(){      

	                    public void run() {

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
        Log.v("Larry", "finish " + String.valueOf(list.size()));
	    return list;
	}
	
	public StringBuilder NewOrderTrace(final String Barcode, final String statusCodeString)
	{
		final StringBuilder result = new StringBuilder();
	    Thread networkThread = new Thread() {    
	        @Override   
	        public void run() {
	            try {
	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_NEW_ORDER_TRACE_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.StatusCode_STRING, statusCodeString);
					Log.v("Larry", "NewOrderTrace CustomerID:" + CustomerID);
	                request.addProperty(WebServiceData.ClientID_STRING, CustomerID);
	                request.addProperty(WebServiceData.UserName_STRING, userIDString);
	                request.addProperty(WebServiceData.BarCode_STRING, Barcode);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                Log.v("Larry", "start http");
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_NEW_ORDER_TRACE_STRING, envelope);
	                Log.v("Larry", "end http");

	                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	                Log.v("Larry","New Order Trace:" + String.valueOf(response.getValue().toString()));
	                
	                result.append(response.getValue().toString());
	                runOnUiThread (new Runnable(){      

	                    public void run() {
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
	    
	    return result;
	}
	
	public StringBuilder NewOrderDetail(final String Barcode, final String signIDString)
	{
		final StringBuilder result = new StringBuilder();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {
	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_NEW_ORDER_DETAIL_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.SIGN_ID_STRING, signIDString);
					Log.v("Larry", "New Order Detail Barcode:" + Barcode);
	                request.addProperty(WebServiceData.BarCode_STRING, Barcode);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                Log.v("Larry", "start http");
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_NEW_ORDER_DETAIL_STRING, envelope);
	                Log.v("Larry", "end http");

	                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	                Log.v("Larry","New Order Detail:" + String.valueOf(response.getValue().toString()));
	                result.append(response.getValue().toString());
	                runOnUiThread (new Runnable(){      

	                    public void run() {

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
	    
	    return result;
	}

	public StringBuilder SetOrderStatus(final String signIDString, final String statusCodeString)
	{
		final StringBuilder result = new StringBuilder();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {

	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_NEW_SET_ORDER_STATUS_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.SIGN_ID_STRING, signIDString);
	                request.addProperty(WebServiceData.StatusCode_STRING, statusCodeString);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                Log.v("Larry", "start http");
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_NEW_SET_ORDER_STATUS_STRING, envelope);
	                Log.v("Larry", "end http");

	                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	                Log.v("Larry","New Order Detail:" + String.valueOf(response.getValue().toString()));
	                result.append(response.getValue().toString());
	                runOnUiThread (new Runnable(){      
	                    public void run() {
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
	    
	    return result;
	}

	public StringBuilder GetCustomerDetail(final String customerIDString, final String statusString, final String signOrderIdString)
	{
		final StringBuilder sb = new StringBuilder();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {

	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_GET_ORDER_DETAIL_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.CUST_ID_STRING, customerIDString);
	                request.addProperty(WebServiceData.CLIENT_CUST_ID_STRING, "");
	                request.addProperty(WebServiceData.CREATE_USR_STRING,"");
	                request.addProperty(WebServiceData.REV_NAME_STRING,"");
	                request.addProperty(WebServiceData.STATUS_STRING,statusString);
	                request.addProperty(WebServiceData.DOC_ID_STRING,"");
	                request.addProperty(WebServiceData.BEGIN_DATE_STRING,"");
	                request.addProperty(WebServiceData.END_DATE_STRING,"");
	                request.addProperty(WebServiceData.SIGNORDER_ID_STRING,signOrderIdString);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                Log.v("Larry", "start http");
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_GET_ORDER_DETAIL_STRING, envelope);
	                Log.v("Larry", "end http");

	                SoapObject response = (SoapObject)envelope.getResponse();
	                SoapObject response_detail = (SoapObject)response.getProperty(0);
	                Log.v("Larry",String.valueOf(WebServiceData.DETAIL_STRINGS.length));
	              //  sb.append(String.valueOf(WebServiceData.DETAIL_STRINGS.length));
	                for(int i = 0; i <= WebServiceData.DETAIL_STRINGS.length; i++)
	                {
	            	  
	                	Object object = response_detail.getPropertySafely(WebServiceData.DETAIL_STRINGS[i]);
	                	String tmp = "";
	                	if(object != null)
	                		tmp = response_detail.getPropertySafelyAsString(WebServiceData.DETAIL_STRINGS[i]);
	                	if(tmp.equals("null")|| tmp.equals("anyType{}"))
	                		tmp = "";
	                		sb.append(WebServiceData.DETAIL_MAPPING_STRINGS[i] + " : " + tmp + "\n");
	                }
	                
	                runOnUiThread (new Runnable(){      

	                    public void run() {
	                    	
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
        Log.v("Larry", "finish ");
	    return sb;
	}
	
	public StringBuilder GetOrderData(final SQLiteDatabase db)
	{
		final StringBuilder sb = new StringBuilder();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {

	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_GET_ORDER_DATA_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.CUST_ID_STRING, CustomerID);
	                request.addProperty(WebServiceData.CLIENT_CUST_ID_STRING, "");
	                request.addProperty(WebServiceData.CREATE_USR_STRING,"");
	                request.addProperty(WebServiceData.REV_NAME_STRING,"");
	                request.addProperty(WebServiceData.STATUS_STRING,"");
	                request.addProperty(WebServiceData.DOC_ID_STRING,"");
	                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	                String date = sDateFormat.format(new java.util.Date());
	                request.addProperty(WebServiceData.BEGIN_DATE_STRING,date);
	                Calendar c = Calendar.getInstance();
	                c.setTime(sDateFormat.parse(date));
	                c.add(Calendar.DATE, 1);
	                date = sDateFormat.format(c.getTime());
	                request.addProperty(WebServiceData.END_DATE_STRING,date);
	                request.addProperty(WebServiceData.SIGN_ID_STRING,"");
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_GET_ORDER_DATA_STRING, envelope);

	                SoapObject response = (SoapObject)envelope.getResponse();
	                Log.v("Larry","All detail:" + String.valueOf(WebServiceData.ALL_DETAIL_STRINGS.length) + " " + String.valueOf(response.getPropertyCount()));
	              //  sb.append(String.valueOf(WebServiceData.DETAIL_STRINGS.length));
	        		db.delete(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE, null, null);
	        		for(int j = 0; j < response.getPropertyCount(); j++)
	        		{
	        			ContentValues values = new ContentValues();
		                SoapObject response_detail = (SoapObject)response.getProperty(j);
		                for(int i = 0; i < WebServiceData.ALL_DETAIL_STRINGS.length; i++)
		                {

		                	Object object = response_detail.getPropertySafely(WebServiceData.ALL_DETAIL_STRINGS[i]);
		                	String tmp = "";
		                	if(object != null)
		                		tmp = response_detail.getPropertySafelyAsString(WebServiceData.ALL_DETAIL_STRINGS[i]);
		                	if(tmp.equals("null")|| tmp.equals("anyType{}"))
		                		tmp = "";
		                	values.put(WebServiceData.ALL_DETAIL_STRINGS[i], tmp);		                	
		                }
		                db.insert(DatabaseGrammar.SIGN_ORDER_MASTER_TABLE, null, values);
	        		}
	                runOnUiThread (new Runnable(){      

	                    public void run() {
	                    	
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
        Log.v("Larry", "finish ");
	    return sb;
	}
 
	public StringBuilder App_NonTaken(final String StatusCode, final String BarCode, final String SIGNORDER_ID)
	{
		final StringBuilder result = new StringBuilder();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {

	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_APP_NONTAKEN_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.STATUS_CODE_STRING, StatusCode);
	                request.addProperty(WebServiceData.ClientID_STRING, CustomerID);
	                request.addProperty(WebServiceData.UserName_STRING, userIDString);
	                request.addProperty(WebServiceData.BarCode_STRING, BarCode);
	                request.addProperty(WebServiceData.SIGNORDER_ID_STRING, SIGNORDER_ID);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_APP_NONTAKEN_STRING, envelope);
	                
	                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	                Log.v("Status", "app nontaken result:" + response);
	                result.append(response.getValue().toString());
  
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
	    
	    return result;
	}
	
	public StringBuilder App_Took(final String status, final String BarCode, final String SIGNORDER_ID)
	{
		final StringBuilder result = new StringBuilder();
	    Thread networkThread = new Thread() {    

	        @Override   
	        public void run() {

	            try {

	                SoapObject request = new SoapObject(WebServiceData.NAMESPACE, WebServiceData.METHOD_APP_TOOK_STRING);
	                
	                request.addProperty(WebServiceData.ID_STRING, "jmsprint");
	                request.addProperty(WebServiceData.PASSWORD_STRING, "Jms@7272");
	                request.addProperty(WebServiceData.STATUS_CODE_STRING, status);
	                request.addProperty(WebServiceData.ClientID_STRING, CustomerID);
	                request.addProperty(WebServiceData.UserName_STRING, userIDString);
	                request.addProperty(WebServiceData.BarCode_STRING, BarCode);
	                request.addProperty(WebServiceData.SIGNORDER_ID_STRING, SIGNORDER_ID);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  

	                envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應

	                envelope.setOutputSoapObject(request);

	                HttpTransportSE ht = new HttpTransportSE(WebServiceData.URL);         
	                ht.call(WebServiceData.NAMESPACE + "/" + WebServiceData.METHOD_APP_TOOK_STRING, envelope);
	                
	                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	                Log.v("Status", "app taken result:" + response);
	                result.append(response.getValue().toString());
  
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
	    
	    return result;
	}
}

	
