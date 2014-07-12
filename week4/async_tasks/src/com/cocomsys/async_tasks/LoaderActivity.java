package com.cocomsys.async_tasks;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yesez on 07-06-14.
 */
public class LoaderActivity extends BaseTaskActivity
		implements LoaderManager.LoaderCallbacks<ArrayList<Country>>  {

	@Override
	protected void loadDataAsync(){
		super.loadDataAsync();
		//http://developer.android.com/guide/components/loaders.html
		//When you use initLoader(), it uses an existing loader with the specified ID if there is one.
		//If there isn't, it creates one. But sometimes you want to discard your old data and start over.
		Loader loader = getLoaderManager().restartLoader(0, null, this);
		loader.forceLoad();
	}

	@Override
	public Loader<ArrayList<Country>> onCreateLoader(int id, Bundle args) {
		return new LoadDataLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Country>> loader, ArrayList<Country> data) {
		onLoadedDataAsync(data);
	}

	@Override
	public void onLoaderReset(Loader loader) {}

	protected static class LoadDataLoader extends AsyncTaskLoader<ArrayList<Country>> {

		public LoadDataLoader(Context context) {
			super(context);
		}

		@Override
		public ArrayList<Country> loadInBackground() {
			return Country.generate(10, 3);
		}
	}

}