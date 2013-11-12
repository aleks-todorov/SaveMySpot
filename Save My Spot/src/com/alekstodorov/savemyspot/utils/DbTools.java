package com.alekstodorov.savemyspot.utils;

import java.util.ArrayList;
  
import com.alekstodorov.savemyspot.models.UserModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbTools extends SQLiteOpenHelper {

	private final static String DB_NAME = "saveMySpot.db";

	public DbTools(Context context) {
		super(context, DB_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		  
		String query = "CREATE TABLE users (userId INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "userName TEXT NOT NULL, password TEXT NOT NULL, authCode TEXT, isLoggedIn NUMERIC)";

		database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String query = "DROP TABLE IF EXISTS users";

		db.execSQL(query);

		onCreate(db);
	}

	public void insertUser(UserModel user) {

		SQLiteDatabase database = this.getWritableDatabase();

		ContentValues values = new ContentValues();
 
		if(user.isLoggedIn() == true){
			resetIsLoggedValue();
		}
		
		values.put("userName", user.getUsername());
		values.put("password", user.getPassword());
		values.put("authCode", user.getAuthCode());
		values.put("isLoggedIn", 1);

		database.insert("users", null, values);

		database.close();
		Log.i(HelpUtilities.TAG, "Inserted into Db");
	}

	public ArrayList<UserModel> getAllUsers() {

		ArrayList<UserModel> usersArrayList = new ArrayList<UserModel>();

		String selectQuery = "SELECT * FROM users ORDER BY userName";

		SQLiteDatabase database = this.getWritableDatabase();

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {

			do {

				UserModel user = new UserModel();

				user = getUserInformationFromDb(cursor);

				usersArrayList.add(user);

			} while (cursor.moveToNext());
		}

		return usersArrayList;
	}

	public UserModel getUserInfo(String userName) {

		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM users WHERE userName ='" + userName
				+ "'";

		Cursor cursor = database.rawQuery(selectQuery, null);

		UserModel user = new UserModel();

		if (cursor.moveToFirst()) {

			do {

				user = getUserInformationFromDb(cursor);

			} while (cursor.moveToNext());
		}

		if (user.getUsername() == null) {
			return null;
		} else {
			return user;
		}
	}
	
	public UserModel getLoggedUser() {

		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM users WHERE isLoggedIn = 1";

		Cursor cursor = database.rawQuery(selectQuery, null);

		UserModel user = new UserModel();

		if (cursor.moveToFirst()) {

			do {

				user = getUserInformationFromDb(cursor);

			} while (cursor.moveToNext());
		}

		if (user.getUsername() == null) {
			return null;
		} else {
			return user;
		}
	}

	private UserModel getUserInformationFromDb(Cursor cursor) {

		UserModel user = new UserModel();

		user.setUsername(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		user.setAuthCode(cursor.getString(3));
		  
		if ( cursor.getString(4).equals(String.valueOf(1))  ) {
			user.setLoggedIn(true);
		} else {
			user.setLoggedIn(false);
		}
		
		return user;
	}
	
	public int updateContact(UserModel user) {

		SQLiteDatabase database = this.getWritableDatabase();

		if(user.isLoggedIn() == true){
			resetIsLoggedValue();
		}
		
		ContentValues values = new ContentValues();
 
		if(user.isLoggedIn() == true){
			values.put("isLoggedIn", 1);
		} else {
			values.put("isLoggedIn", 0);
		} 
		
		return database.update("users", values, "userName" + " = ?",
				new String[] { user.getUsername() });
	}
	
	public void resetIsLoggedValue(){
		
		ArrayList<UserModel> users = getAllUsers();
		
		for (UserModel user : users) {
			
			user.setLoggedIn(false);
			
			updateContact(user); 
		}
		
	}
}
