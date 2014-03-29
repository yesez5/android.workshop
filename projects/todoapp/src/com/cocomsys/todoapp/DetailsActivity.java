package com.cocomsys.todoapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.cocomsys.todoapp.common.AppConstants;

/**
 * Created by Jimmy on 03-08-14.
 */
public class DetailsActivity extends Activity {

	TextView txtTaskTitle;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_details);

		init();
	}

	private void init(){
		initUI();
		getParams();
	}

	private void initUI(){
		this.txtTaskTitle = (TextView)findViewById(R.id.tv_task_title);
	}

	private void getParams(){
		String selectedTask = getIntent().getStringExtra(AppConstants.TASK_TITLE_KEY);
		int selectedTaskPos = getIntent().getIntExtra(AppConstants.TASK_POS_KEY, 0);
		int selectedTaskId = getIntent().getIntExtra(AppConstants.TASK_ID_KEY, 0);

		this.txtTaskTitle.setText(String.format("tarea: %s posición: %d id: %d",
				selectedTask, selectedTaskPos, selectedTaskId));
	}
}