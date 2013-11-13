package com.alekstodorov.savemyspot;

import java.util.ArrayList;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.utils.AllSpots;
import com.alekstodorov.savemyspot.utils.HelpUtilities;
  
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;  
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; 
import android.widget.ListView;
import android.widget.TextView;
  
public class FragmentSpotList extends ListFragment {
	 
	private ArrayList<SpotModel> spotsList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Change the title for the current Activity
		
		getActivity().setTitle(R.string.fragment_spots_list_title);
		
		// Get the ArrayList from AllContacts
		
		populateListView(); 
	}
	 
	public void populateListView() {
		spotsList = AllSpots.get(getActivity()).getSpotsList();
		
		SpotAdapter contactAdapter = new SpotAdapter(spotsList);
		
		// Provides the data for the ListView by setting the Adapter 
		
		setListAdapter(contactAdapter);
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		 
		
		SpotModel clieckedSpot = ((SpotAdapter) getListAdapter()).getItem(position);
		
		// Pass the Context with getActivity and the Activity to
		// start being ContactActivity
		
		Intent newIntent = new Intent(getActivity(), SpotViewPager.class);
		
		// Add the IdNumber when calling for the Activity to display
		// so that the right data is loaded
		
		newIntent.putExtra(HelpUtilities.SPOT_ID, 
				clieckedSpot.getId());
		
		startActivity(newIntent); 
	}
 
	private class SpotAdapter extends ArrayAdapter<SpotModel> {

		public SpotAdapter(ArrayList<SpotModel> spots) {
	    	
	    		// An Adapter acts as a bridge between an AdapterView and the 
				// data for that view. The Adapter also makes a View for each 
				// item in the data set. (Each list item in our ListView)
			
				// The constructor gets a Context so it so it can use the 
				// resource being the simple_list_item and the ArrayList
				// android.R.layout.simple_list_item_1 is a predefined 
				// layout provided by Android that stands in as a default
	    	
	            super(getActivity(), android.R.layout.simple_list_item_1, spots);
	    }
		
		// getView is called each time it needs to display a new list item
		// on the screen because of scrolling for example.
		// The Adapter is asked for the new list row and getView provides
		// it.
		// position represents the position in the Array from which we will 
		// be pulling data.
		// convertView is a pre-created list item that will be reconfigured 
		// in the code that follows.
		// ViewGroup is our ListView
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			// Check if this is a recycled list item and if not we inflate it
			
			if(convertView == null){
				
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_spot, null); 
			}
			
			// Find the right data to put in the list item
			
			SpotModel theSpot = getItem(position);
			
			// Put the right data into the right components
			
			TextView contactTitleTextView =
	                (TextView)convertView.findViewById(R.id.spot_title);
			
			contactTitleTextView.setText(theSpot.getTitle());
			
	        TextView descriptionTextView =
	                (TextView)convertView.findViewById(R.id.spot_description);
	        
	        descriptionTextView.setText(theSpot.getDescription());
	          
			// Return the finished list item for display
			
	        return convertView; 
		} 
	}  
}
