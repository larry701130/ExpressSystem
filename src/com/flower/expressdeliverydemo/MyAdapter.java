package com.flower.expressdeliverydemo;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter {

    private LayoutInflater myInflater;
    List<DataStruct> list = null;
    private String mstatusString;
    private Context cxt;
	
	public MyAdapter(Context cxt, List<DataStruct> list, String statusString)
	{
        myInflater = LayoutInflater.from(cxt);
        this.cxt = cxt;
        this.list = list;
        this.mstatusString = statusString;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public DataStruct getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void UpdateData()
	{
		list.clear();
		list = new MainActivity().QueryCustomersList(cxt, mstatusString);
		Log.v("Larry", "UpdateData status:" + mstatusString + " list num:"+ list.size());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        //自訂類別，表達個別listItem中的view物件集合。
        ViewTag viewTag;
        
        if(convertView == null){
            //取得listItem容器 view
            convertView = myInflater.inflate(R.layout.list_row, null);
            //建構listItem內容view
            viewTag = new ViewTag(
                    (TextView) convertView.findViewById(
                            R.id.ID_Number),
                    (TextView) convertView.findViewById(
                            R.id.Order_Type),
                    (TextView)convertView.findViewById(
                        R.id.Name),
                    (TextView)convertView.findViewById(
                            R.id.Company),
                    (TextView) convertView.findViewById(
                        R.id.Address),
                    (TextView) convertView.findViewById(
                        R.id.TEL_Number),
                    (TextView) convertView.findViewById(
                            R.id.Mobile_Number),
                    (TextView)convertView.findViewById(
                            R.id.r_Name),
                    (TextView)convertView.findViewById(
                            R.id.r_Company),
                    (TextView) convertView.findViewById(
                        R.id.r_Address),
                    (TextView) convertView.findViewById(
                        R.id.r_TEL_Number),
                    (TextView) convertView.findViewById(
                            R.id.r_Mobile_Number)
                    );
            //設置容器內容
            convertView.setTag(viewTag);
        }
        else{
            viewTag = (ViewTag) convertView.getTag();
        }
        
        //設定內容文字
        viewTag.idNo.setText(list.get(position).ID);
        if(CheckStringNonNull(list.get(position).OrderType).contains("特急件"))
        {
        	viewTag.orderType.setTextColor(Color.RED);
        	 viewTag.orderType.setText("件種:" + "特急件");
        }
        else if(CheckStringNonNull(list.get(position).OrderType).contains("專件"))
        {
        	viewTag.orderType.setTextColor(Color.RED);
        	viewTag.orderType.setText("件種:" + "專件");
        }
        else if(CheckStringNonNull(list.get(position).OrderType).contains("一般件"))
        {
        	viewTag.orderType.setTextColor(Color.BLACK);
        	viewTag.orderType.setText("件種:" + "一般件");
        }
        else
        	viewTag.orderType.setText("件種:" + CheckStringNonNull(list.get(position).OrderType));
        
        viewTag.name.setText("寄件人:" + CheckStringNonNull(list.get(position).Name));
        viewTag.company.setText("公司名稱:" + CheckStringNonNull(list.get(position).Company));
        viewTag.address.setText("地址:" + CheckStringNonNull(list.get(position).Address_Code) + " " + CheckStringNonNull(list.get(position).Address));
        viewTag.tel.setText("電話:" + CheckStringNonNull(list.get(position).H_Tel) + " " + CheckStringNonNull(list.get(position).H_Ext));
        viewTag.mobile.setText("手機:" + CheckStringNonNull(list.get(position).Mobile));
        
        viewTag.r_name.setText("收件人:" + CheckStringNonNull(list.get(position).r_Name));
        viewTag.r_company.setText("公司名稱:" + CheckStringNonNull(list.get(position).r_Company));
        viewTag.r_address.setText("地址:" + CheckStringNonNull(list.get(position).r_Address_Code) + " " + CheckStringNonNull(list.get(position).r_Address));
        viewTag.r_tel.setText("電話:" + CheckStringNonNull(list.get(position).r_H_Tel) + " 分機:" + CheckStringNonNull(list.get(position).r_H_Ext));
        viewTag.r_mobile.setText("手機:" + CheckStringNonNull(list.get(position).r_Mobile));
        return convertView;
	}
	
    class ViewTag{
        TextView idNo;
        TextView orderType;
        
    	TextView name;
        TextView address;
        TextView tel;
        TextView mobile;
        TextView company;
        
    	TextView r_name;
        TextView r_address;
        TextView r_idNo;
        TextView r_tel;
        TextView r_mobile;
        TextView r_company;
    
        public ViewTag(TextView idNo,TextView orderType, TextView name, TextView company ,TextView address,  TextView tel, TextView mobile,
        		TextView r_name, TextView r_company, TextView r_address, TextView r_tel, TextView r_mobile){
            this.orderType = orderType;
        	this.name = name;
            this.address = address;
            this.idNo = idNo;
            this.tel = tel;
            this.mobile = mobile;
            this.company = company;
            
            this.r_name = r_name;
            this.r_address = r_address;
            this.r_tel = r_tel;
            this.r_mobile = r_mobile;
            this.r_company = r_company;
        }
    }
    
    public String CheckStringNonNull(String mString)
    {
    	if(mString == null || mString.equals("null") || mString.equals("anyType{}"))
    		return "";
    	else 
			return mString;
    }
}
