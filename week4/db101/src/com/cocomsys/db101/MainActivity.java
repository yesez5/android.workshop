package com.cocomsys.db101;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.cocomsys.db101.db.CountryRepository;
import com.cocomsys.db101.db.DbHandler;
import com.cocomsys.db101.models.Country;

import java.util.ArrayList;

//http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
public class MainActivity extends Activity {

	EditText etName;
	Button btnAdd;
	ListView lvCountries;

	DbHandler db;
	ArrayList<Country> modelList;
	CountriesAdapter adapter;
	CountryRepository repository;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init(){
		db = new DbHandler(getApplicationContext());
		repository = new CountryRepository(db);

		modelList = new ArrayList<Country>();
		adapter = new CountriesAdapter(modelList, this);

		etName = (EditText)findViewById(R.id.et_name);
		btnAdd = (Button)findViewById(R.id.btn_add);
		lvCountries = (ListView)findViewById(R.id.lv_countries);

		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addRecord();
			}
		});

		lvCountries.setAdapter(adapter);

		setFromDb();
	}

	private void setModelFromUi(Country model){
		model.setId(1);
		model.setName(String.valueOf(etName.getText()));
		model.setAbrev(model.getName().substring(0, 1));
	}

	private void addRecord(){
		Country selectedModel = new Country();
		setModelFromUi(selectedModel);
		if(!isValid(selectedModel)) return;
		repository.add(selectedModel);

		modelList.add(selectedModel);
		adapter.notifyDataSetChanged();

		etName.setText("");
		Toast.makeText(this, getString(R.string.record_saved), Toast.LENGTH_SHORT).show();
	}

	private boolean isValid(Country model){
		if(TextUtils.isEmpty(model.getName())) {
			etName.setError(getString(R.string.fill_the_field));
			return false;
		}
		return true;
	}

	private void setFromDb(){
		ArrayList<Country> listFromDb = new ArrayList<Country>(repository.getAll());
		modelList.clear();
		modelList.addAll(listFromDb);
		adapter.notifyDataSetChanged();
	}


}
