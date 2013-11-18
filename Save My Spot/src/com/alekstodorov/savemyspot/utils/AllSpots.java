package com.alekstodorov.savemyspot.utils;

import java.util.ArrayList;

import com.alekstodorov.savemyspot.data.IReadable;
import com.alekstodorov.savemyspot.data.IUowData;
import com.alekstodorov.savemyspot.data.UowData;
import com.alekstodorov.savemyspot.data.UsersDatasource;
import com.alekstodorov.savemyspot.models.SpotModel;

import android.content.Context;
  
public class AllSpots {

	private static AllSpots allSpots;
	private static ArrayList<SpotModel> spotsList;

	private AllSpots(Context applicationContext) {

		IUowData uowData = new UowData(applicationContext);
		((IReadable) uowData).open();
		String authCode = ((UsersDatasource) uowData.getUsers())
				.getLoggedUser().getAuthCode(); 
		 
		spotsList = new ArrayList<SpotModel>();

		SpotModel spotOne = new SpotModel("Catedral Church Sveta Nedelya",
				42.696741, 23.321289, authCode);
		spotsList.add(spotOne);

		SpotModel spotTwo = new SpotModel(
				"Saint Clement of Ohridski University", 42.693476, 23.334691,
				authCode);
		spotsList.add(spotTwo);

		SpotModel spotThree = new SpotModel("National Palace of Culture",
				42.685054, 23.318993, authCode);
		spotsList.add(spotThree);

		SpotModel spotFour = new SpotModel("Mall of Sofia", 42.698649,
				23.308758, authCode);
		spotsList.add(spotFour);

		SpotModel spotFive = new SpotModel("National Stadium Vasil Levski",
				42.687593, 23.335193, authCode);
		spotsList.add(spotFive);

		SpotModel spotSix = new SpotModel("The Mall", 42.660316, 23.381499,
				authCode);
		spotsList.add(spotSix);

		SpotModel spotSeven = new SpotModel("Telerik Academy", 42.650832,
				23.379439, authCode);
		spotsList.add(spotSeven);

		SpotModel spotEight = new SpotModel("Saint Alexandar Nevski Cathedral",
				42.69581, 23.332851, authCode);
		spotsList.add(spotEight);

		SpotModel spotNine = new SpotModel("Theatre Ivan Vazov", 42.694359,
				23.326499, authCode);
		spotsList.add(spotNine);

		SpotModel spotTen = new SpotModel("Sheraton Hotel", 42.697104,
				23.322728, authCode);
		spotsList.add(spotTen);

		SpotModel spotEleven = new SpotModel("Ministry of Justice", 42.694754,
				23.328834, authCode);
		spotsList.add(spotEleven);

		SpotModel spotTwoelve = new SpotModel("#Ostavka", 42.694178, 23.332616,
				authCode);
		spotsList.add(spotTwoelve);
	}

	public static AllSpots get(Context context) {

		if (allSpots == null) {

			allSpots = new AllSpots(context.getApplicationContext());

		}

		return allSpots;
	}

	public ArrayList<SpotModel> getSpotsList() {

		return spotsList;
	}
}
