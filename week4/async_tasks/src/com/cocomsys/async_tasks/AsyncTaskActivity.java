package com.cocomsys.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class AsyncTaskActivity extends BaseTaskActivity {

	@Override
	protected void loadDataAsync(){
		super.loadDataAsync();
		new LoadDataTask().execute();
	}

	private class LoadDataTask extends AsyncTask<Void, Void, ArrayList<Country>>{

		@Override
		protected ArrayList<Country> doInBackground(Void... params) {
			return Country.generate(10, 3);
		}

		@Override
		protected void onPostExecute(ArrayList<Country> list) {
			onLoadedDataAsync(list);
		}
	}

}