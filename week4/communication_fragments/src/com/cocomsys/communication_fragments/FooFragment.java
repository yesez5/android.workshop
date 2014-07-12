package com.cocomsys.communication_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by yesez on 07-07-14.
 */
public class FooFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BusProvider.getInstance().register(this);
	}

	@Override
	public void onDestroy() {
		BusProvider.getInstance().unregister(this);
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.foo_fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendData();
			}
		});
	}

	private void sendData(){
		EditText txtData = (EditText)getActivity().findViewById(R.id.et_data);
		String data = String.valueOf(txtData.getText());
		BusProvider.getInstance().post(new SendDataEvent(data));
	}
}