package com.alekstodorov.savemyspot;

import com.alekstodorov.savemyspot.data.IReadable;
import com.alekstodorov.savemyspot.data.IUowData;
import com.alekstodorov.savemyspot.data.UowData;
import com.alekstodorov.savemyspot.data.UsersDatasource;
import com.alekstodorov.savemyspot.utils.HelpUtilities;
import com.alekstodorov.savemyspot.utils.HttpRequester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager; 
import android.view.Menu;
import android.view.MenuItem;

public abstract class FragmentActivityBuilder extends FragmentActivity {

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.spots_listview_activity);

		FragmentManager fragManager = getSupportFragmentManager();

		Fragment theFragment = fragManager
				.findFragmentById(R.id.spotsListContainer);

		if (theFragment == null) {

			theFragment = createFragment();

			fragManager.beginTransaction()
					.add(R.id.spotsListContainer, theFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	 
		getMenuInflater().inflate(R.menu.list_view_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_create) {

			createSpot();
		} else if (item.getItemId() == R.id.action_download) {

			//Log.v(HelpUtilities.TAG, "Downloading spots from server");

			IUowData uowData = new UowData(this);
			((IReadable) uowData).open();
			
			String authCode = ((UsersDatasource)uowData.getUsers()).getLoggedUser().getAuthCode();
			
			HttpRequester requester = new HttpRequester(this);
			
			requester.getSpots(authCode);
		}

		return super.onOptionsItemSelected(item);
	}

	private void createSpot() {

		Intent intent = new Intent(this, CrateSpotActivity.class);

		startActivityForResult(intent, HelpUtilities.CREATOR_ACTIVITY_REQUEST);
	}
}
