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
	public void onListItemClick(ListView listView, View view, int position,
			long id) {

		SpotModel clickedSpot = ((SpotAdapter) getListAdapter())
				.getItem(position);
		//
		// Intent newIntent = new Intent(getActivity(), SpotViewPager.class);
		//
		// newIntent.putExtra(HelpUtilities.SPOT_ID, clickedSpot.getId());
		//
		// startActivity(newIntent);

		Intent newIntent = new Intent(getActivity(), ListItemActivity.class);
		
		newIntent.putExtra(HelpUtilities.SPOT_ID, clickedSpot.getId());
	 
		startActivity(newIntent);
	}

	private class SpotAdapter extends ArrayAdapter<SpotModel> {

		public SpotAdapter(ArrayList<SpotModel> spots) {

			super(getActivity(), android.R.layout.simple_list_item_1, spots);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// Check if this is a recycled list item and if not we inflate it

			if (convertView == null) {

				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_spot, null);
			}

			// Find the right data to put in the list item

			SpotModel theSpot = getItem(position);

			// Put the right data into the right components

			TextView contactTitleTextView = (TextView) convertView
					.findViewById(R.id.spot_title);

			contactTitleTextView.setText(theSpot.getTitle());
 
			// Return the finished list item for display

			return convertView;
		}
	}
}
