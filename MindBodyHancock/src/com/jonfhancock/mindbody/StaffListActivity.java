package com.jonfhancock.mindbody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jonfhancock.mindbody.data.provider.util.SyncUtils;
import com.jonfhancock.mindbody.fragments.AppointmentsFragment;
import com.jonfhancock.mindbody.fragments.StaffListFragment;
import com.jonfhancock.mindbody.models.Staff;

/**
 * An activity representing a list of Staff members. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of Staff members, which when touched, lead to a
 * {@link AppointmentsActivity} representing stat staff's Appointment list. On tablets, the
 * activity presents the list of staff and appointment list side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of staff members is a
 * {@link StaffListFragment} and the appointment list (if present) is a
 * {@link AppointmentsFragment}.
 * <p>
 * This activity also implements the required {@link StaffListFragment.StaffCallbacks}
 * interface to listen for item selections.
 */
public class StaffListActivity extends ActionBarActivity implements
		StaffListFragment.StaffCallbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		SyncUtils.CreateSyncAccount(this);
		
		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((StaffListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}

	}

	/**
	 * Callback method from {@link StaffListFragment.StaffCallbacks} indicating that
	 * the item was selected.
	 */
	@Override
	public void onItemSelected(Staff staff) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putParcelable(AppointmentsFragment.ARG_STAFF, staff);
			AppointmentsFragment fragment = new AppointmentsFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, AppointmentsActivity.class);
			detailIntent.putExtra(AppointmentsFragment.ARG_STAFF, staff);
			startActivity(detailIntent);
		}
	}
}
