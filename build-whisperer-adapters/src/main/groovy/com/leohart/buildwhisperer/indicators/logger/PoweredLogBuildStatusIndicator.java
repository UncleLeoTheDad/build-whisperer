package com.leohart.buildwhisperer.indicators.logger;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */

public class PoweredLogBuildStatusIndicator implements PoweredBuildStatusIndicator {

	private static final Log LOG = LogFactory.getLog(PoweredLogBuildStatusIndicator.class);

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)
	 */
	@Override
	public void indicate(BuildStatus status) {
		LOG.info("Device was told to indicate status of " + status);
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus[])
	 */
	public void indicate(BuildStatus[] statuses) {
		LOG.info("Device was told to indicate statuses of " + ArrayUtils.toString(statuses));
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator#turnOff()
	 */
	@Override
	public void turnOff() {
		LOG.info("Device was requested to turn off.");
	}

}
// /CLOVER:ON
