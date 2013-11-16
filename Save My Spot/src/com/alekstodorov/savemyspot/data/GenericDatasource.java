package com.alekstodorov.savemyspot.data;

import java.util.List; 
import android.content.Context; 
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericDatasource<T> implements IDatasource<T>, IReadable {
	protected DbTools dbTools;
	protected SQLiteDatabase database;
  
	public GenericDatasource(Context context) {

		this.dbTools = DbTools.getDatabaseInstance(context); 
		database = dbTools.getWritableDatabase();
	} 

	public abstract T create(T entry);

	public abstract List<T> findAll();

	public abstract T findById(long id);
 
	public void open() {

		database = dbTools.getWritableDatabase();
	}

	public void close() {

		dbTools.close();
	}
}
