package com.leohart.buildwhisperer.repositories.json.generic

import groovy.json.internal.LazyMap

import java.util.regex.Matcher

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import com.leohart.buildwhisperer.repositories.BuildStatusRepository
import com.leohart.buildwhisperer.status.BuildStatus
import com.leohart.buildwhisperer.status.SimpleBuildStatus

/**
 * This is a fairly simple way to retrieve status.  We get a JSON response, allow some tree traversal and then use a 
 * regex to match against the remaining JSON retrieved.  Seems to be "good enough" for now.
 *
 * @author Leo Hart
 *
 */
class RegexMatchingJsonBuildStatusRepository implements BuildStatusRepository<BuildStatus> {

	private static final Log LOG = LogFactory.getLog(RegexMatchingJsonBuildStatusRepository.class);

	private JsonRetriever jsonRetriever;
	private static final JsonTraverser jsonTraverser = new DefaultJsonTraverser();
	private String jsonURL;
	private String jsonTraversalPath = "";
	private String successRegEx;	

	public RegexMatchingJsonBuildStatusRepository(JsonRetriever jsonRetriever,
	String jsonURL, String successRegEx) {
		super();
		this.jsonRetriever = jsonRetriever;
		this.jsonURL = jsonURL;
		this.successRegEx = successRegEx;
	}

	public RegexMatchingJsonBuildStatusRepository(String jsonURL, String successRegEx) {
		super();
		this.jsonURL = jsonURL;
		this.successRegEx = successRegEx;
		this.jsonRetriever = new RESTClientJsonRetriever();
	}

	public RegexMatchingJsonBuildStatusRepository(JsonRetriever jsonRetriever, String jsonURL,
			String jsonTraversalPath, String successRegEx) {
		super();
		this.jsonRetriever = jsonRetriever;
		this.jsonURL = jsonURL;
		this.jsonTraversalPath = jsonTraversalPath;
		this.successRegEx = successRegEx;
	}

	@Override
	public BuildStatus getBuildStatus() {
		def json = this.jsonRetriever.retrieve(jsonURL);
	
		json = jsonTraverser.traverse(json, this.jsonTraversalPath);

		Matcher matcher = json =~ /${successRegEx}/	

		if (matcher.matches()){
			return new SimpleBuildStatus(true);
		}

		return new SimpleBuildStatus(false);
	}	
	
}
