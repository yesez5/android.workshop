package com.cocomsys.http101;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

	HttpService service;
	ListView lvData;
	VideosAdapter adapter;
	ArrayList<VideoItem> modelList;

	private enum HttpMethod{
		HTTP_CLIENT,
		OK_HTTP,
		VOLLEY
	}

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
				getData(HttpMethod.HTTP_CLIENT);
			}
		});
		findViewById(R.id.btn_okhttp).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getData(HttpMethod.OK_HTTP);
			}
		});
	}

	private ProgressDialog progress;
	private void isShowProgress(boolean isLoading){
		if(isLoading){
			progress = ProgressDialog.show(this,
					getString(R.string.app_name),
					getString(R.string.loading),
					true, true);
		}else if(progress != null){
			progress.dismiss();
		}
	}

	private void getData(HttpMethod method){
		isShowProgress(true);
		this.modelList.clear();
		this.adapter.notifyDataSetChanged();

		new GetDataTask().execute(method);
	}

	private void setUiFromData(ArrayList<VideoItem> data){
		isShowProgress(false);
		this.modelList.addAll(data);
		this.adapter.notifyDataSetChanged();
	}

	private class GetDataTask extends AsyncTask<HttpMethod, Void, ArrayList<VideoItem>>{
		@Override
		protected ArrayList<VideoItem> doInBackground(HttpMethod... params) {
			HttpMethod method = params[0];
			String response = "";
			switch (method){
				case HTTP_CLIENT:
					response = service.getByApacheHttpClient();
					break;
				case OK_HTTP:
					response = service.getByOkHttp();
					break;
			}
			return service.parseToModelWithGson(response);
		}

		@Override
		protected void onPostExecute(ArrayList<VideoItem> data) {
			setUiFromData(data);
		}
	}
}
