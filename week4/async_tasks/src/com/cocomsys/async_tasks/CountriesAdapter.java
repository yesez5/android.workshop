package com.cocomsys.async_tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yesez on 07-06-14.
 */
public class CountriesAdapter extends BaseAdapter {

	ArrayList<Country> model;
	Context ctx;

	public CountriesAdapter(ArrayList<Country> model, Context ctx) {
		this.model = model;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return model.size();
	}

	@Override
	public Object getItem(int position) {
		return model.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(ctx).inflate(R.layout.country_row, null);
		}

		Country selectedItem = (Country)getItem(position);
		((TextView)convertView.findViewById(R.id.tv_country_name)).setText(selectedItem.getName());

		return convertView;
	}
}
