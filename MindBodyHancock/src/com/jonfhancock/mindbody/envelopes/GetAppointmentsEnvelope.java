package com.jonfhancock.mindbody.envelopes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import com.alexgilleran.icesoap.xml.XMLParentNode;

public class GetAppointmentsEnvelope extends BaseEnvelope {

	private static final String FORCED_END_DATE = "January 4, 2014";
	private static final String FORCED_START_DATE = "January 3, 2014";
	
	private static final String KEY_END_DATE = "EndDate";
	private static final String KEY_START_DATE = "StartDate";
	private static final String KEY_USERNAME = "Username";
	private static final String KEY_STAFF_CREDS = "StaffCredentials";
	private static final String KEY_GET_STAFF_APPTS = "GetStaffAppointments";
	
	@SuppressLint("SimpleDateFormat")
	private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("deprecation")
	public GetAppointmentsEnvelope(String srcName, String password,
			int[] siteIds, String staffUsername, String staffPassword) {
		declarePrefix(VERSION_PREFIX, MINDBODY_NAMESPACE);
		XMLParentNode rootNode = getBody().addNode(MINDBODY_NAMESPACE, KEY_GET_STAFF_APPTS);
		XMLParentNode request = addSourceCredentials(srcName, password, siteIds, rootNode);

		XMLParentNode staffCreds = request.addNode(MINDBODY_NAMESPACE, KEY_STAFF_CREDS);
		staffCreds.addTextNode(MINDBODY_NAMESPACE, KEY_USERNAME, staffUsername);
		staffCreds.addTextNode(MINDBODY_NAMESPACE, KEY_PASSWORD, staffPassword);
		XMLParentNode staffSiteIdsField = staffCreds.addNode( MINDBODY_NAMESPACE, KEY_SITE_IDS);
		for (int i : siteIds) {
			staffSiteIdsField.addTextNode(MINDBODY_NAMESPACE, KEY_INT, String.valueOf(i));
		}
		request.addTextNode(MINDBODY_NAMESPACE, KEY_START_DATE, mDateFormat.format(new Date(FORCED_START_DATE)));
		request.addTextNode(MINDBODY_NAMESPACE, KEY_END_DATE, mDateFormat.format(new Date(FORCED_END_DATE)));
	}
}
