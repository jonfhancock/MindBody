/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jonfhancock.mindbody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.jonfhancock.mindbody.data.provider.MindBodyContent;
import com.jonfhancock.mindbody.data.provider.MindBodyProvider;
import com.jonfhancock.mindbody.envelopes.GetAppointmentsEnvelope;
import com.jonfhancock.mindbody.envelopes.GetStaffEnvelope;
import com.jonfhancock.mindbody.models.Appointment;
import com.jonfhancock.mindbody.models.Staff;

/**
 * Define a sync adapter for the app.
 * 
 * <p>
 * This class is instantiated in {@link SyncService}, which also binds
 * SyncAdapter to the system. SyncAdapter should only be initialized in
 * SyncService, never anywhere else.
 * 
 * <p>
 * The system calls onPerformSync() via an RPC call through the IBinder object
 * supplied by SyncService.
 */
class SyncAdapter extends AbstractThreadedSyncAdapter {

	private static final int[] SITE_IDS = new int[] { -31100 };

	private static final String SOURCE_NAME = "MBO.Jon.Hancock";
	private static final String SOURCE_PASSWORD = "Z8y+5AOMyc8qCPAdbL0lZxcY6HM=";

	private static final String SOAP_SERVICE__STAFF = "https://api.mindbodyonline.com/0_5/StaffService.asmx";
	private static final String SOAP_SERVICE__APPOINTMENTS = "https://api.mindbodyonline.com/0_5/AppointmentService.asmx";

	private static final String SOAP_ACTION__GET_STAFF = "http://clients.mindbodyonline.com/api/0_5/GetStaff";
	private static final String SOAP_ACTION__GET_APPOINTMENTS = "http://clients.mindbodyonline.com/api/0_5/GetStaffAppointments";

	public static final String TAG = "SyncAdapter";

	/**
	 * Content resolver, for performing database operations.
	 */
	private final ContentResolver mContentResolver;

	private RequestFactory mRequestFactory = new RequestFactoryImpl();

