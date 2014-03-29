package com.cocomsys.fragments_demo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cocomsys.fragments_demo.R;

/**
 * Created by Jimmy on 03-28-14.
 */
public class ClockFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.clock_layout, container, false);
	}
}