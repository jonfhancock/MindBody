package com.jonfhancock.mindbody;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jonfhancock.mindbody.accounts.GenericAccountService;
import com.jonfhancock.mindbody.data.provider.MindBodyProvider;
import com.jonfhancock.mindbody.data.provider.util.SyncUtils;
import com.jonfhancock.mindbody.fragments.AppointmentsFragment;
import com.jonfhancock.mindbody.models.Staff;

/**
 * An activity representing a single Staff's Appointments list screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * Appointments lists are presented side-by-side with a list of Staff in a
 * {@link StaffListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link AppointmentsFragment}.
 */
public class AppointmentsActivity extends ActionBarActivity {
	/**
	 * Handle to a SyncObserver. The ProgressBar element is visible until the
	 * SyncObserver reports that the sync is complete.
	 * 
	 * <p>
	 * This allows us to delete our SyncObserver once the application is no
	 * longer in the foreground.
	 */
	private Object mSyncObserverHandle;

	/**
	 * Options menu used to populate ActionBar.
	 */
	private Menu mOptionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		Staff staff = getIntent().getParcelableExtra(
				AppointmentsFragment.ARG_STAFF);
		setTitle(getString(R.string.detail_title, staff.getName()));

		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();

			arguments.putParcelable(AppointmentsFragment.ARG_STAFF, staff);
			AppointmentsFragment fragment = new AppointmentsFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}
	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// super.onCreateOptionsMenu(menu, inflater);
	// mOptionsMenu = menu;
	// inflater.inflate(R.menu.main, menu);
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this,
					StaffListActivity.class));
			return true;
		case R.id.menu_refresh:
			SyncUtils.TriggerRefresh();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		mOptionsMenu = menu;
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		mSyncStatusObserver.onStatusChanged(0);

		// Watch for sync state changes
		final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING
				| ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
		mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask,
				mSyncStatusObserver);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mSyncObserverHandle != null) {
			ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
			mSyncObserverHandle = null;
		}
	}

	/**
	 * Crfate a new anonymous SyncStatusObserver. It's attached to the app's
	 * ContentResolver in onResume(), and removed in onPause(). If status
	 * changes, it sets the state of the Refresh button. If a sync is active or
	 * pending, the Refresh button is replaced by an indeterminate ProgressBar;
	 * otherwise, the button itself is displayed.
	 */
	private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver() {
		/** Callback invoked with the sync adapter status changes. */
		@Override
		public void onStatusChanged(int which) {
			runOnUiThread(new Runnable() {
				/**
				 * The SyncAdapter runs on a background thread. To update the
				 * UI, onStatusChanged() runs on the UI thread.
				 */
				@Override
				public void run() {
					// Create a handle to the account that was created by
					// SyncService.CreateSyncAccount(). This will be used to
					// query the system to
					// see how the sync status has changed.
					Account account = GenericAccountService.GetAccount();
					if (account == null) {
						// GetAccount() returned an invalid value. This
						// shouldn't happen, but
						// we'll set the status to "not refreshing".
						setRefreshActionButtonState(false);
						return;
					}

					// Test the ContentResolver to see if the sync adapter is
					// active or pending.
					// Set the state of the refresh button accordingly.
					boolean syncActive = ContentResolver.isSyncActive(account,
							MindBodyProvider.AUTHORITY);
					boolean syncPending = ContentResolver.isSyncPending(
							account, MindBodyProvider.AUTHORITY);
					setRefreshActionButtonState(syncActive || syncPending);
				}
			});
		}
	};

	/**
	 * Set the state of the Refresh button. If a sync is active, turn on the
	 * ProgressBar widget. Otherwise, turn it off.
	 * 
	 * @param refreshing
	 *            True if an active sync is occuring, false otherwise
	 */
	public void setRefreshActionButtonState(boolean refreshing) {
		if (mOptionsMenu == null) {
			return;
		}

		final MenuItem refreshItem = mOptionsMenu.findItem(R.id.menu_refresh);
		if (refreshItem != null) {
			if (refreshing) {
				MenuItemCompat
						.setActionView(refreshItem,R.layout.actionbar_indeterminate_progress);
			} else {
				MenuItemCompat.setActionView(refreshItem,null);
			}
		}
	}
}
