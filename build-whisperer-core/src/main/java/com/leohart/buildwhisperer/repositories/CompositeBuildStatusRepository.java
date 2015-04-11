package com.leohart.buildwhisperer.repositories;

import java.util.ArrayList;
import java.util.List;

import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.CompositeBuildStatus;

public class CompositeBuildStatusRepository implements
		BuildStatusRepository<BuildStatus> {

	private List<BuildStatusRepository<BuildStatus>> childRepositories = new ArrayList<BuildStatusRepository<BuildStatus>>();

	public CompositeBuildStatusRepository() {
		// Nothing
	}

	public CompositeBuildStatusRepository(
			final List<BuildStatusRepository<BuildStatus>> childRepositories) {
		super();
		this.childRepositories = childRepositories;
	}

	public void addRepository(
			final BuildStatusRepository<BuildStatus> repository) {
		this.childRepositories.add(repository);
	}

	public BuildStatus getBuildStatus() throws BuildStatusRepositoryException {
		CompositeBuildStatus buildStatus = new CompositeBuildStatus();

		for (BuildStatusRepository<BuildStatus> repository : this.childRepositories) {
			buildStatus.addBuildStatus(repository.getBuildStatus());
		}

		return buildStatus;
	}

	public List<BuildStatusRepository<BuildStatus>> getChildRepositories() {
		return this.childRepositories;
	}

}
