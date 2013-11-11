package com.alekstodorov.savemyspot.data;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbTools extends SQLiteOpenHelper {

	private final static String DB_NAME = "saveMySpot.db";

	public DbTools(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		String query = "CREATE TABLE users (userId INTEGER PRIMARY KEY, "
				+ "userName TEXT NOT NULL, password TEXT NOT NULL)";

		database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String query = "DROP TABLE IF EXISTS users";

		db.execSQL(query);

		onCreate(db);
	}

	public void insertUser(HashMap<String, String> queryValues) {

		SQLiteDatabase database = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("userName", queryValues.get("userName"));
		values.put("password", queryValues.get("password"));

		database.insert("users", null, values);

		database.close();
	}

	public ArrayList<HashMap<String, String>> getAllUsers() {

		ArrayList<HashMap<String, String>> usersArrayList = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT * FROM users ORDER BY userName";

		SQLiteDatabase database = this.getWritableDatabase();

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {

			do {
				HashMap<String, String> userMap = new HashMap<String, String>();

				userMap = getUserInformationFromDb(cursor);

				usersArrayList.add(userMap);

			} while (cursor.moveToNext());
		}

		return usersArrayList;
	}

	public HashMap<String, String> getUserInfo(String id) {

		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM users WHERE userId ='" + id + "'";

		Cursor cursor = database.rawQuery(selectQuery, null);

		HashMap<String, String> userMap = new HashMap<String, String>();

		if (cursor.moveToFirst()) {

			do {

				userMap = getUserInformationFromDb(cursor);

			} while (cursor.moveToNext());
		}

		return userMap;
	}

	private HashMap<String, String> getUserInformationFromDb(Cursor cursor ) {
		
		HashMap<String, String> userMap = new HashMap<String, String>();
		
		userMap.put("userId", cursor.getString(0));
		userMap.put("userName", cursor.getString(1));
		userMap.put("password", cursor.getString(2));

		return userMap;
	}
}
