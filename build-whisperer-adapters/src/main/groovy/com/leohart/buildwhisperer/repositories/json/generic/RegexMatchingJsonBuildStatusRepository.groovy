package com.leohart.buildwhisperer.repositories.json.generic

import groovyx.net.http.RESTClient

import java.util.regex.Matcher

import com.leohart.buildwhisperer.repositories.BuildStatusRepository
import com.leohart.buildwhisperer.status.BuildStatus
import com.leohart.buildwhisperer.status.SimpleBuildStatus

/**
 * This is a fairly lazy way to retrieve status, using a regex to match against the entire JSON 
 * retrieved.  Once upon a time I did create a dynamic way to step into jsonslurpy via a 
 * string (this.thing.i.want), but the solution escapes me at the moment and this will work 
 * for now.
 * 
 * @author Leo Hart
 *
 */
class RegexMatchingJsonBuildStatusRepository implements BuildStatusRepository<BuildStatus> {

	private JsonRetriever jsonRetriever;
	private String jsonURL;
	private String successRegEx;

	public RegexMatchingJsonBuildStatusRepository(String jsonURL, String successRegEx) {
		super();
		this.jsonURL = jsonURL;
		this.successRegEx = successRegEx;
		this.jsonRetriever = new RESTClientJsonRetriever();
	}

	public RegexMatchingJsonBuildStatusRepository(JsonRetriever jsonRetriever,
	String jsonURL, String successRegEx) {
		super();
		this.jsonRetriever = jsonRetriever;
		this.jsonURL = jsonURL;
		this.successRegEx = successRegEx;
	}

	@Override
	public BuildStatus getBuildStatus() {
		def json = this.jsonRetriever.retrieve(jsonURL);

		Matcher matcher = json =~ /${successRegEx}/

		if (matcher.matches()){
			return new SimpleBuildStatus(true);
		}

		return new SimpleBuildStatus(false);
	}
}
