package com.cocomsys.todoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

	ArrayList<String> tasks;

	ListView lvTasks;
	EditText txtSaveTask;
	Button btnSaveTask;
	TaskListAdapter adapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		init();
	}

	private void init(){
		txtSaveTask = (EditText)findViewById(R.id.txt_save_task);
		btnSaveTask = (Button)findViewById(R.id.btn_save_task);
		lvTasks     = (ListView)findViewById(R.id.lv_tasks);

		tasks = new ArrayList<String>();
		//popular con datos x defecto
		for (int i = 1; i <= 40; i++){
			tasks.add(String.format(getString(R.string.dummy_task_title), i));
		}

		adapter = new TaskListAdapter(this, R.layout.task_list_item_layout, tasks);
		lvTasks.setAdapter(adapter);

		btnSaveTask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTask();
			}
		});
	}

	private void addTask(){
		String taskTitle = String.valueOf(txtSaveTask.getText());
		if(!taskTitle.isEmpty()){
			tasks.add(taskTitle);
			txtSaveTask.setText("");
			adapter.notifyDataSetChanged(); //notificar a adapter el cambio en la colección
		}
		else
			Toast.makeText(this, getString(R.string.insert_a_value), Toast.LENGTH_SHORT).show();
	}
}
