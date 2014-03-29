package com.cocomsys.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.cocomsys.todoapp.common.AppConstants;
import com.cocomsys.todoapp.models.Task;

import java.util.Date;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	ListView lvTasks;
	EditText txtSaveTask;
	Button btnSaveTask;
	BaseTaskListAdapter adapter;

	private String[] getContextualMenu(){
		return getResources().getStringArray(R.array.menu);
	}

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lv_tasks) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(Task.getTasks().get(info.position).getName());
			String[] menuItems = getContextualMenu();
			for (int i = 0; i< menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return executeMenuItemAction(item);
	}

	private void init(){
		txtSaveTask = (EditText)findViewById(R.id.txt_save_task);
		btnSaveTask = (Button)findViewById(R.id.btn_save_task);
		lvTasks     = (ListView)findViewById(R.id.lv_tasks);

		//popular con datos x defecto
		//generateDummyData();

		adapter = new BaseTaskListAdapter(this, R.layout.task_list_item_layout, Task.getTasks());
		lvTasks.setAdapter(adapter);
		registerForContextMenu(lvTasks);

		btnSaveTask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTask();
			}
		});

		lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Task selectedTask = (Task)parent.getItemAtPosition(position);
				if(selectedTask != null)
					openDetailsView(selectedTask.getName(), position, selectedTask.getId());
				else
					Toast.makeText(MainActivity.this,
							getString(R.string.something_goes_wrong), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void generateDummyData() {
		for (int i = 1; i <= 40; i++){
			Task task = new Task();
			task.setId(i);
			task.setName(String.format(getString(R.string.dummy_task_title), i));
			task.setCreationDate(new Date());
			Task.getTasks().add(task);
		}
	}

	private Boolean executeMenuItemAction(MenuItem item){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		Log.i(TAG, "menu item index: " + menuItemIndex);
		if(info != null){
			Task selectedTask = Task.getTasks().get(info.position);
			Log.i(TAG, "selected item: " + selectedTask.getName());

			switch (menuItemIndex){
				case 0: //detalle
					openDetailsView(selectedTask.getName(), info.position, selectedTask.getId());
					break;
				case 1: //eliminar
					showDeleteConfirmDialog(selectedTask);
					break;
			}
		}
		return true;
	}

	private void openDetailsView(String name, int pos, int id){
		Intent detailsIntent = new Intent(this, DetailsActivity.class);
		detailsIntent.putExtra(AppConstants.TASK_TITLE_KEY, name);
		detailsIntent.putExtra(AppConstants.TASK_POS_KEY, pos);
		detailsIntent.putExtra(AppConstants.TASK_ID_KEY, id);
		startActivity(detailsIntent);
	}

	private void addTask(){
		String taskTitle = String.valueOf(txtSaveTask.getText());
		if(!taskTitle.isEmpty()){
			Task task = new Task();
			task.setId(Task.getTasks().size() + 1);
			task.setName(taskTitle);
			task.setCreationDate(new Date());
			Task.getTasks().add(task);

			txtSaveTask.setText("");
			adapter.notifyDataSetChanged(); //notificar a adapter el cambio en la colección
		}
		else
			Toast.makeText(this, getString(R.string.insert_a_value), Toast.LENGTH_SHORT).show();
	}

	private void deleteTask(int taskId){
		int posById = 0;
		for(int i = 0; i < Task.getTasks().size(); i ++){
			Task task = Task.getTasks().get(i);
			if(task.getId() == taskId){
				posById = i;
				Log.i(TAG, "item to delete found! " + posById + " id " + task.getId() + " " + task.getName());
				break;
			}
		}
		Task.getTasks().remove(posById);
		adapter.notifyDataSetChanged();
	}

	private void showDeleteConfirmDialog(final Task selectedTask){
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(getString(R.string.delete))
				.setMessage(getString(R.string.are_you_sure))
				.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteTask(selectedTask.getId());
					}

				})
				.setNegativeButton(getString(R.string.no), null)
				.show();
	}
}