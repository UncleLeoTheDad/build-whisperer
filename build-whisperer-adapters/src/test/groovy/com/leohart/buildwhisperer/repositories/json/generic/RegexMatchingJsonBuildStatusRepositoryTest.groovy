package com.leohart.buildwhisperer.repositories.json.generic;

import groovy.json.JsonSlurper

import org.junit.Assert
import org.junit.Test

import com.leohart.buildwhisperer.status.BuildStatus

public class RegexMatchingJsonBuildStatusRepositoryTest {

	private static final String JSON_URL = "Doesn't Matter as Retriver is Mocked";
	private static final String SUCCESS_REGEX = ".*SUCCESS.*";

	private static JsonRetriever jsonRetriever = new JsonRetriever () {
		private String status;

		@Override
		public Object retrieve(String jsonApiUrl) {
			String json = '{"result":"' + this.status + '"}';
			return new JsonSlurper().parseText(json);
		}

		public void setStatus(String status) {
			this.status = status;
		}
	};



	private RegexMatchingJsonBuildStatusRepository repository;

	@Test
	public void shouldReturnSuccessfulIfRegexMatchesJsonRetrieved() {
		this.jsonRetriever.setStatus("SUCCESS");
		repository = new RegexMatchingJsonBuildStatusRepository(jsonRetriever, JSON_URL, SUCCESS_REGEX);

		BuildStatus buildStatus = repository.getBuildStatus();

		Assert.assertTrue("Build should have been successful, but was ${buildStatus}: ", buildStatus.isSuccessful());
	}

	@Test
	public void shouldReturnFailureIfRegexDoesNotMatchJsonRetrieved() {
		this.jsonRetriever.setStatus("FAILURE");
		repository = new RegexMatchingJsonBuildStatusRepository(jsonRetriever, JSON_URL, SUCCESS_REGEX);

		BuildStatus buildStatus = repository.getBuildStatus();

		Assert.assertFalse("Build should have failed, but was ${buildStatus}: ", buildStatus.isSuccessful());
	}
}
