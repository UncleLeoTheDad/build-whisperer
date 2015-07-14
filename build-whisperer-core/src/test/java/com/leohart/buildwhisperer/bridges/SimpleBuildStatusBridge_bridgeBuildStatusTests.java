package com.leohart.buildwhisperer.bridges;

import junit.framework.Assert;

import org.junit.Test;

import com.leohart.buildwhisperer.repositories.BuildStatusRepository;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

/**
 * @author Leo Hart
 */
public class SimpleBuildStatusBridge_bridgeBuildStatusTests {

	public class AlwaysSuccessOrAlwaysFailedBuildStatusRepository implements
		BuildStatusRepository<BuildStatus> {

		private BuildStatus buildStatusToReturn;

		public AlwaysSuccessOrAlwaysFailedBuildStatusRepository(
				BuildStatus buildStatusToReturn) {
			this.buildStatusToReturn = buildStatusToReturn;
		}

		@Override
		public BuildStatus getBuildStatus() {
			return this.buildStatusToReturn;
		}

	}

	private SimpleBuildStatusBridge bridge;
	private BuildStatusRepository<BuildStatus> repository;
	private StoreLastIndicatedBuildStatusIndicator indicator;

	@Test
	public void shouldCorrectlyBridgeFailureStatuses() {
		this.shouldCorrectlyBridgeStatus(false);
	}

	@Test
	public void shouldCorrectlyBridgeSuccessStatuses() {
		this.shouldCorrectlyBridgeStatus(true);
	}

	private void shouldCorrectlyBridgeStatus(boolean successful) {
		final BuildStatus EXPECTED_STATUS = new SimpleBuildStatus(successful);

		this.indicator = new StoreLastIndicatedBuildStatusIndicator();
		this.repository = new AlwaysSuccessOrAlwaysFailedBuildStatusRepository(EXPECTED_STATUS);
		this.bridge = new SimpleBuildStatusBridge(this.indicator, this.repository);

		this.bridge.bridgeBuildStatus();

		Assert.assertEquals(EXPECTED_STATUS, this.indicator.getLastStatusIndicated());
	}
}
