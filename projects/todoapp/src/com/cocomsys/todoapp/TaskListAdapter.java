package com.cocomsys.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.cocomsys.todoapp.models.Task;

import java.util.ArrayList;

/**
 * Created by Jimmy on 03-08-14.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {
	int layout;

	public TaskListAdapter(Context context, int layout, ArrayList<Task> list) {
		super(context, layout, list);
		this.layout = layout;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Task selectedItem = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(layout, null);
		}

		TextView txtTaskTitle = (TextView)convertView.findViewById(R.id.tv_task_title);
		if(txtTaskTitle != null) txtTaskTitle.setText(selectedItem.getName());

		return convertView;
	}
}
