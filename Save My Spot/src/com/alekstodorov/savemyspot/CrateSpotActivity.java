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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CrateSpotActivity extends Activity {

	private double latitude;
	private double longitude;
	EditText spotTitle;
	TextView errorTextView;
	Button createSpotBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_spot);
		setTitle(R.string.create_item_page_title);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initComponents();

		setMapOnCurrentPossition();

	}

	private void setMapOnCurrentPossition() {
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.createSpotMap)).getMap();
		GPSTracker tracker = new GPSTracker(this);

		latitude = tracker.getLatitude();
		longitude = tracker.getLongitude();

		tracker.stopUsingGPS();

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
				longitude), 17));

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

	public void saveAndReturn() {

		String title = spotTitle.getText().toString();
		   
			SpotModel theSpot = new SpotModel(13, title, latitude, longitude);

			AllSpots.addSpot(theSpot);
 
			Intent intent = new Intent(this, SpotListviewActivity.class);
			
		    startActivity(intent); 
	}

	private void initComponents() {

		spotTitle = (EditText) findViewById(R.id.createSpotTitle);
		errorTextView = (TextView) findViewById(R.id.createSpotErrorTextView);
		createSpotBtn = (Button) findViewById(R.id.createSpotSaveButton);

		createSpotBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearErrorField();
				
				String title = spotTitle.getText().toString();

				try {
					HelpUtilities.validateTitle(title);
					saveAndReturn();

				} catch (Exception e) {
					errorTextView.setText(e.getMessage());
				}
			}
		});
	}
	
	private void clearErrorField(){ 
		errorTextView.setText("");
	}
	
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
