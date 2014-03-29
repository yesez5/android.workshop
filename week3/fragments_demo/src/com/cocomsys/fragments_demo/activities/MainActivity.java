package com.cocomsys.fragments_demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.cocomsys.fragments_demo.R;

/**
 * Created by Jimmy on 03-28-14.
 */
public class MainActivity extends FragmentActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
	}
}