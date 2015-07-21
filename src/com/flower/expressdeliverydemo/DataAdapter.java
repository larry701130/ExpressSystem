package com.flower.expressdeliverydemo;

import java.util.List;

import com.flower.expressdeliverydemo.MyAdapter.ViewTag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter {


    private LayoutInflater myInflater;
    List<ProductStruct> list = null;
	
	public DataAdapter(Context ctx, List<ProductStruct> list)
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        //自訂類別，表達個別listItem中的view物件集合。
        ViewTag viewTag;
        
        if(convertView == null){
            //取得listItem容器 view
            convertView = myInflater.inflate(R.layout.data_list_view, null);
            
            //建構listItem內容view
            viewTag = new ViewTag(
                    (TextView)convertView.findViewById(
                        R.id.barcode_tView),
                    (TextView) convertView.findViewById(
                        R.id.description_tView)
                    );
            
            //設置容器內容
            convertView.setTag(viewTag);
        }
        else{
            viewTag = (ViewTag) convertView.getTag();
        }
        
        //設定內容文字
        viewTag.barcode.setText(list.get(position).Barcode);
        viewTag.description.setText(list.get(position).Description);
        
        return convertView;
	}
	
    class ViewTag{
    	TextView barcode;
        TextView description;
    
        public ViewTag(TextView barcode, TextView description){
            this.barcode = barcode;
            this.description = description;
        }
    }

}
