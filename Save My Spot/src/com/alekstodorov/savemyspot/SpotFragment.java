package com.alekstodorov.savemyspot;
 
import android.os.Bundle;
import android.support.v4.app.Fragment;
 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.widget.TextView;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.utils.AllSpots;
import com.alekstodorov.savemyspot.utils.HelpUtilities;
  
public class SpotFragment extends Fragment {

	private SpotModel theSpot;
	private TextView titleTextView;
	private TextView descriptionTextView;
	private TextView latitudeValueTextView;
	private TextView longitudeValueTextView;

	public static SpotFragment newSpotFragment(long id) {

		Bundle passedData = new Bundle();

		passedData.putSerializable(HelpUtilities.SPOT_ID, id);

		SpotFragment spotFragment = new SpotFragment();

		spotFragment.setArguments(passedData);

		return spotFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// NEW Replace contact = new Contact();
		// Get the value from CONTACT_ID that was passed in

		long spotId = (Long) getArguments().getSerializable(
				HelpUtilities.SPOT_ID);

		// Get the Contact with the matching ID

		theSpot = AllSpots.get(getActivity()).getSpot(spotId);

		// END OF NEW
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Pass in the layout to inflate, the views parent and whether
		// to add the inflated view to the parent.
		// We mark this false because the Activity will add the view.

		View theView = inflater.inflate(R.layout.fragment_spot, container,
				false);

		titleTextView = (TextView) theView.findViewById(R.id.titleTextView);
		descriptionTextView = (TextView) theView
				.findViewById(R.id.descriptionTextView);
		latitudeValueTextView = (TextView) theView
				.findViewById(R.id.latitudeValueTextView); 
		longitudeValueTextView = (TextView) theView
				.findViewById(R.id.longitudeValueTextView);

		titleTextView.setText(theSpot.getTitle());
		descriptionTextView.setText(theSpot.getDescription());
		latitudeValueTextView.setText(String.valueOf(theSpot.getLatitute()));
		longitudeValueTextView.setText(String.valueOf(theSpot.getLongitude()));
		 
		return theView;
		// All the EditText components will use just one TextWatcher
		// which auto updates Contact.java 
	}; 
}
