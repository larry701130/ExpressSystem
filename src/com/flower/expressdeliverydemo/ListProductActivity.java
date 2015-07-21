package com.flower.expressdeliverydemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class ListProductActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_product);
		// Show the Up button in the action bar.
		setupActionBar();
		
//		List<ProductStruct> list = new ArrayList<ProductStruct>();
//		Intent intent = getIntent();
//		String name = intent.getStringExtra("Name");
//		
//		getActionBar().setTitle(name);
//		for(int i = 0; i < 3; i++)
//		{
//			ProductStruct tmp = new ProductStruct();
//			tmp.Barcode = "Barcod:" + (int)(Math.random()*100000000);
//			tmp.Description =" Product " + i;
//			list.add(tmp);
//		}
//		DataAdapter dataAdapter = new DataAdapter(this, list);
//		setListAdapter(dataAdapter);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_product, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
