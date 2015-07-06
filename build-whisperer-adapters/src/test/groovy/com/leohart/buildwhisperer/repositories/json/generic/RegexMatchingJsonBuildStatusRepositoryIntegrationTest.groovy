package com.leohart.buildwhisperer.repositories.json.generic;

import groovy.json.JsonSlurper

import org.junit.Assert
import org.junit.Test

import com.leohart.buildwhisperer.status.BuildStatus

public class RegexMatchingJsonBuildStatusRepositoryIntegrationTest {

	private static final String JSON_URL = "https://ci.jenkins-ci.org/job/core_selenium-test/lastBuild/api/json?tree=result";
	private static final String SUCCESS_REGEX = ".*SUCCESS.*";


	private RegexMatchingJsonBuildStatusRepository repository;

	@Test
	public void shouldReturnSuccessfulIfRegexMatchesJsonRetrieved() {
		repository = new RegexMatchingJsonBuildStatusRepository(JSON_URL, SUCCESS_REGEX);

		BuildStatus buildStatus = repository.getBuildStatus();

		Assert.assertNotNull("Build can succeed or fail, but must not be null: ${buildStatus} ", buildStatus);
	}

}
