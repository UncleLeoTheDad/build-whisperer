package com.leohart.buildwhisperer.status;

import java.util.ArrayList;
import java.util.List;

public class CompositeBuildStatus implements BuildStatus {

	private final List<BuildStatus> childStatuses = new ArrayList<BuildStatus>();

	public void addBuildStatus(final BuildStatus buildStatus) {
		this.childStatuses.add(buildStatus);
	}

	public List<BuildStatus> getChildStatuses() {
		return this.childStatuses;
	}

	/**
	 * @see com.leohart.buildwhisperer.status.BuildStatus#isSuccessful() Only
	 *      returns successful if all child statuses are successful
	 */
	public boolean isSuccessful() {
		for (BuildStatus status : this.childStatuses) {
			if (!status.isSuccessful()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("{successful=%s}", this.isSuccessful());
	}
}
