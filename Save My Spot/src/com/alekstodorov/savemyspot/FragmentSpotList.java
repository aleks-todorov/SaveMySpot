package com.alekstodorov.savemyspot;

import java.util.ArrayList;

import com.alekstodorov.savemyspot.data.IReadable;
import com.alekstodorov.savemyspot.data.IUowData;
import com.alekstodorov.savemyspot.data.SpotsDatasource;
import com.alekstodorov.savemyspot.data.UowData;
import com.alekstodorov.savemyspot.models.SpotModel;
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
	private IUowData uowData;
	private SpotsDatasource spotDatasource;
	private ArrayList<SpotModel> spotsList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActivity().setTitle(R.string.fragment_spots_list_title);

		uowData = new UowData(getActivity()); 
		((IReadable) uowData).open(); 
		spotDatasource = (SpotsDatasource) uowData.getSpots();
	  
		populateListView(); 
	}

	public void populateListView() {

		spotsList = (ArrayList<SpotModel>) spotDatasource.findAll();

		SpotAdapter contactAdapter = new SpotAdapter(spotsList);

		setListAdapter(contactAdapter);
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {

		SpotModel clickedSpot = ((SpotAdapter) getListAdapter())
				.getItem(position);

		Intent newIntent = new Intent(getActivity(), ViewSpotActivity.class);

		newIntent.putExtra(HelpUtilities.SPOT_ID, clickedSpot.getId());

		startActivity(newIntent);
	}

	private class SpotAdapter extends ArrayAdapter<SpotModel> {

		public SpotAdapter(ArrayList<SpotModel> spots) {

			super(getActivity(), android.R.layout.simple_list_item_1, spots);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
 
			if (convertView == null) {

				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_spot, null);
			}

			SpotModel theSpot = getItem(position);

			TextView contactTitleTextView = (TextView) convertView
					.findViewById(R.id.spot_title);

			contactTitleTextView.setText(theSpot.getTitle());

			return convertView;
		}
	}
}
