package com.leohart.buildwhisperer.repositories.hudson.json;

import java.net.ConnectException;

import org.codehaus.jackson.map.ObjectMapper;
import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.leohart.buildwhisperer.repositories.BuildStatusRepositoryException;

public class HudsonJsonBuildStatusRepository_getBuildStatusTests {

	private static final String ALL_BLUE_RESOURCE_PATH = "com/leohart/buildwhisperer/repositories/hudson/json/blue.json";
	private static final String ALL_BLUE_RUNNING_RESOURCE_PATH = "com/leohart/buildwhisperer/repositories/hudson/json/blue-running.json";
	private static final String ALL_RED_RESOURCE_PATH = "com/leohart/buildwhisperer/repositories/hudson/json/red.json";
	private static final String ALL_RED_RUNNING_RESOURCE_PATH = "com/leohart/buildwhisperer/repositories/hudson/json/red-running.json";
	private static final String ALL_YELLOW_RESOURCE_PATH = "com/leohart/buildwhisperer/repositories/hudson/json/yellow.json";
	private static final String ALL_YELLOW_RUNNING_RESOURCE_PATH = "com/leohart/buildwhisperer/repositories/hudson/json/yellow-running.json";

	private HudsonJsonBuildStatusRepository repository;

	@Test(expected = BuildStatusRepositoryException.class)
	public void shouldFailOn11thReconnectAttempt()
			throws Exception {
		final Integer EXPECTED_RECONNECT_ATTEMPTS = 10;

		Resource resource = new ClassPathResource(
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_BLUE_RESOURCE_PATH);

		ObjectMapper exceptionThrowingMapper = EasyMock
				.createNiceMock(ObjectMapper.class);

		org.easymock.EasyMock.expect(
				exceptionThrowingMapper.readValue(resource.getURL(),
						HudsonView.class))
				.andThrow(new ConnectException("whatever"))
				.times(EXPECTED_RECONNECT_ATTEMPTS + 1);

		EasyMock.replay(exceptionThrowingMapper);

		this.repository = new HudsonJsonBuildStatusRepository(resource,
				exceptionThrowingMapper);

		this.repository.getBuildStatus();
	}

	@Test
	public void shouldPauseAndAttemptReconnectUpTo10Times()
			throws Exception {
		final Integer EXPECTED_RECONNECT_ATTEMPTS = 10;

		Resource resource = new ClassPathResource(
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_BLUE_RESOURCE_PATH);

		ObjectMapper exceptionThrowingMapper = EasyMock
				.createNiceMock(ObjectMapper.class);

		org.easymock.EasyMock.expect(
				exceptionThrowingMapper.readValue(resource.getURL(),
						HudsonView.class))
				.andThrow(new ConnectException("whatever"))
				.times(EXPECTED_RECONNECT_ATTEMPTS - 1);

		org.easymock.EasyMock.expect(
				exceptionThrowingMapper.readValue(resource.getURL(),
						HudsonView.class)).andReturn(
				new HudsonViewBuilder().build());

		EasyMock.replay(exceptionThrowingMapper);

		this.repository = new HudsonJsonBuildStatusRepository(resource,
				exceptionThrowingMapper);

		this.repository.getBuildStatus();

		Assert.assertEquals("Should have attempted reconnection 10 times: ",
				EXPECTED_RECONNECT_ATTEMPTS,
				this.repository.getNumberOfConnectionAttempts());
	}

	@Test
	public void shouldReturnSuccessIfAllJobsAreBlue() {
		this.shouldReturnSuccessOrFailureDependingOnColor(
				true,
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_BLUE_RESOURCE_PATH);

		this.shouldReturnSuccessOrFailureDependingOnColor(
				true,
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_BLUE_RUNNING_RESOURCE_PATH);
	}

	@Test
	public void shouldReturnSuccessIfAtLeastOneJobIsRed() {
		this.shouldReturnSuccessOrFailureDependingOnColor(
				false,
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_RED_RESOURCE_PATH);

		this.shouldReturnSuccessOrFailureDependingOnColor(
				false,
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_RED_RUNNING_RESOURCE_PATH);
	}

	@Test
	public void shouldReturnSuccessIfAtLeastOneJobIsYello() {
		this.shouldReturnSuccessOrFailureDependingOnColor(
				false,
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_YELLOW_RESOURCE_PATH);

		this.shouldReturnSuccessOrFailureDependingOnColor(
				false,
				HudsonJsonBuildStatusRepository_getBuildStatusTests.ALL_YELLOW_RUNNING_RESOURCE_PATH);
	}

	private void shouldReturnSuccessOrFailureDependingOnColor(
			final boolean shouldReturnSuccess, final String colorResourcePath) {
		Resource resource = new ClassPathResource(colorResourcePath);

		this.repository = new HudsonJsonBuildStatusRepository(resource);

		Assert.assertEquals(
				String.format("Successful should have been %s for path %s: ",
						shouldReturnSuccess, colorResourcePath),
				shouldReturnSuccess, this.repository
						.getBuildStatus().isSuccessful());
	}

}
