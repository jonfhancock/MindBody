package com.jonfhancock.mindbody.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.jonfhancock.mindbody.AppointmentsActivity;
import com.jonfhancock.mindbody.R;
import com.jonfhancock.mindbody.StaffListActivity;
import com.jonfhancock.mindbody.adapters.AppointmentAdapter;
import com.jonfhancock.mindbody.data.provider.MindBodyContent;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Appointment.Columns;
import com.jonfhancock.mindbody.models.Staff;

/**
 * A fragment representing a single Staff member's Appointment list screen. This fragment is either
 * contained in a {@link StaffListActivity} in two-pane mode (on tablets) or a
 * {@link AppointmentsActivity} on handsets.
 */
public class AppointmentsFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	/**
	 * The fragment argument representing the Staff whose Appointments this fragment
	 * represents.
	 */
	public static final String ARG_STAFF = "staff";

	public static final String TAG = "Appointments";

	private AppointmentAdapter mAppointmentAdapter;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public AppointmentsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAppointmentAdapter = new AppointmentAdapter(getActivity(), null, false);
		setListAdapter(mAppointmentAdapter);
		if (getArguments().containsKey(ARG_STAFF)) {
			
			getLoaderManager().initLoader(1, getArguments(), this);
		}
	}
	
	@Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Staff staff = getArguments().getParcelable(ARG_STAFF);
        setEmptyText(getActivity().getString(R.string.has_no_appointments,staff.getName()));
        getListView().setDividerHeight(0);

    }
	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
		Staff staff = args.getParcelable(ARG_STAFF);
		// Here we're pulling all of the appointments from the content provider
		// given that their staff ID matches the Staff passed into the fragment.
		return new CursorLoader(getActivity(),
				MindBodyContent.Appointment.CONTENT_URI,
				MindBodyContent.Appointment.PROJECTION, Columns.STAFF.getName()
						+ "=?", new String[] { String.valueOf(staff.getId()) },
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAppointmentAdapter.changeCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAppointmentAdapter.changeCursor(null);
	}

}