	/**
	 * Constructor. Obtains handle to content resolver for later use.
	 */
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		mContentResolver = context.getContentResolver();
	}

	/**
	 * Constructor. Obtains handle to content resolver for later use.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public SyncAdapter(Context context, boolean autoInitialize,
			boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		mContentResolver = context.getContentResolver();
	}

	/**
	 * Called by the Android system in response to a request to run the sync
	 * adapter. The work required to read data from the network, parse it, and
	 * store it in the content provider is done here. Extending
	 * AbstractThreadedSyncAdapter ensures that all methods within SyncAdapter
	 * run on a background thread. For this reason, blocking I/O and other
	 * long-running tasks can be run <em>in situ</em>, and you don't have to set
	 * up a separate thread for them. .
	 * 
	 * <p>
	 * This is where we actually perform any work required to perform a sync.
	 * {@link AbstractThreadedSyncAdapter} guarantees that this will be called
	 * on a non-UI thread, so it is safe to perform blocking I/O here.
	 * 
	 * <p>
	 * The syncResult argument allows you to pass information back to the method
	 * that triggered the sync.
	 */
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		Log.i(TAG, "Beginning network synchronization");

		try {
			// First we get all the Staff members
			syncStaff(syncResult);

			// Next we'll get all the Appointments for each Staff member.
			// Since this must be done as separate api calls for each staff
			// member
			// using their credentials, we get our list of staff members, loop
			// over
			// them, and sync each staff's appointments individually.
			Uri uri = MindBodyContent.Staff.CONTENT_URI;
			Cursor c = mContentResolver.query(uri,
					MindBodyContent.Staff.PROJECTION, null, null, null);
			if (c != null) {
				while (c.moveToNext()) {
					syncAppointments(syncResult, new Staff(c));
				}
				c.close();
			}
		} catch (RemoteException e) {
			Log.e(TAG, "Error updating database: " + e.toString());
			syncResult.databaseError = true;
			return;
		} catch (SOAPException e) {
			Log.e(TAG, "Error parsing feed: " + e.toString());
			syncResult.stats.numParseExceptions++;
			return;
		} catch (OperationApplicationException e) {
			Log.e(TAG, "Error updating database: " + e.toString());
			syncResult.databaseError = true;
			return;
		}

		Log.i(TAG, "Network synchronization complete");
	}

	public void syncStaff(final SyncResult syncResult) throws SOAPException,
			RemoteException, OperationApplicationException {
		SOAP11Request<List<Staff>> staffRequest = mRequestFactory
				.buildListRequest(SOAP_SERVICE__STAFF, new GetStaffEnvelope(
						SOURCE_NAME, SOURCE_PASSWORD, SITE_IDS),
						SOAP_ACTION__GET_STAFF, Staff.class);

		final ContentResolver contentResolver = getContext()
				.getContentResolver();

		List<Staff> temp = staffRequest.executeBlocking();
		final List<Staff> entries = new ArrayList<Staff>();
		for (Staff member : temp) {
			if (member.getId() > 0) {
				entries.add(member);
			}
		}
		Log.i(TAG, "Parsing complete. Found " + entries.size() + " entries");

		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

		// Build hash table of incoming entries
		HashMap<Long, Staff> entryMap = new HashMap<Long, Staff>();
		for (Staff e : entries) {
			entryMap.put(e.getId(), e);
		}

		// Get list of all items
		Log.i(TAG, "Fetching local entries for merge");
		Uri uri = MindBodyContent.Staff.CONTENT_URI; // Get all entries
		Cursor c = contentResolver.query(uri, MindBodyContent.Staff.PROJECTION,
				null, null, null);
		if (c != null) {
			Log.i(TAG, "Found " + c.getCount()
					+ " local entries. Computing merge solution...");

			// Find stale data
			long id;

			while (c.moveToNext()) {
				syncResult.stats.numEntries++;
				id = c.getLong(MindBodyContent.Staff.Columns.ID.getIndex());

				Staff match = entryMap.get(id);
				if (match != null) {
					// Entry exists. Remove from entry map to prevent insert
					// later.
					entryMap.remove(id);
					// Check to see if the entry needs to be updated
					Uri existingUri = MindBodyContent.Staff.CONTENT_URI
							.buildUpon().appendPath(Long.toString(id)).build();

					// Update existing record
					Log.i(TAG, "Scheduling update: " + existingUri);
					batch.add(match.getUpdateOperation(existingUri));
					syncResult.stats.numUpdates++;

				} else {
					// Entry doesn't exist. Remove it from the database.
					Uri deleteUri = MindBodyContent.Staff.CONTENT_URI
							.buildUpon().appendPath(Long.toString(id)).build();
					Log.i(TAG, "Scheduling delete: " + deleteUri);
					batch.add(ContentProviderOperation.newDelete(deleteUri)
							.build());
					syncResult.stats.numDeletes++;
				}
			}

			c.close();
		}
		// Add new items
		for (Staff e : entryMap.values()) {
			Log.i(TAG, "Scheduling insert: staff id=" + e.getId());
			batch.add(e.getInsertOperation(MindBodyContent.Staff.CONTENT_URI));

			syncResult.stats.numInserts++;
		}
		Log.i(TAG, "Merge solution ready. Applying batch update");
		mContentResolver.applyBatch(MindBodyProvider.AUTHORITY, batch);
		mContentResolver.notifyChange(MindBodyContent.Staff.CONTENT_URI, // URI
																			// where
																			// data
																			// was
																			// modified
				null, // No local observer
				false); // IMPORTANT: Do not sync to network

	}

	public void syncAppointments(final SyncResult syncResult, Staff staff)
			throws SOAPException, RemoteException,
			OperationApplicationException {
		SOAP11Request<List<Appointment>> mApptRequest = mRequestFactory
				.buildListRequest(SOAP_SERVICE__APPOINTMENTS,
						new GetAppointmentsEnvelope(SOURCE_NAME,
								SOURCE_PASSWORD, SITE_IDS, staff.getUsername(),
								staff.getPassword()),
						SOAP_ACTION__GET_APPOINTMENTS, Appointment.class);

		final ContentResolver contentResolver = getContext()
				.getContentResolver();

		List<Appointment> entries = mApptRequest.executeBlocking();
		if (entries != null) {
			Log.i(TAG, "Parsing complete. Found " + entries.size() + " entries");

			ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

			// Build hash table of incoming entries
			HashMap<Long, Appointment> entryMap = new HashMap<Long, Appointment>();
			for (Appointment e : entries) {
				entryMap.put(e.getID(), e);
			}

			// Get list of all items
			Log.i(TAG, "Fetching local entries for merge");
			Uri uri = MindBodyContent.Appointment.CONTENT_URI; // Get all
																// entries
			Cursor c = contentResolver.query(uri,
					MindBodyContent.Appointment.PROJECTION,
					MindBodyContent.Appointment.Columns.STAFF.getName() + "=?",
					new String[] { String.valueOf(staff.getId()) }, null);
			if (c != null) {
				Log.i(TAG, "Found " + c.getCount()
						+ " local entries. Computing merge solution...");

				// Find stale data
				long id;

				while (c.moveToNext()) {
					syncResult.stats.numEntries++;
					id = c.getLong(MindBodyContent.Staff.Columns.ID.getIndex());

					Appointment match = entryMap.get(id);
					if (match != null) {
						// Entry exists. Remove from entry map to prevent insert
						// later.
						entryMap.remove(id);
						Uri existingUri = MindBodyContent.Appointment.CONTENT_URI
								.buildUpon().appendPath(Long.toString(id))
								.build();

						// Update existing record
						Log.i(TAG, "Scheduling update: " + existingUri);
						batch.add(match.getUpdateOperation(existingUri));
						syncResult.stats.numUpdates++;

					} else {
						// Entry doesn't exist. Remove it from the database.
						Uri deleteUri = MindBodyContent.Appointment.CONTENT_URI
								.buildUpon().appendPath(Long.toString(id))
								.build();
						Log.i(TAG, "Scheduling delete: " + deleteUri);
						batch.add(ContentProviderOperation.newDelete(deleteUri)
								.build());
						syncResult.stats.numDeletes++;
					}
				}

				c.close();
			}
			// Add new items
			for (Appointment e : entryMap.values()) {
				Log.i(TAG, "Scheduling insert: appointment id=" + e.getID());
				batch.add(e
						.getInsertOperation(MindBodyContent.Appointment.CONTENT_URI));

				syncResult.stats.numInserts++;
			}
			Log.i(TAG, "Merge solution ready. Applying batch update");
			mContentResolver.applyBatch(MindBodyProvider.AUTHORITY, batch);
			mContentResolver.notifyChange(
					MindBodyContent.Appointment.CONTENT_URI, // URI where data
																// was modified
					null, // No local observer
					false); // IMPORTANT: Do not sync to network
		}
	}
}
