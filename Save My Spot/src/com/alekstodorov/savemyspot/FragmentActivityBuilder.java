package com.alekstodorov.savemyspot;

 import com.alekstodorov.savemyspot.utils.HelpUtilities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
  
public abstract class FragmentActivityBuilder extends FragmentActivity {

	// This method must be implemented so that the right
	// type of Fragment can be returned.
	// CensusApp gets ContactFragment()
	// ContactListActivity gets FragmentContactList()

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.spots_listview_activity);

		FragmentManager fragManager = getSupportFragmentManager();

		// Check if the FragmentManager knows about the Fragment
		// id we refer to

		Fragment theFragment = fragManager
				.findFragmentById(R.id.spotsListContainer);

		// Check if the Fragment was found

		if (theFragment == null) {

			// If the Fragment wasn't found then we must create it

			// NEW We can generate many types of Fragments by having
			// CreateFragment define the type. So
			// theFragment = new ContactFragment();
			// is replaced by

			theFragment = createFragment();

			// Creates and commits the Fragment transaction
			// Fragment transactions add, attach, detach, replace
			// and remove Fragments.

			// add() gets the location to place the Fragment into and
			// the Fragment itself.

			fragManager.beginTransaction()
					.add(R.id.spotsListContainer, theFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_view_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_create) {

			createSpot();
		}

		return super.onOptionsItemSelected(item);
	}

	private void createSpot() {

		Intent intent = new Intent(this, CrateSpotActivity.class);

		startActivityForResult(intent, HelpUtilities.CREATOR_ACTIVITY_REQUEST);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == HelpUtilities.CREATOR_ACTIVITY_REQUEST
				&& resultCode == RESULT_OK) {
			refreshDisplay();
		}
	}

	private void refreshDisplay() {

		Intent intent = new Intent(this, SpotListviewActivity.class);

		startActivity(intent);
	} 
}
