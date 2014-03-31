package com.jonfhancock.mindbody.envelopes;

import com.alexgilleran.icesoap.xml.XMLParentNode;

public class GetStaffEnvelope extends BaseEnvelope {


	private static final String KEY_GET_STAFF = "GetStaff";

	public GetStaffEnvelope(String srcName,String password,int[] siteIds){
		declarePrefix(VERSION_PREFIX,MINDBODY_NAMESPACE);

		XMLParentNode getStaff = getBody().addNode(MINDBODY_NAMESPACE, KEY_GET_STAFF);
		addSourceCredentials(srcName,password,siteIds,getStaff);

	}
}
