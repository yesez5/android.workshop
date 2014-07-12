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
		service = new HttpService(this);
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
		findViewById(R.id.btn_volley).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getData(HttpMethod.VOLLEY);
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

		switch (method){
			case HTTP_CLIENT:
			case OK_HTTP:
				new GetDataTask().execute(method);
				break;
			case VOLLEY:
				getByVolley();
				break;
		}
	}

	private void getByVolley(){
		service.getByVolley(new OnRequestCompletedListener() {
			@Override
			public void onResponse(String response) {
				ArrayList<VideoItem> list = service.parseToModelWithGson(response);
				setUiFromData(list);
			}
			@Override
			public void onErrorResponse(Throwable error) {
				//TODO: algo salió mal
			}
		});
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
