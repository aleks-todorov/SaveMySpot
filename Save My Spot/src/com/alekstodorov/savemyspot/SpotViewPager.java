package com.alekstodorov.savemyspot;

import java.util.ArrayList;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.utils.AllSpots;
import com.alekstodorov.savemyspot.utils.HelpUtilities;
  
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager; 
import android.view.MenuItem;

public class SpotViewPager extends FragmentActivity {

	private ViewPager theViewPager;
	private ArrayList<SpotModel> spotList;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		theViewPager = new ViewPager(this);
		theViewPager.setId(R.id.viewPager);
		spotList = AllSpots.get(this).getSpotsList();
		 
		setContentView(theViewPager);

		getActionBar().setDisplayHomeAsUpEnabled(true);
 
		FragmentManager fragManager = getSupportFragmentManager();

		theViewPager.setAdapter(new FragmentStatePagerAdapter(fragManager) {

			@Override
			public int getCount() {

				return spotList.size();
			}

			@Override
			public Fragment getItem(int possition) {

				SpotModel theSpot = spotList.get(possition);

				return SpotFragment.newSpotFragment(theSpot.getId());
			}
		});

		long spotId = (Long) getIntent().getSerializableExtra(
				HelpUtilities.SPOT_ID);

		for (int i = 0; i < spotList.size(); i++) {

			if (spotList.get(i).getId() == spotId) {

				theViewPager.setCurrentItem(i);
				break;
			}
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
}
