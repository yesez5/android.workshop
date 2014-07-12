package com.cocomsys.async_tasks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BaseTaskActivity extends Activity {

	ListView lvCountries;
	CountriesAdapter adapter;
	ArrayList<Country> model;
	int counter = 0;
	Button btnLoad, btnCounter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_layout);
		init();
	}

	protected void init(){
		lvCountries = (ListView)findViewById(R.id.lv_countries);
		model = new ArrayList<Country>();
		adapter = new CountriesAdapter(model, this);
		lvCountries.setAdapter(adapter);

		btnLoad = (Button)findViewById(R.id.btn_load);
		btnCounter = (Button)findViewById(R.id.btn_counter);
		btnCounter.setEnabled(false);

		btnLoad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadDataAsync();
			}
		});
		btnCounter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				counter += 1;
				btnCounter.setText(String.valueOf(counter));
			}
		});
	}

	protected void showProgress(boolean isProgress){
		if(isProgress)
			btnLoad.setText(getString(R.string.loading));
		else
			btnLoad.setText(getString(R.string.load_data));

		btnLoad.setEnabled(!isProgress);
		btnCounter.setEnabled(isProgress);
	}

	protected void fillList(ArrayList<Country> newList){
		model.clear();
		model.addAll(newList);
	}

	protected void loadDataSync(){
		fillList(Country.generate(10, 3));
		adapter.notifyDataSetChanged();
	}

	protected void loadDataAsync(){
		showProgress(true);
		//TODO: implementar carga de datos
	}

	protected void onLoadedDataAsync(ArrayList<Country> list){
		showProgress(false);
		fillList(list);
		adapter.notifyDataSetChanged();
	}

}
