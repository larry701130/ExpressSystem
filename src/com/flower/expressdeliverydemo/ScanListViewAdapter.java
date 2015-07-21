package com.flower.expressdeliverydemo;

import java.util.ArrayList;
import java.util.List;

import com.flower.expressdeliverydemo.MyAdapter.ViewTag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScanListViewAdapter extends BaseAdapter {

    private LayoutInflater myInflater;
    ArrayList<String> list = null;
    
	public ScanListViewAdapter(Context ctx, ArrayList<String> list)
	{
        myInflater = LayoutInflater.from(ctx);
        this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
        ViewTag viewTag;
        
        if(arg1 == null){
            //取得listItem容器 view
        	arg1 = myInflater.inflate(R.layout.scan_list_row, null);
            
            //建構listItem內容view
            viewTag = new ViewTag(
                    (TextView)arg1.findViewById(R.id.textView1));
            
            //設置容器內容
            arg1.setTag(viewTag);
        }
        else{
            viewTag = (ViewTag) arg1.getTag();
        }
        
        viewTag.barcode.setText(list.get(arg0));
        
        return arg1;
	}

	class ViewTag{
		TextView barcode;
	
	    public ViewTag(TextView Barcode){
	        this.barcode = Barcode;
	    }
	}

	
    public void removeAt(int pos)
    {
    	this.list.remove(pos);
    }
}
