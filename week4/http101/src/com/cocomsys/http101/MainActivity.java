package com.cocomsys.http101;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	HttpService service;
	TextView tvResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init(){
		service = new HttpService();

		tvResult = (TextView)findViewById(R.id.tv_result);
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

	private void setUiFromData(String data){
		tvResult.setText(data);
	}

	private class HttpClientTask extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			return service.getByApacheHttpClient();
		}

		@Override
		protected void onPostExecute(String data) {
			setUiFromData(data);
		}
	}
}
