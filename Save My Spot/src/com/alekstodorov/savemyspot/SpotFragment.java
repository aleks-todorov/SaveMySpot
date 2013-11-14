package com.alekstodorov.savemyspot;

import android.app.AlertDialog; 
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
 
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	private Button deleteButton;

	public static SpotFragment newSpotFragment(long id) {

		Bundle passedData = new Bundle();

		passedData.putSerializable(HelpUtilities.SPOT_ID, id);

		SpotFragment spotFragment = new SpotFragment();

		spotFragment.setArguments(passedData);

		return spotFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		long spotId = (Long) getArguments().getSerializable(
				HelpUtilities.SPOT_ID);

		theSpot = AllSpots.get(getActivity()).getSpot(spotId);
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
		deleteButton = (Button) theView.findViewById(R.id.delete_item_btn);

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
 
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						getActivity());

				// Setting Dialog Title
				alertDialog.setTitle("Delete list item");

				// Setting Dialog Message
				alertDialog.setMessage("Are you sure that you want to delete this item?");

				// On pressing Settings button
				alertDialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								int id = (int) theSpot.getId();

								AllSpots.removeSpot(id);

								Intent intent = new Intent(getActivity(),
										SpotListviewActivity.class);

								startActivity(intent);
							}
						});

				alertDialog.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

				// Showing Alert Message
				alertDialog.show();
			}
		});

		titleTextView.setText(theSpot.getTitle());
		descriptionTextView.setText(theSpot.getDescription());
		latitudeValueTextView.setText(String.valueOf(theSpot.getLatitute()));
		longitudeValueTextView.setText(String.valueOf(theSpot.getLongitude()));

		return theView;
	};
		
}
