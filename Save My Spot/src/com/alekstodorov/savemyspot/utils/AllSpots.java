package com.alekstodorov.savemyspot.utils;

import java.util.ArrayList;
  
import com.alekstodorov.savemyspot.models.SpotModel;
  
import android.content.Context;

public class AllSpots {

	private static AllSpots allSpots;
	private Context applicationContext;
	private static  ArrayList<SpotModel> spotsList;

	private AllSpots(Context applicationContext) {

		this.applicationContext = applicationContext;

		spotsList = new ArrayList<SpotModel>();

		// TODO
		// This goes away when app is ready

		SpotModel spotOne = new SpotModel(1, "Test spot One",
				"Spot one description", 24.54445, 58.2344);
		spotsList.add(spotOne);

		SpotModel spotTwo = new SpotModel(2, "Test spot Two",
				"Spot two description", 34.2345, 18.4544);
		spotsList.add(spotTwo);

		SpotModel spotThree = new SpotModel(3, "Test spot Three",
				"Spot three description", 13.54445, 98.2344);
		spotsList.add(spotThree);

		SpotModel spotFour = new SpotModel(4, "Test spot Four",
				"Spot four description", 76.14356, 65.234234);
		spotsList.add(spotFour);

		SpotModel spotFive = new SpotModel(5, "Test spot Five",
				"Spot one description", 51.124325, 95.23445);
		spotsList.add(spotFive);
		
		SpotModel spotSix = new SpotModel(6, "Test spot 6",
				"Spot 6 description", 51.124325, 95.23445);
		spotsList.add(spotSix);
		
		
		SpotModel spotSeven = new SpotModel(7, "Test spot 7",
				"Spot 7 description", 51.124325, 95.23445);
		spotsList.add(spotSeven);
		
		SpotModel spotEight = new SpotModel(8, "Test spot 8",
				"Spot 8 description", 51.124325, 95.23445);
		spotsList.add(spotEight);
		
		
		SpotModel spotNine = new SpotModel(9, "Test spot 9",
				"Spot 9 description", 51.124325, 95.23445);
		spotsList.add(spotNine);
		
		SpotModel spotTen = new SpotModel(10, "Test spot 10",
				"Spot 10 description", 51.124325, 95.23445);
		spotsList.add(spotTen);
		
		SpotModel spotEleven = new SpotModel(11, "Test spot 11",
				"Spot 11 description", 51.124325, 95.23445);
		spotsList.add(spotEleven);
		
		SpotModel spotTwoelve = new SpotModel(12, "Test spot 12",
				"Spot 12 description", 51.124325, 95.23445);
		spotsList.add(spotTwoelve);
	}
	
	public static void addSpot(SpotModel spot){
		
		spotsList.add(spot);
		 
	}

	public static AllSpots get(Context context) {

		if (allSpots == null) {

			// getApplicationContext returns the global Application object
			// This Context is global to every part of the application

			allSpots = new AllSpots(context.getApplicationContext());

		}

		return allSpots;
	}

	public ArrayList<SpotModel> getSpotsList() {

		return spotsList;
	}

	public SpotModel getSpot(long id) {

		for (SpotModel theSpot : spotsList) {

			if (theSpot.getId() == id) {

				return theSpot;

			}
		}

		return null; 
	} 
}
