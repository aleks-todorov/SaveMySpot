package com.alekstodorov.savemyspot;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.utils.AllSpots; 
import com.alekstodorov.savemyspot.utils.GPSTracker;
import com.alekstodorov.savemyspot.utils.HelpUtilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent; 
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ListItemActivity extends Activity {

	private Button deleteButton;
	private SpotModel theSpot;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();

		Long spotId = intent.getLongExtra(HelpUtilities.SPOT_ID, 0);

		theSpot = AllSpots.get(this).getSpot(spotId);

		setTitle(theSpot.getTitle());

		deleteButton = (Button) findViewById(R.id.deleteButton);

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						ListItemActivity.this);

				// Setting Dialog Title
				alertDialog.setTitle("Delete list item");

				// Setting Dialog Message
				alertDialog
						.setMessage("Are you sure that you want to delete this item?");

				// On pressing Settings button
				alertDialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								int id = (int) theSpot.getId();

								AllSpots.removeSpot(id);

								Intent intent = new Intent(
										ListItemActivity.this,
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

		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.spotMap)).getMap();

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(theSpot.getLatitute(), theSpot.getLongitude()), 17));

		// You can customize the marker image using images bundled with
		// your app, or dynamically generated bitmaps.
		map.addMarker(new MarkerOptions()
				.anchor(0.0f, 1.0f)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
				.position(
						new LatLng(theSpot.getLatitute(), theSpot
								.getLongitude())));

		// Marker showing current possition
		GPSTracker tracker = new GPSTracker(this);

		double currentLatitue = tracker.getLatitude();
		double currentLongitude = tracker.getLongitude();

		map.addMarker(new MarkerOptions()
				.anchor(0.0f, 1.0f)
				// Anchors the marker on the bottom left
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.current_possition))
				.title("You are here")
				.position(new LatLng(currentLatitue, currentLongitude)));
	}

	// Allows using the Application header to navigate back
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			Intent theIntent = new Intent(getApplication(),
					SpotListviewActivity.class);

			startActivity(theIntent);

			break;
		}

		return true;
	}
}
