package com.cocomsys.admin_image;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yesez on 07-27-14.
 */
public class StorageUtils {

	private static final String CACHE_NAME = "cache";
	private Context ctx;

	public StorageUtils(Context ctx){
		this.ctx = ctx;
	}

	public void save(String key, String value){
		SharedPreferences prefs =
				ctx.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String get(String key, String defaultValue){
		SharedPreferences prefs =
				ctx.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
		return prefs.getString(key, defaultValue);
	}
}
