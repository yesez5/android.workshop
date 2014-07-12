package com.cocomsys.http101;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

	HttpService service;
	ListView lvData;
	VideosAdapter adapter;
	ArrayList<VideoItem> modelList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init(){
		service = new HttpService();
		modelList = new ArrayList<VideoItem>();
		adapter = new VideosAdapter(modelList, this);

		lvData = (ListView)findViewById(R.id.lv_data);
		lvData.setAdapter(adapter);

		findViewById(R.id.btn_httpclient).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getByHttpClient();
			}
		});
	}

	private void getByHttpClient(){
		new HttpClientTask().execute();
	}

	private void setUiFromData(ArrayList<VideoItem> data){
		this.modelList.clear();
		this.modelList.addAll(data);
		this.adapter.notifyDataSetChanged();
	}

	private class HttpClientTask extends AsyncTask<Void, Void, ArrayList<VideoItem>>{
		@Override
		protected ArrayList<VideoItem> doInBackground(Void... params) {
			String response = service.getByApacheHttpClient();
			return service.parseToModelWithGson(response);
		}

		@Override
		protected void onPostExecute(ArrayList<VideoItem> data) {
			setUiFromData(data);
		}
	}
}
