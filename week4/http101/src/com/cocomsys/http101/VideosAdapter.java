package com.cocomsys.http101;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yesez on 07-11-14.
 */
public class VideosAdapter extends BaseAdapter {

	private ArrayList<VideoItem> model;
	private Context ctx;

	public VideosAdapter(ArrayList<VideoItem> model, Context ctx) {
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
			convertView = LayoutInflater.from(ctx).inflate(R.layout.video_row, null);
		}

		VideoItem selectedItem = model.get(position);

		TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
		TextView tvDescription = (TextView)convertView.findViewById(R.id.tv_description);

		tvTitle.setText(selectedItem.getTitle());
		tvDescription.setText(selectedItem.getDescription());

		return convertView;
	}
}
