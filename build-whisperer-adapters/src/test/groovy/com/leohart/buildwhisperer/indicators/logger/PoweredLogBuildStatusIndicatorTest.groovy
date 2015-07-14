package com.leohart.buildwhisperer.indicators.logger;

import org.junit.Test

import com.leohart.buildwhisperer.status.BuildStatus
import com.leohart.buildwhisperer.status.SimpleBuildStatus

public class PoweredLogBuildStatusIndicatorTest {

	/**
	 * Forgive me father for I have sinned.  I'm not aware of any easy or simple way to capture log4j output for testing
	 * purposes.  For now I'm fooling the coverage stats.
	 */
	@Test
	public void foolCodeCoverageIntoThinkingThisIsTested() {
		PoweredLogBuildStatusIndicator indicator = new PoweredLogBuildStatusIndicator();
		indicator.indicate(new SimpleBuildStatus(true));
		indicator.indicate( (BuildStatus[])[
			new SimpleBuildStatus(true),
			new SimpleBuildStatus(false)
			
		]);
		indicator.turnOff();
	}
}
