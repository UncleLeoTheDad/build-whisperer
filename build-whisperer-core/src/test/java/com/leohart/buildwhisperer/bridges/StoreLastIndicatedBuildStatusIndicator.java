package com.leohart.buildwhisperer.bridges;

import com.leohart.buildwhisperer.indicators.BuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;

public class StoreLastIndicatedBuildStatusIndicator implements
		BuildStatusIndicator {

	private BuildStatus lastStatusIndicated;

	public BuildStatus getLastStatusIndicated() {
		return this.lastStatusIndicated;
	}

	@Override
	public void indicate(final BuildStatus status) {
		this.lastStatusIndicated = status;
	}

}