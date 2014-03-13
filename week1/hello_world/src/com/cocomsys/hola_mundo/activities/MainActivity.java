package com.cocomsys.hola_mundo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.cocomsys.hola_mundo.R;

public class MainActivity extends Activity {
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
		initUI();
	}

	private void initUI(){
		final EditText txtValue = (EditText)findViewById(R.id.txt_value);
		Button btnGreet = (Button)findViewById(R.id.btn_greet);

		btnGreet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String value = String.valueOf(txtValue.getText());
				Toast.makeText(MainActivity.this,
						String.format(getString(R.string.hello_value), value),
						Toast.LENGTH_LONG)
						.show();
			}
		});

	}
}
