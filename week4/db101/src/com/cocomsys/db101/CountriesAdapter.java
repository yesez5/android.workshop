package com.cocomsys.db101;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cocomsys.db101.models.Country;

import java.util.ArrayList;

/**
 * Created by yesez on 07-12-14.
 */
public class CountriesAdapter extends BaseAdapter {

	ArrayList<Country> modelList;
	Context ctx;

	public CountriesAdapter(ArrayList<Country> modelList, Context ctx) {
		this.modelList = modelList;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return modelList.size();
	}

	@Override
	public Object getItem(int position) {
		return modelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null)
			convertView = LayoutInflater.from(ctx).inflate(R.layout.country_row, null);

		Country selectedModel = modelList.get(position);
		TextView tvName = (TextView)convertView.findViewById(R.id.tv_name);
		tvName.setText(selectedModel.getName());

		return convertView;
	}
}
