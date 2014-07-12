package com.cocomsys.db101.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yesez on 07-12-14.
 */
public class DbHandler extends SQLiteOpenHelper {

	private static String DB_NAME = "countries";
	private static int DB_VERSION = 1;

	public DbHandler(Context ctx){
		super(ctx, DB_NAME, null, DB_VERSION);
	}

	public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CountryDbHandler.Sentences.CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(CountryDbHandler.Sentences.DELETE);
		onCreate(db);
	}
}
