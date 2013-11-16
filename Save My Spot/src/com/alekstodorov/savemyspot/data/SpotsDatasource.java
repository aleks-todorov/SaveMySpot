package com.alekstodorov.savemyspot.data;

import java.util.ArrayList;
import java.util.List;
import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.models.UserModel;
import com.alekstodorov.savemyspot.utils.HelpUtilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class SpotsDatasource extends GenericDatasource<SpotModel> {

	private UserModel loggedUser;
	private Context currentContext;

	public SpotsDatasource(Context context) {
		super(context);

		currentContext = context;
	}

	@Override
	public SpotModel create(SpotModel spot) {

		getLoggedUser();
		Log.v(HelpUtilities.TAG, loggedUser.getUserId() + " ");

		ContentValues values = new ContentValues();

		values.put(DbTools.TABLE_SPOTS_TITLE, spot.getTitle());
		values.put(DbTools.TABLE_SPOTS_LATITUDE, spot.getLatitute());
		values.put(DbTools.TABLE_SPOTS_LONGITUDE, spot.getLongitude());
		values.put(DbTools.TABLE_SPOTS_USERID, loggedUser.getUserId());

		database.insert(DbTools.TABLE_SPOTS, null, values);

		return spot;
	}

	private void getLoggedUser() {

		UsersDatasource users = new UsersDatasource(currentContext);
		loggedUser = users.getLoggedUser();
	}

	@Override
	public List<SpotModel> findAll() {

		getLoggedUser();
		Log.v(HelpUtilities.TAG, loggedUser.getUserId() + " ");

		ArrayList<SpotModel> spotsList = new ArrayList<SpotModel>();

		String selectQuery = "SELECT * FROM " + DbTools.TABLE_SPOTS + " WHERE "
				+ DbTools.TABLE_SPOTS_USERID + " = " + loggedUser.getUserId()
				+ " ORDER BY " + DbTools.TABLE_SPOTS_ID + " DESC ";

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {

			do {

				SpotModel spot = new SpotModel();

				spot = getSpotInformationFromDb(cursor);

				spotsList.add(spot);

			} while (cursor.moveToNext());
		}

		return spotsList;
	}

	private SpotModel getSpotInformationFromDb(Cursor cursor) {

		SpotModel spot = new SpotModel();
		spot.setId(cursor.getLong(0));
		spot.setTitle(cursor.getString(1));
		spot.setLatitute(cursor.getDouble(2));
		spot.setLongitude(cursor.getDouble(3));

		return spot;
	}

	@Override
	public SpotModel findById(long id) {

		SpotModel spot = new SpotModel();

		String selectQuery = "SELECT * FROM " + DbTools.TABLE_SPOTS + " WHERE "
				+ DbTools.TABLE_SPOTS_ID + " = " + id;

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {

			do {

				spot = getSpotInformationFromDb(cursor);

			} while (cursor.moveToNext());
		}

		Log.v(HelpUtilities.TAG, spot.getId() + " Spot Id  ");
	  
		return spot;
	}

	public void delete(long id) {

		String request = "DELETE FROM " + DbTools.TABLE_SPOTS + " WHERE "
				+ DbTools.TABLE_SPOTS_ID + " = " + id;

		database.execSQL(request);
	}
}
