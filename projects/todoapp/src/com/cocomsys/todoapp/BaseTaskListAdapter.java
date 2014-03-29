package com.cocomsys.todoapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cocomsys.todoapp.models.Task;

import java.util.ArrayList;

/**
 * Created by Jimmy on 03-22-14.
 */
public class BaseTaskListAdapter extends BaseAdapter{
	int layout;
	ArrayList<Task> list;
	Context context;

	public BaseTaskListAdapter(Context context, int layout, ArrayList<Task> list){
		this.layout = layout;
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Task selectedItem = (Task)getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layout, null);
		}

		TextView txtTaskTitle = (TextView)convertView.findViewById(R.id.tv_task_title);
		if(txtTaskTitle != null) txtTaskTitle.setText(selectedItem.getName());

		return convertView;
	}
}
