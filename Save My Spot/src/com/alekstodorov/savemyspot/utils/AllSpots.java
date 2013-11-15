package com.alekstodorov.savemyspot.utils;

import java.util.ArrayList;
  
import com.alekstodorov.savemyspot.models.SpotModel;
  
import android.content.Context;

public class AllSpots {

	private static AllSpots allSpots; 
	private static  ArrayList<SpotModel> spotsList;

	private AllSpots(Context applicationContext) {
 
		spotsList = new ArrayList<SpotModel>();
  
		SpotModel spotOne = new SpotModel(1, "Catedral Church Sveta Nedelya",
				 42.696741,23.321289);
		spotsList.add(spotOne);

		SpotModel spotTwo = new SpotModel(2, "Saint Clement of Ohridski University",
				42.6957,23.321568);
		spotsList.add(spotTwo);

		SpotModel spotThree = new SpotModel(3, "National Palace of Culture",
				42.685054,23.318993);
		spotsList.add(spotThree);

		SpotModel spotFour = new SpotModel(4, "Mall of Sofia",
				42.698649,23.308758);
		spotsList.add(spotFour);

		SpotModel spotFive = new SpotModel(5, "National Stadium Vasil Levski",
				42.687593,23.335193);
		spotsList.add(spotFive);
		
		SpotModel spotSix = new SpotModel(6, "The Mall",
				42.660316,23.381499);
		spotsList.add(spotSix);
		 
		SpotModel spotSeven = new SpotModel(7, "Telerik Academy",
				42.650832,23.379439);
		spotsList.add(spotSeven);
		
		SpotModel spotEight = new SpotModel(8, "Saint Alexandar Nevski Cathedral",
				42.69581,23.332851);
		spotsList.add(spotEight);
		 
		SpotModel spotNine = new SpotModel(9, "Theatre Ivan Vazov",
				42.694359,23.326499);
		spotsList.add(spotNine);
		
		SpotModel spotTen = new SpotModel(10, "Sheraton Hotel",
				42.697104,23.322728);
		spotsList.add(spotTen);
		
		SpotModel spotEleven = new SpotModel(11, "Ministry of Justice",
				42.694754,23.328834);
		spotsList.add(spotEleven);
		
		SpotModel spotTwoelve = new SpotModel(12, "#Ostavka",
				42.694178,23.332616);
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
	
	public static void removeSpot(int id){
		
		int itemId = 0;
		
		for (int i = 0; i < spotsList.size(); i++) {
			 
			if(spotsList.get(i).getId() == id){
				itemId = i;
				break;
			}
		}
		
		spotsList.remove(itemId);
	}
}
