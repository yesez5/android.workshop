package com.cocomsys.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		init();
	}

	private void init(){
		Button btnLinear = (Button)findViewById(R.id.btn_linear);
		btnLinear.setOnClickListener(this);
		Button btnRelative = (Button)findViewById(R.id.btn_relative);
		btnRelative.setOnClickListener(this);
		Button btnFrame = (Button)findViewById(R.id.btn_frame);
		btnFrame.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_linear:
				startActivity(new Intent(this, LinearLayoutActivity.class));
				break;
			case R.id.btn_relative:
				startActivity(new Intent(this, RelativeLayoutActivity.class));
				break;
			case R.id.btn_frame:
				startActivity(new Intent(this, FrameLayoutActivity.class));
				break;
			default:
				break;
		}
	}
}
