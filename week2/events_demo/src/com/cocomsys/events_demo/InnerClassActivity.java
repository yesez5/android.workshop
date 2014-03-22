package com.cocomsys.events_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Jimmy on 03-21-14.
 */
public class InnerClassActivity extends Activity {
	Button btnGreet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnGreet = (Button)findViewById(R.id.btn_greet);
		btnGreet.setOnClickListener(new onBtnGreetClick());
	}

	private class onBtnGreetClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Toast.makeText(InnerClassActivity.this, "hello from inner class", Toast.LENGTH_LONG).show();
		}
	}
}
