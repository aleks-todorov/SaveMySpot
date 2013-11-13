package com.alekstodorov.savemyspot;

import android.support.v4.app.Fragment;

public class SpotListviewActivity extends FragmentActivityBuilder {

	@Override
	protected Fragment createFragment() { 
		return new FragmentSpotList();
	} 
}
