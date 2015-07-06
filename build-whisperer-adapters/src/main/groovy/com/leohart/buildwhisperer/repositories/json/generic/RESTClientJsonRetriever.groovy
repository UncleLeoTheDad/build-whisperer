package com.leohart.buildwhisperer.repositories.json.generic

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

class RESTClientJsonRetriever implements JsonRetriever {

	@Override
	public Object retrieve(String jsonApiUrl) {
		RESTClient rest = new RESTClient("");
		
		return rest.get(uri: jsonApiUrl,
			contentType: ContentType.JSON);
	}

}
