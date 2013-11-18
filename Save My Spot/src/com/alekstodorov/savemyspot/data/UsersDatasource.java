package com.alekstodorov.savemyspot.data;

import java.util.ArrayList;
import java.util.List; 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 
import com.alekstodorov.savemyspot.models.UserModel; 

public class UsersDatasource extends GenericDatasource<UserModel> {
	private Context currentContext;

	public UsersDatasource(Context context) {
		super(context);

		currentContext = context;
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
		values.put(DbTools.TABLE_USERS_AUTH_CODE, user.getAuthCode());
		values.put(DbTools.TABLE_USERS_ISLOGGEDIN, 1);

		database.insert(DbTools.TABLE_USERS, null, values);
 
		//Seeding method for presenting test data. Executed only on create.
		seedDbOnUserCreation();

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
		user.setAuthCode(cursor.getString(3));
 
		if (cursor.getString(4).equals(String.valueOf(1))) {
			user.setLoggedIn(true);
		} else {
			user.setLoggedIn(false);
		}

		return user;
	}
	
	private void seedDbOnUserCreation() {
		
		UowData uowData = new UowData(currentContext);
		((IReadable) uowData).open();
		
		SpotsDatasource spotsDatasourse = (SpotsDatasource) uowData.getSpots();
		 
		spotsDatasourse.seedDatabase(currentContext);
		
		((IReadable) uowData).close();
	}
}
