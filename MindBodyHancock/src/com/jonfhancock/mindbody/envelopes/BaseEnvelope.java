package com.jonfhancock.mindbody.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

public class BaseEnvelope extends BaseSOAP11Envelope {
	protected static final String MINDBODY_NAMESPACE = "http://clients.mindbodyonline.com/api/0_5";
	protected static final String VERSION_PREFIX = "_5";

	protected static final String KEY_SITE_IDS = "SiteIDs";
	protected static final String KEY_PASSWORD = "Password";
	protected static final String KEY_INT = "int";

	private static final String KEY_SOURCE_NAME = "SourceName";
	private static final String KEY_SOURCE_CREDS = "SourceCredentials";
	private static final String KEY_REQUEST = "Request";

	protected XMLParentNode addSourceCredentials(String srcName,
			String password, int[] siteIds, XMLParentNode root) {
		XMLParentNode request = root.addNode(MINDBODY_NAMESPACE, KEY_REQUEST);
		XMLParentNode srcCreds = request.addNode(MINDBODY_NAMESPACE, KEY_SOURCE_CREDS);
		srcCreds.addTextNode(MINDBODY_NAMESPACE, KEY_SOURCE_NAME, srcName);
		srcCreds.addTextNode(MINDBODY_NAMESPACE, KEY_PASSWORD, password);
		XMLParentNode siteIdsField = srcCreds.addNode(MINDBODY_NAMESPACE, KEY_SITE_IDS);
		for (int i : siteIds) {
			siteIdsField.addTextNode(MINDBODY_NAMESPACE, KEY_INT, String.valueOf(i));
		}
		return request;
	}
}
