package com.cocomsys.admin_image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by yesez on 07-27-14.
 */
public class ImageUtils {
	private static final String TAG = ImageUtils.class.getSimpleName();

	public static void saveImageInCache(Context context, Bitmap b,String name, Bitmap.CompressFormat format){
		FileOutputStream out;
		try {
			out = context.openFileOutput(name, Context.MODE_PRIVATE);
			b.compress(format, 95, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Bitmap getImageFromCache(Context context,String name){
		try{
			FileInputStream fis = context.openFileInput(name);
			Bitmap b = BitmapFactory.decodeStream(fis);
			fis.close();
			return b;
		}
		catch (FileNotFoundException ex){ Log.e(TAG, String.format("archivo %s no encontrado", name)); }
		catch(Exception e){ e.printStackTrace(); }
		return null;
	}

	public static void deleteImageInCache(Context context,String name){
		try{
			context.deleteFile(name);
		} catch(Exception e){ Log.e(TAG, e.getMessage() + e.getCause()); }
	}

	public static void launchGalleryPicker(Activity activity, int id, String msg){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(Intent.createChooser(intent,
				msg), id);
	}

	public static String getPath(Activity activity, Uri uri) throws IllegalArgumentException{
		if(uri == null) return null;
		String path = "";
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				int columnIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
				cursor.close();
			}
		}
		else{
			path = uri.getPath();
		}
		return path;
	}

}
