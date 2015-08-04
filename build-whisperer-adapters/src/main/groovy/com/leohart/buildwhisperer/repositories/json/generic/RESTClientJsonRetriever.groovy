package com.leohart.buildwhisperer.repositories.json.generic

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

class RESTClientJsonRetriever implements JsonRetriever {

	private String userName;
	private String password;

	public RESTClientJsonRetriever(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public RESTClientJsonRetriever() {
		super();
	}

	@Override
	public Object retrieve(String jsonApiUrl) {
		RESTClient rest = new RESTClient("");

		if (userName !=null){
			rest.client.addRequestInterceptor(new HttpRequestInterceptor() {
						void process(HttpRequest httpRequest, HttpContext httpContext) {
							httpRequest.addHeader('Authorization', 'Basic ' + '${userName}:${password}'.bytes.encodeBase64().toString())
						}
					});
		}

		rest.get(uri: jsonApiUrl,
				 contentType: ContentType.JSON) { response, json -> return json };
	}

}
