package com.cocomsys.http101;

/**
 * Created by yesez on 07-12-14.
 */
public interface OnRequestCompletedListener {
	void onResponse(String response);
	void onErrorResponse(Throwable error);
}
