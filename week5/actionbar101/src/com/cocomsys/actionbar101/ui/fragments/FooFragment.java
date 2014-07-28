package com.cocomsys.actionbar101.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cocomsys.actionbar101.R;

/**
 * Created by Programador on 28/07/2014.
 */
public class FooFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.foo_fragment_layout, container, false);
    }
}