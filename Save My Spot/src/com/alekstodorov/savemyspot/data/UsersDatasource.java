package com.alekstodorov.savemyspot.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alekstodorov.savemyspot.models.UserModel;
import com.alekstodorov.savemyspot.utils.HelpUtilities;

public class UsersDatasource extends GenericDatasource<UserModel> {

	public UsersDatasource(Context context) {
		super(context);
	}
 
	public UserModel getLoggedUser() {

		String selectQuery = "SELECT * FROM " + DbTools.TABLE_USERS + " WHERE "
				+ DbTools.TABLE_USERS_ISLOGGEDIN + " = 1";

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
			Log.v(HelpUtilities.TAG, user.getUserId() + " Id");
			return user;
		}
	}
 
	public int updateContact(UserModel user) {

		if (user.isLoggedIn() == true) {
			resetIsLoggedValue();
		}

		ContentValues values = new ContentValues();

		if (user.isLoggedIn() == true) {
			values.put(DbTools.TABLE_USERS_ISLOGGEDIN, 1);
		} else {
			values.put(DbTools.TABLE_USERS_ISLOGGEDIN, 0);
		}

		return database.update(DbTools.TABLE_USERS, values,
				DbTools.TABLE_USERS_USERNAME + " = ?",
				new String[] { user.getUsername() });
	}

	@Override
	public UserModel create(UserModel user) {
		resetIsLoggedValue();
		ContentValues values = new ContentValues();

		if (user.isLoggedIn() == true) {
			resetIsLoggedValue();
		}

		values.put(DbTools.TABLE_USERS_USERNAME, user.getUsername());
		values.put(DbTools.TABLE_USERS_PASSWORD, user.getPassword());
		values.put(DbTools.TABLE_USERS_ISLOGGEDIN, 1);

		database.insert(DbTools.TABLE_USERS, null, values);
 
		return user;
	}

	@Override
	public List<UserModel> findAll() {
		
		ArrayList<UserModel> usersArrayList = new ArrayList<UserModel>();

		String selectQuery = "SELECT * FROM " + DbTools.TABLE_USERS;
				  
		SQLiteDatabase database = dbTools.getWritableDatabase();

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

	@Override
	public UserModel findById(long id) {
		SQLiteDatabase database = dbTools.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + DbTools.TABLE_USERS + " WHERE "
				+ DbTools.TABLE_USERS_ID + " = '" + id + "'";

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
	 
	public UserModel findByName(String name) {
		SQLiteDatabase database = dbTools.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + DbTools.TABLE_USERS + " WHERE "
				+ DbTools.TABLE_USERS_USERNAME + " = '" + name + "'";

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
			Log.v(HelpUtilities.TAG, user.getUserId() + " Id");
			return user;
		}
	}

	private void resetIsLoggedValue() {

		List<UserModel> users = findAll();

		for (UserModel user : users) {

			user.setLoggedIn(false);

			updateContact(user);
		}
	}

	private UserModel getUserInformationFromDb(Cursor cursor) {

		UserModel user = new UserModel();

		user.setUserId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		  
		if (cursor.getString(3).equals(String.valueOf(1))) {
			user.setLoggedIn(true);
		} else {
			user.setLoggedIn(false);
		}

		return user;
	}
}
