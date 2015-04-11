package com.leohart.buildwhisperer.repositories;

import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

public class DummyBuildStatusRepository implements
		BuildStatusRepository<BuildStatus> {

	private final BuildStatus buildStatus;

	public DummyBuildStatusRepository() {
		this.buildStatus = new SimpleBuildStatus(true);
	}

	public DummyBuildStatusRepository(final BuildStatus buildStatus) {
		super();
		this.buildStatus = buildStatus;
	}

	public BuildStatus getBuildStatus() throws BuildStatusRepositoryException {
		return this.buildStatus;
	}

}
