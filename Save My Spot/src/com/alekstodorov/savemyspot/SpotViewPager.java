package com.alekstodorov.savemyspot;

import java.util.ArrayList;
import java.util.UUID;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.utils.AllSpots;
import com.alekstodorov.savemyspot.utils.HelpUtilities;
import com.tutorial.censusapp.ContactFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class SpotViewPager extends FragmentActivity {

	private ViewPager theViewPager;
	private ArrayList<SpotModel> spotList;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		theViewPager = new ViewPager(this);
		theViewPager.setId(R.id.viewPager);

		setContentView(theViewPager);

		spotList = AllSpots.get(this).getSpotsList();

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
}
