package com.alekstodorov.savemyspot;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.utils.AllSpots;
import com.alekstodorov.savemyspot.utils.GPSTracker;
import com.alekstodorov.savemyspot.utils.HelpUtilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CrateSpotActivity extends Activity {

	EditText spotTitle;
	EditText spotDescription;
	Button createSpot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_spot);
		setTitle(R.string.create_item_page_title);

		initComponents();
	}

	public void saveAndReturn(View view) {

		String title = spotTitle.getText().toString();
		String description = spotDescription.getText().toString();
		double longitude = 0;
		double latitude = 0;

		GPSTracker gps = new GPSTracker(this);
		if (gps.canGetLocation()) {

			longitude = gps.getLongitude();
			latitude = gps.getLatitude();

			SpotModel theSpot = new SpotModel(13, title, description, latitude,
					longitude);

			AllSpots.addSpot(theSpot);

			Log.i(HelpUtilities.TAG, "New spot Added");

			gps.stopUsingGPS();

			Intent intent = new Intent();

			setResult(RESULT_OK, intent);
			finish();
		} else {
			gps.showSettingsAlert();
		}
	}

	private void initComponents() {
		spotTitle = (EditText) findViewById(R.id.spot_title_editText);
		spotDescription = (EditText) findViewById(R.id.spot_description_editText);
		createSpot = (Button) findViewById(R.id.create_spot_btn);
	}

}
