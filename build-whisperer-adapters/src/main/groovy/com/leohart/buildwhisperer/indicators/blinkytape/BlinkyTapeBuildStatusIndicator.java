package com.leohart.buildwhisperer.indicators.blinkytape;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */
public class BlinkyTapeBuildStatusIndicator implements PoweredBuildStatusIndicator {

	private static final Log LOG = LogFactory.getLog(BlinkyTapeBuildStatusIndicator.class);

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)
	 */
	@Override
	public void indicate(BuildStatus status) {
		LOG.info("Not yet implemented, but device was told to indicate status of " + status);
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus[])
	 */
	public void indicate(BuildStatus[] statuses) {
		LOG.info("Not yet implemented, Device was told to indicate statuses of "
				+ ArrayUtils.toString(statuses));
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator#turnOff()
	 */
	@Override
	public void turnOff() {
		LOG.info("Not yet implemented, Device was requested to turn off.");
	}

}
