package com.cocomsys.db101.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cocomsys.db101.models.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryRepository {

	DbHandler dbHandler;
	public CountryRepository(DbHandler dbHandler){
		this.dbHandler = dbHandler;
	}

	public void add(Country model) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CountryDbHandler.Fields.ID, model.getId());
		values.put(CountryDbHandler.Fields.NAME, model.getName());
		values.put(CountryDbHandler.Fields.ABREV, model.getAbrev());

		if(db != null){
			db.insert(CountryDbHandler.NAME, null, values);
			db.close();
		}
	}

	public List<Country> getAll() {
		List<Country> modelList = new ArrayList<Country>();

		SQLiteDatabase db = dbHandler.getReadableDatabase();
		if(db == null) throw new NullPointerException("db");
		Cursor cursor = db.rawQuery(CountryDbHandler.Sentences.SELECT_ALL, null);

		if (cursor.moveToFirst()) {
			do {
				Country model = new Country();
				model.setId(Integer.parseInt(cursor.getString(0)));
				model.setName(cursor.getString(1));
				model.setAbrev(cursor.getString(2));
				modelList.add(model);
			} while (cursor.moveToNext());
		}

		return modelList;
	}
}
