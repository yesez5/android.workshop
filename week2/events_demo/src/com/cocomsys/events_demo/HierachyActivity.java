package com.cocomsys.events_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Jimmy on 03-21-14.
 */
public class HierachyActivity extends Activity implements View.OnClickListener {
	Button btnGreet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnGreet = (Button)findViewById(R.id.btn_greet);
		btnGreet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_greet:
				Toast.makeText(HierachyActivity.this, "hello from hierachy", Toast.LENGTH_LONG).show();
				break;
		}
	}
}
