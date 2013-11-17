package com.alekstodorov.savemyspot;

import com.alekstodorov.savemyspot.data.IReadable;
import com.alekstodorov.savemyspot.data.IUowData;
import com.alekstodorov.savemyspot.data.SpotsDatasource;
import com.alekstodorov.savemyspot.data.UowData;
import com.alekstodorov.savemyspot.models.SpotModel;
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

	private IUowData uowData;
	private Button deleteButton;
	private SpotModel theSpot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();

		uowData = new UowData(this);
		((IReadable) uowData).open();

		Long spotId = intent.getLongExtra(HelpUtilities.SPOT_ID, 0);

		theSpot = uowData.getSpots().findById(spotId);

		setTitle(theSpot.getTitle());

		deleteButton = (Button) findViewById(R.id.deleteButton);

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						ListItemActivity.this);
 
				alertDialog.setTitle(getResources().getString(R.string.delete_item_alert_title));
 
				alertDialog
						.setMessage(getResources().getString(R.string.delete_item_alert_question));
 
				alertDialog.setPositiveButton(getResources().getString(R.string.delete_item_alert_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								long id = theSpot.getId();

								SpotsDatasource spotDatasource = (SpotsDatasource) uowData
										.getSpots();

								spotDatasource.delete(id);

								Intent intent = new Intent(
										ListItemActivity.this,
										SpotListviewActivity.class);

								startActivity(intent);
							}
						});

				alertDialog.setNegativeButton(getResources().getString(R.string.delete_item_alert_no),
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

		// Marker showing current possition
		GPSTracker tracker = new GPSTracker(this);
		if (tracker.canGetLocation() == true) {

			GoogleMap map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.spotMap)).getMap();

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(theSpot.getLatitute(), theSpot.getLongitude()),
					17));
 
			map.addMarker(new MarkerOptions()
					.anchor(0.0f, 1.0f)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
					.position(
							new LatLng(theSpot.getLatitute(), theSpot
									.getLongitude())));

			double currentLatitue = tracker.getLatitude();
			double currentLongitude = tracker.getLongitude();

			map.addMarker(new MarkerOptions()
					.anchor(0.0f, 1.0f)
					// Anchors the marker on the bottom left
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.current_possition))
					.title(getResources().getString(R.string.map_current_possition))
					.position(new LatLng(currentLatitue, currentLongitude)));
			tracker.stopUsingGPS();
		} else {
			tracker.showSettingsAlert(); 
		}
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

	@Override
	protected void onResume() {
		super.onResume();
		if (uowData instanceof IReadable) {
			((IReadable) uowData).open();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (uowData instanceof IReadable) {
			((IReadable) uowData).close();
		}
	}
}
