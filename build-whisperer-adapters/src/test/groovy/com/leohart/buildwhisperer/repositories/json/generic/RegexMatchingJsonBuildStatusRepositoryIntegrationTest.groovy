package com.leohart.buildwhisperer.repositories.json.generic;

import groovy.json.JsonSlurper

import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

import com.leohart.buildwhisperer.status.BuildStatus

public class RegexMatchingJsonBuildStatusRepositoryIntegrationTest {

	private static final String JSON_URL = "SOMEURL"; //change me
	private static final String SUCCESS_REGEX = ".*SUCCESS.*";


	private JsonRetriever jsonRetriever;
	private RegexMatchingJsonBuildStatusRepository repository;

	@Test
	@Ignore //Don't use in CI
	public void shouldReturnSuccessfulIfRegexMatchesJsonRetrieved() {
		jsonRetriever = new RESTClientJsonRetriever("USER", "PASSWORD");
		repository = new RegexMatchingJsonBuildStatusRepository(jsonRetriever, JSON_URL, SUCCESS_REGEX);

		BuildStatus buildStatus = repository.getBuildStatus();

		Assert.assertNotNull("Build can succeed or fail, but must not be null: ${buildStatus} ", buildStatus);
	}

}
