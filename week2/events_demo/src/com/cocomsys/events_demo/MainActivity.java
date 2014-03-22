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
	Button btnGreet;
	TextView tvGreet;

	final String GREET_KEY = "GreetKey";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

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
}
