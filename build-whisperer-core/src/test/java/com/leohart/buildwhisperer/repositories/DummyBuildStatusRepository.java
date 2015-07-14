package com.leohart.buildwhisperer.repositories;

import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

public class DummyBuildStatusRepository implements BuildStatusRepository<BuildStatus> {

	private BuildStatus buildStatus;

	public DummyBuildStatusRepository() {
		this.buildStatus = new SimpleBuildStatus(true);
	}

	public DummyBuildStatusRepository(BuildStatus buildStatus) {
		super();
		this.buildStatus = buildStatus;
	}

	@Override
	public BuildStatus getBuildStatus() throws BuildStatusRepositoryException {
		return this.buildStatus;
	}

}
