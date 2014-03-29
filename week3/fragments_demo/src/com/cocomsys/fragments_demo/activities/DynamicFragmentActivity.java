package com.cocomsys.fragments_demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.cocomsys.fragments_demo.R;
import com.cocomsys.fragments_demo.fragments.CalcFragment;
import com.cocomsys.fragments_demo.fragments.ClockFragment;

/**
 * Created by Jimmy on 03-29-14.
 */
public class DynamicFragmentActivity extends FragmentActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_fragment_layout);

		addFragments();
	}

	private void addFragments(){
		Fragment clockFragment = new ClockFragment();
		Fragment calcFragment = new CalcFragment();
		FragmentManager manager = getSupportFragmentManager();
		//Start a series of edit operations on the Fragments associated with this FragmentManager.
		manager.beginTransaction()
				.add(R.id.layout_frg_clock, clockFragment) //Add a fragment to the activity state
				.add(R.id.layout_frg_calc,  calcFragment)
				.commit();
		//finish transation
		//Schedules a commit of this transaction. The commit does not happen immediately; it will be scheduled as work on the main thread to be done the next time that thread is ready.
	}

}