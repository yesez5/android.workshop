package com.cocomsys.admin_image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int SELECT_PICTURE = 1;
	private static final String TAG = MainActivity.class.getSimpleName();
	private String selectedImagePath;
	private Bitmap selectedImage;
	private ImageView ivImg;
	private Button btnLoad, btnRead, btnSave;
	StorageUtils storageUtils;
	private static final String IMG_PATH_KEY = "ImgPath";
	private static final String IMG_NAME_KEY = "ImgName";
	private static final String DEFAULT_IMG_NAME = "my_image";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				try{
					String path = getPathFromResult(data);
					setUiFromData(path);
				}catch (IllegalArgumentException ex){
					ex.printStackTrace();
					Toast.makeText(this, getString(R.string.error_retrieving_path), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private void init(){
		storageUtils = new StorageUtils(this);

		ivImg = (ImageView)findViewById(R.id.iv_img);
		btnLoad = (Button)findViewById(R.id.btn_load);
		btnSave = (Button)findViewById(R.id.btn_save);
		btnRead = (Button)findViewById(R.id.btn_read);

		btnLoad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchPicker();
			}
		});
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveImgInCache();
			}
		});
		btnRead.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				readImgFromCache();
			}
		});

	}

	private void launchPicker(){
		ImageUtils.launchGalleryPicker(MainActivity.this, SELECT_PICTURE, getString(R.string.select_img));
	}

	private String getPathFromResult(Intent data){
		Uri selectedImageUri = data.getData();
		return ImageUtils.getPath(this, selectedImageUri);
	}

	private void setUiFromData(String path){
		selectedImagePath = path;
		Log.i(TAG, "image path: " + selectedImagePath);
		if(!isValidPath()) return;
		selectedImage = BitmapFactory.decodeFile(selectedImagePath);
	    ivImg.setImageBitmap(selectedImage);
	}

	private void saveImgInCache(){
		if(!isValidPath()) return;
		try{
			ImageUtils.saveImageInCache(this, selectedImage, DEFAULT_IMG_NAME, Bitmap.CompressFormat.JPEG);
			saveImgData(selectedImagePath, DEFAULT_IMG_NAME);
			Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
		}catch (Exception ex){
			ex.printStackTrace();
			Toast.makeText(this, getString(R.string.error_saving), Toast.LENGTH_SHORT).show();
		}
	}

	private void saveImgData(String path, String name){
		storageUtils.save(IMG_PATH_KEY, path);
		storageUtils.save(IMG_NAME_KEY, name);
	}

	private void readImgFromCache(){
		String savedName = storageUtils.get(IMG_NAME_KEY, "");
		if(TextUtils.isEmpty(savedName)){
			Toast.makeText(this, getString(R.string.error_retrieving_path), Toast.LENGTH_SHORT).show();
		}else{
			this.selectedImage = ImageUtils.getImageFromCache(this, savedName);
			ivImg.setImageBitmap(selectedImage);
			Toast.makeText(this, getString(R.string.ready), Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isValidPath(){
		if(!TextUtils.isEmpty(selectedImagePath))
			return true;
		else{
			Toast.makeText(this, getString(R.string.error_retrieving_path), Toast.LENGTH_SHORT).show();
			return false;
		}
	}

}
