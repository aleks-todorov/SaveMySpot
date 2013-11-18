package com.alekstodorov.savemyspot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbTools extends SQLiteOpenHelper {

	private final static String DB_NAME = "saveMySpot.db";
	public final static String TABLE_USERS = "users";
	public final static String TABLE_SPOTS = "spots";

	private static DbTools dataBaseInstance = null;

	public static final String TABLE_USERS_ID = "userId";
	public static final String TABLE_USERS_USERNAME = "userName";
	public static final String TABLE_USERS_PASSWORD = "password";
	public static final String TABLE_USERS_AUTH_CODE = "authCode";
	public static final String TABLE_USERS_ISLOGGEDIN = "isLoggedIn";
	private static final String CREATE_TABLE_USERS = "CREATE TABLE "
			+ TABLE_USERS + " (" + TABLE_USERS_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_USERS_USERNAME
			+ " TEXT NOT NULL," + TABLE_USERS_PASSWORD + " TEXT NOT NULL, "
			+ TABLE_USERS_AUTH_CODE + " TEXT NOT NULL, "
			+ TABLE_USERS_ISLOGGEDIN + " NUMERIC NOT NULL)";

	public static final String TABLE_SPOTS_ID = "spotId";
	public static final String TABLE_SPOTS_TITLE = "spotTitle";
	public static final String TABLE_SPOTS_LATITUDE = "spotLatitude";
	public static final String TABLE_SPOTS_LONGITUDE = "spotLongitude";
	public static final String TABLE_SPOTS_AUTHCODE = "authCode";
	public static final String TABLE_SPOTS_USERID = "userId";
	private static final String CREATE_TABLE_SPOTS = "CREATE TABLE "
			+ TABLE_SPOTS + " (" + TABLE_SPOTS_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_SPOTS_TITLE
			+ " TEXT NOT NULL, " + TABLE_SPOTS_LATITUDE + " REAL NOT NULL, "
			+ TABLE_SPOTS_LONGITUDE + " REAL NOT NULL, " + TABLE_SPOTS_USERID
			+ " INTEGER NOT NULL," + TABLE_SPOTS_AUTHCODE + " TEXT NOT NULL,"
			+ " FOREIGN KEY(" + TABLE_SPOTS_USERID + ") " + "REFERENCES "
			+ TABLE_USERS + "(" + TABLE_USERS_ID + "))";

	private DbTools(Context context) {
		super(context, DB_NAME, null, 14);
	}

	public static DbTools getDatabaseInstance(Context context) {

		if (dataBaseInstance == null) {

			dataBaseInstance = new DbTools(context);
		}

		return dataBaseInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(CREATE_TABLE_USERS);
		database.execSQL(CREATE_TABLE_SPOTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPOTS);

		onCreate(db);
	}
}
