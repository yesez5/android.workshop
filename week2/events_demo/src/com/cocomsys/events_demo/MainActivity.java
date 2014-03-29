package com.cocomsys.events_demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String GREET_KEY = "GreetKey";
	Button btnGreet;
	TextView tvGreet;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d("State", "onCreate");

		btnGreet = (Button)findViewById(R.id.btn_greet);
		btnGreet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "hello from anonymous function", Toast.LENGTH_LONG).show();
			}
		});

		tvGreet = (TextView)findViewById(R.id.tv_greet);

		if(savedInstanceState != null && savedInstanceState.containsKey(GREET_KEY)){
			Toast.makeText(this, "saved text: " + savedInstanceState.get(GREET_KEY), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		String greet = String.valueOf(tvGreet.getText());
		Log.i(TAG, "saving in bundle");
		outState.putString(GREET_KEY, greet);
	}

	@Override
	public void onStart() {
		Log.d("State", "onStart");
		super.onStart();
	}
	@Override
	public void onResume() {
		Log.d("State", "onResume");
		super.onResume();
	}
	@Override
	public void onPause() {
		Log.d("State", "onPause");
		super.onPause();
	}
	@Override
	public void onStop() {
		Log.d("State", "onStop");
		super.onStop();
	}
	@Override
	public void onDestroy() {
		Log.d("State", "onDestroy");
		super.onDestroy();
	}
	@Override
	public void onRestart() {
		Log.d("State", "onRestart");
		super.onRestart();
	}
}
