package com.leohart.buildwhisperer.repositories.json.generic

import groovy.util.logging.Commons
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

@Commons
class RESTClientJsonRetriever implements JsonRetriever {
	
	//private static final Log LOG = LogFactory.getLog(RESTClientJsonRetriever.class);

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

		if (userName != null){
			log.info("Authenticating wth user ${userName}");
			/*See https://weblogs.java.net/blog/rah003/archive/2013/02/20/jira-groovy-and-rest-it for details around authT*/ 
			def login = "${userName}:${password}";
			
			rest.client.addRequestInterceptor(new HttpRequestInterceptor() {
						void process(HttpRequest httpRequest, HttpContext httpContext) {
							httpRequest.addHeader("Authorization", "Basic " + login.bytes.encodeBase64().toString());
						}
					});
		}
		
		try{
			rest.get(uri: jsonApiUrl,
					 contentType: ContentType.JSON) { response, json -> 
					 	log.debug("Headers: ");
						response.getHeaders().each{log.debug("\t${it.name}=${it.value}")};
						log.debug("JSON: ${json}")
						
					 	return json ;
					 };
		}
		catch(Throwable ex){
			throw new JsonRetrieverException("JSON retrieval failed while parsing ${jsonApiUrl}.  Is destination service down?", ex);
		}
	}

}
